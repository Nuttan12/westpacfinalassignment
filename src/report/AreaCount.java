package report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * Captures the execution status of the tests
 */
public class AreaCount {

	@SuppressWarnings("unused")
	private ArrayList<String[]> areaDataTable;
	private HashMap<String, String> areaMap;
	private List<Object> lTestmethodname;
	private List<Object> lStatus;
	@SuppressWarnings("unused")
	private List<Object> areas;
	private List<Object> lTestCaseAreas;

	/**
	 * 
	 * : Gets the overall execution status of the tests
	 */
	public List<Object> getlTestCaseAreas() {
		return lTestCaseAreas;
	}

	private HashMap<String, Integer> passAreaCount;
	private HashMap<String, Integer> failAreaCount;
	private HashMap<String, Integer> skipAreaCount;

	/**
	 * 
	 * : This method is used to get the status of each test method executed
	 * 
	 * @throws IOException
	 */
	public AreaCount(ParsingTestResultXML results) throws IOException {
		areaDataTable = new ArrayList<>();
		passAreaCount = new HashMap<>();
		failAreaCount = new HashMap<>();
		skipAreaCount = new HashMap<>();

		areas = new ArrayList<>();
		lTestCaseAreas = new ArrayList<>();
		areaMap = results.getAreaMap();
		lTestmethodname = results.gettestmethodname();
		lStatus = results.getStatus();
		for (int i = 0; i < lTestmethodname.size(); i++) {
			String testName = (String) lTestmethodname.get(i);
			String area = areaMap.get(testName);
			lTestCaseAreas.add(i, area);
		}
	}

	/**
	 * 
	 * : This method is used to get the counts based on pass, fail, skip from
	 * the methods and puts them in a hashmap
	 */
	public void generateAreasTestCount() {
		for (int i = 0; i < lTestCaseAreas.size(); i++) {
			String area = (String) lTestCaseAreas.get(i);
			if (passAreaCount.containsKey(area)) {
				int passCount = passAreaCount.get(area);
				int failCount = failAreaCount.get(area);
				int skipCount = skipAreaCount.get(area);
				if (lStatus.get(i).toString().equalsIgnoreCase("Pass")) {
					passAreaCount.remove(area);
					passAreaCount.put(area, ++passCount);
				} else if (lStatus.get(i).toString().equalsIgnoreCase("Fail")) {
					failAreaCount.remove(area);
					failAreaCount.put(area, ++failCount);
				} else {
					skipAreaCount.remove(area);
					skipAreaCount.put(area, ++skipCount);
				}
			} else {
				if (lStatus.get(i).toString().equalsIgnoreCase("Pass")) {
					passAreaCount.put(area, 1);
					failAreaCount.put(area, 0);
					skipAreaCount.put(area, 0);
				} else if (lStatus.get(i).toString().equalsIgnoreCase("Fail")) {
					passAreaCount.put(area, 0);
					failAreaCount.put(area, 1);
					skipAreaCount.put(area, 0);
				} else {
					passAreaCount.put(area, 0);
					failAreaCount.put(area, 0);
					skipAreaCount.put(area, 1);
				}
			}
		}
	}

	/**
	 * 
	 * : Gets the Passed execution status of the tests
	 */
	public HashMap<String, Integer> getPassAreaCount() {
		return passAreaCount;
	}

	/**
	 * 
	 * : Gets the Failed execution status of the tests
	 */
	public HashMap<String, Integer> getFailAreaCount() {
		return failAreaCount;
	}

	/**
	 * 
	 * : Gets the Skipped execution status of the tests
	 */
	public HashMap<String, Integer> getSkipAreaCount() {
		return skipAreaCount;
	}
}
