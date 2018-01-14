package report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import properties.LoadFrameworkProp;

/**
 * Reads the testng xml file to generate the customized html report
 */
public class ParsingTestResultXML {

	private String str_skipcount;
	private String str_passcount;
	private String str_failcount;
	private String str_duration;
	private String str_totalcount;
	private String str_startdate;
	private String str_enddate;
	private List<Object> lTestName;
	private List<Object> lClassName;
	private List<Object> lTestmethodname;
	private List<Object> lTestmethoddescription;
	private List<Object> lStatus;
	private List<Object> lErrormessage;
	private List<Object> ltestduration;
	private List<Object> lScreenShotlink;
	private List<Object> lGetAccess;
	private List<Object> lbrowserStatus;
	private HashMap<String, String> areaMap;

	/**
	 * @param args
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	/**
	 * : Reads the Testng Result XML and Returns Total Count,Pass
	 * Count,FailCount,duration,StartDate,EndDate,Class Name,TestName Test
	 * Status,ErrorMessage,ExceptionMessage,testduration.
	 **/

	public ParsingTestResultXML() throws IOException, ParserConfigurationException, SAXException {
		areaMap = new HashMap<>();
		str_skipcount = "0";
		str_passcount = "0";
		str_failcount = "0";
		str_duration = "0";
		str_totalcount = "0";
		str_startdate = "0";
		str_enddate = "0";
		lClassName = new ArrayList<Object>();
		lTestName = new ArrayList<Object>();
		lTestmethodname = new ArrayList<Object>();
		lTestmethoddescription = new ArrayList<Object>();
		lStatus = new ArrayList<Object>();
		ltestduration = new ArrayList<Object>();
		lErrormessage = new ArrayList<Object>();
		lScreenShotlink = new ArrayList<Object>();
		lGetAccess= new ArrayList<Object>();
		lbrowserStatus = new ArrayList<Object>();
		LoadFrameworkProp frameProp = new LoadFrameworkProp();
		File fXmlFile = new File(frameProp.getTestNgResultsXML());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		// Read and assign Total test,pass,fail skipped test cases count.
		NodeList nList = doc.getElementsByTagName("testng-results");
		Node nNode = nList.item(0);
		Element eElement = (Element) nNode;
		str_passcount = (eElement.getAttribute("passed"));
		str_failcount = (eElement.getAttribute("failed"));
		str_skipcount = (eElement.getAttribute("skipped"));
		str_totalcount = (eElement.getAttribute("total"));
		// Suite Duration
		nList = doc.getElementsByTagName("suite");
		nNode = nList.item(0);
		eElement = (Element) nNode;
		str_duration = (eElement.getAttribute("duration-ms"));
		str_startdate = (eElement.getAttribute("started-at"));
		str_enddate = (eElement.getAttribute("finished-at"));
		// Areas dataArray Creation
		NodeList groups = doc.getElementsByTagName("group");
		for (int i = 0; i < groups.getLength(); i++) {
			Node groupNode = groups.item(i);
			Element groupElement = (Element) groupNode;
			String groupName = groupElement.getAttribute("name");
			NodeList methods = groupElement.getElementsByTagName("method");
			for (int j = 0; j < methods.getLength(); j++) {
				Node method = methods.item(j);
				Element methodElement = (Element) method;
				String methodName = methodElement.getAttribute("name");
				if (areaMap.isEmpty()) {
					areaMap.put(methodName, groupName);
				} else if (!(areaMap.containsKey(methodName))) {
					areaMap.put(methodName, groupName);
				}

			}
		}
		// Reading the Test Results and adding them to list
		NodeList testNodelist = doc.getElementsByTagName("test");
		for (int k = 0; k < testNodelist.getLength(); k++) {
			Node testNode = testNodelist.item(k);
			Element eTestElement = (Element) testNode;
			String str_testname = eTestElement.getAttribute("name");

			NodeList classNodelist = eTestElement.getElementsByTagName("class");
			for (int i = 0; i < classNodelist.getLength(); i++) {
				Node classNode = classNodelist.item(i);
				Element eClassElement = (Element) classNode;
				String str_classname = eClassElement.getAttribute("name");
				NodeList testmethodNodeList = eClassElement.getElementsByTagName("test-method");
				String str_errormessage = null;
				for (int j = 0; j < testmethodNodeList.getLength(); j++) {
					Node testMethodNode = testmethodNodeList.item(j);
					Element eTestMethodElement = (Element) testMethodNode;

					if ((eTestMethodElement.getAttribute("name").equals("setup")
							&& eTestMethodElement.getAttribute("status").equals("FAIL"))) {
						if (eTestMethodElement.getElementsByTagName("message").getLength() > 0) {
							str_errormessage = (eTestMethodElement.getElementsByTagName("message").item(0)
									.getTextContent().toString());
						} else {
							str_errormessage = eTestMethodElement.getElementsByTagName("full-stacktrace").item(0)
									.getTextContent().toString();
						}

					} else if ((!eTestMethodElement.getAttribute("name").equals("setup")
							&& (!eTestMethodElement.getAttribute("name").equals("teardown"))
							&& (!eTestMethodElement.getAttribute("name").equals("beforeTest"))
							&& (!eTestMethodElement.getAttribute("name").equals("beforeClass"))
							&& (!eTestMethodElement.getAttribute("name").equals("beforeSuite"))
							&& (!eTestMethodElement.getAttribute("name").equals("afterSuite"))
							&& (!eTestMethodElement.getAttribute("name").equals("afterTest"))
							&& (!eTestMethodElement.getAttribute("name").equals("afterClass")))) {
						if ((eTestMethodElement.getAttribute("status").equals("PASS")) &&!(eTestMethodElement.getAttribute("name").equals("logStatus")) ) {
							lTestName.add(str_testname);
							lClassName.add(str_classname);
							
							if ((eTestMethodElement.getElementsByTagName("line").getLength() > 0)) {
								int size = eTestMethodElement.getElementsByTagName("line").getLength();
								boolean imagePresent = false;
								for (int y = size - 1; size > 0; size--) {
									if (eTestMethodElement.getElementsByTagName("line").item(size-1).getTextContent()
											.toString().replaceAll("\\s+","").contains(eTestMethodElement.getAttribute("name"))) {
										imagePresent = true;
										lTestmethodname.add(eTestMethodElement.getElementsByTagName("line").item(size-1)
												.getTextContent().toString());
										break;
									}								}
								if (imagePresent == false) {
									lTestmethodname.add("Proper Test Folder Structure Not Found");
								}

							}else {
								lTestmethodname.add("Proper Test Folder Structure Not Found");
							}
							
							//lTestmethodname.add(eTestMethodElement.getAttribute("name"));
							lStatus.add(eTestMethodElement.getAttribute("status"));
							ltestduration.add(eTestMethodElement.getAttribute("duration-ms"));
							if (eTestMethodElement.getAttribute("description").length() > 0) {
								lTestmethoddescription.add(eTestMethodElement.getAttribute("description"));
							} else {
								lTestmethoddescription.add("No Description Added");
							}
							lErrormessage.add("");
							
							if ((eTestMethodElement.getElementsByTagName("line").getLength() > 0)) {
								int size = eTestMethodElement.getElementsByTagName("line").getLength();
								boolean imagePresent = false;
								for (int y = size - 1; size > 0; size--) {
									if (eTestMethodElement.getElementsByTagName("line").item(size-1).getTextContent()
											.toString().contains("EXPECTED ACCESS:")) {
										imagePresent = true;
										lScreenShotlink.add(eTestMethodElement.getElementsByTagName("line").item(size-1)
												.getTextContent().toString().replace("EXPECTED ACCESS:", ""));
										break;
									}								}
								if (imagePresent == false) {
									lScreenShotlink.add("No access found");
								}

							}else {
								lScreenShotlink.add("No access needed");
							}
							
							if ((eTestMethodElement.getElementsByTagName("line").getLength() > 0)) {
								int size = eTestMethodElement.getElementsByTagName("line").getLength();
								boolean imagePresent = false;
								for (int y = size - 1; size > 0; size--) {
									if (eTestMethodElement.getElementsByTagName("line").item(size-1).getTextContent()
											.toString().contains("ACTUAL ACCESS:")) {
										imagePresent = true;
										lGetAccess.add(eTestMethodElement.getElementsByTagName("line").item(size-1)
												.getTextContent().toString().replace("ACTUAL ACCESS:", ""));
										break;
									}								}
								if (imagePresent == false) {
									lGetAccess.add("Access couldnot be verified");
								}

							}else {
								lGetAccess.add("No access needed");
							}

						} else if (eTestMethodElement.getAttribute("status").equals("SKIP")) {
							lTestName.add(str_testname);
							lClassName.add(str_classname);
							if ((eTestMethodElement.getElementsByTagName("line").getLength() > 0)) {
								int size = eTestMethodElement.getElementsByTagName("line").getLength();
								boolean imagePresent = false;
								for (int y = size - 1; size > 0; size--) {
									if (eTestMethodElement.getElementsByTagName("line").item(size-1).getTextContent()
											.toString().replaceAll("\\s+","").contains(eTestMethodElement.getAttribute("name"))) {
										imagePresent = true;
										lTestmethodname.add(eTestMethodElement.getElementsByTagName("line").item(size-1)
												.getTextContent().toString());
										break;
									}								}
								if (imagePresent == false) {
									lTestmethodname.add("Proper Test Folder Structure Not Found");
								}

							}else {
								lTestmethodname.add("Proper Test Folder Structure Not Found");
							}
							//lTestmethodname.add(eTestMethodElement.getAttribute("name"));
							lStatus.add(eTestMethodElement.getAttribute("status"));
							ltestduration.add(eTestMethodElement.getAttribute("duration-ms"));
							if (eTestMethodElement.getAttribute("description").length() > 0) {
								lTestmethoddescription.add(eTestMethodElement.getAttribute("description"));
							} else {
								lTestmethoddescription.add("No Description Added");
							}
							lErrormessage.add(str_errormessage);
							if ((eTestMethodElement.getElementsByTagName("line").getLength() > 0)) {
								int size = eTestMethodElement.getElementsByTagName("line").getLength();
								boolean imagePresent = false;
								for (int y = size - 1; size > 0; size--) {
									if (eTestMethodElement.getElementsByTagName("line").item(size-1).getTextContent()
											.toString().contains("EXPECTED ACCESS:")) {
										imagePresent = true;
										lScreenShotlink.add(eTestMethodElement.getElementsByTagName("line").item(size-1)
												.getTextContent().toString().replace("EXPECTED ACCESS:", ""));
										break;
									}								}
								if (imagePresent == false) {
									lScreenShotlink.add("No access found");
								}

							}else {
								lScreenShotlink.add("No access needed");
							}
							
							if ((eTestMethodElement.getElementsByTagName("line").getLength() > 0)) {
								int size = eTestMethodElement.getElementsByTagName("line").getLength();
								boolean imagePresent = false;
								for (int y = size - 1; size > 0; size--) {
									if (eTestMethodElement.getElementsByTagName("line").item(size-1).getTextContent()
											.toString().contains("ACTUAL ACCESS:")) {
										imagePresent = true;
										lGetAccess.add(eTestMethodElement.getElementsByTagName("line").item(size-1)
												.getTextContent().toString().replace("ACTUAL ACCESS:", ""));
										break;
									}								}
								if (imagePresent == false) {
									lGetAccess.add("No access found");
								}

							}else {
								lGetAccess.add("No access needed");
							}

						}

						else if (eTestMethodElement.getAttribute("status").equals("FAIL")) {
							lTestName.add(str_testname);
							lClassName.add(str_classname);
							if ((eTestMethodElement.getElementsByTagName("line").getLength() > 0)) {
								int size = eTestMethodElement.getElementsByTagName("line").getLength();
								boolean imagePresent = false;
								for (int y = size - 1; size > 0; size--) {
									if ((eTestMethodElement.getElementsByTagName("line").item(size-1).getTextContent()
											.toString().replaceAll("\\s+","").contains(eTestMethodElement.getAttribute("name")) ) && (!(eTestMethodElement.getElementsByTagName("line").item(size-1).getTextContent()
													.toString().contains(".png")))&& (!(eTestMethodElement.getElementsByTagName("line").item(size-1).getTextContent()
															.toString().contains("java")))) {
					
										imagePresent = true;
										lTestmethodname.add(eTestMethodElement.getElementsByTagName("line").item(size-1)
												.getTextContent().toString());
										break;
									}								}
								if (imagePresent == false) {
									lTestmethodname.add("Proper Test Folder Structure Found");
								}

							}else {
								lTestmethodname.add("Proper Test Folder Structure Not Found");
							}
							//lTestmethodname.add(eTestMethodElement.getAttribute("name"));
							ltestduration.add(eTestMethodElement.getAttribute("duration-ms"));
							if (eTestMethodElement.getAttribute("description").length() > 0) {
								lTestmethoddescription.add(eTestMethodElement.getAttribute("description"));
							} else {
								lTestmethoddescription.add("No Description Added");
							}
							if ((eTestMethodElement.getElementsByTagName("message").getLength() > 0)) {
								lErrormessage.add(eTestMethodElement.getElementsByTagName("message").item(0)
										.getTextContent().toString());
							} else {
								lErrormessage.add(eTestMethodElement.getElementsByTagName("full-stacktrace").item(0)
										.getTextContent().toString());
							}
							if ((eTestMethodElement.getElementsByTagName("line").getLength() > 0)) {
								int size = eTestMethodElement.getElementsByTagName("line").getLength();
								boolean imagePresent = false;
								for (int y = size - 1; size > 0; size--) {
									if (eTestMethodElement.getElementsByTagName("line").item(size-1).getTextContent()
											.toString().contains("EXPECTED ACCESS:")) {
										imagePresent = true;
										lScreenShotlink.add(eTestMethodElement.getElementsByTagName("line").item(size-1)
												.getTextContent().toString().replace("EXPECTED ACCESS:", ""));
										break;
									}								}
								if (imagePresent == false) {
									lScreenShotlink.add("No access found");
								}

							}else {
								lScreenShotlink.add("No access needed");
							}
							
							if ((eTestMethodElement.getElementsByTagName("line").getLength() > 0)) {
								int size = eTestMethodElement.getElementsByTagName("line").getLength();
								boolean imagePresent = false;
								for (int y = size - 1; size > 0; size--) {
									if (eTestMethodElement.getElementsByTagName("line").item(size-1).getTextContent()
											.toString().contains("ACTUAL ACCESS:")) {
										imagePresent = true;
										lGetAccess.add(eTestMethodElement.getElementsByTagName("line").item(size-1)
												.getTextContent().toString().replace("ACTUAL ACCESS:", ""));
										break;
									}								}
								if (imagePresent == false) {
									lGetAccess.add("Access couldnot be verified");
								}

							}else {
								lGetAccess.add("No access needed");
							}
							lStatus.add(eTestMethodElement.getAttribute("status"));

						}
					}
				}
			}
		}

		// Get Browser specific Test Status

		Set<Object> browsers = new HashSet<>(lTestName);
		List<Object> lstbrowsers = new ArrayList<>(browsers);
		int ipass = 0;
		int ifail = 0;
		int iskip = 0;
		for (int y = 0; y < lstbrowsers.size(); y++) {
			ipass = 0;
			ifail = 0;
			iskip = 0;
			for (int z = 0; z < lTestName.size(); z++) {
				if (lstbrowsers.get(y).equals(lTestName.get(z))) {
					if (lStatus.get(z).equals("PASS")) {
						ipass++;
					}
					if (lStatus.get(z).equals("FAIL")) {
						ifail++;
					}
					if (lStatus.get(z).equals("SKIP")) {
						iskip++;
					}

				}

			}
			int itotal = ipass + ifail + iskip;
			lbrowserStatus.add(lstbrowsers.get(y).toString());
			lbrowserStatus.add(itotal);
			lbrowserStatus.add(ipass);
			lbrowserStatus.add(ifail);
			lbrowserStatus.add(iskip);
		}
	}

	/**
	 * : Gets the count of total number of tests ran
	 */
	public String gettotalcount() {
		return str_totalcount;
	}

	/**
	 * : Gets the count of total number of tests skipped
	 */
	public String getskipcount() {
		return str_skipcount;
	}

	/**
	 * 
	 * : Gets the count of total number of tests passed
	 */
	public String getpasscount() {
		return str_passcount;
	}

	/**
	 * 
	 * : Gets a map that contains TestMethods name with their respective Area
	 */
	public HashMap<String, String> getAreaMap() {
		return areaMap;
	}

	/**
	 * 
	 * : Gets the count of total number of tests failed
	 */
	public String getfailcount() {
		return str_failcount;
	}

	/**
	 * 
	 * : Gets the total duration of the test run
	 */
	public String getduration() {
		return str_duration;
	}

	/**
	 * 
	 * : Gets the start date of the test run
	 */
	public String getStartdate() {
		return str_startdate;
	}

	/**
	 * 
	 * : Gets the end date of the test run
	 */
	public String getEnddate() {
		return str_enddate;
	}

	/**
	 * 
	 * : Gets the test name
	 */
	public List<Object> gettestname() {
		return lTestName;
	}

	/**
	 * 
	 * : Gets the test class name
	 */
	public List<Object> getclassname() {
		return lClassName;
	}

	/**
	 * 
	 * : Gets the test method name
	 */
	public List<Object> gettestmethodname() {
		return lTestmethodname;
	}

	/**
	 * 
	 * : Gets the status of each test
	 */
	public List<Object> getStatus() {
		return lStatus;
	}

	/**
	 * 
	 * : Gets the error message of the failed tests
	 */
	public List<Object> geterrormessage() {
		return lErrormessage;
	}

	/**
	 * 
	 * : Gets the test run duration
	 */
	public List<Object> gettestrundduration() {
		return ltestduration;
	}

	/**
	 * 
	 * : Gets the browser status
	 */
	public List<Object> getbrowserstatus() {
		return lbrowserStatus;
	}

	/**
	 * 
	 * : Gets the test method description of each test
	 */
	public List<Object> getTestMethodDesc() {
		return lTestmethoddescription;
	}

	/**
	 * 
	 * : Gets the test method name
	 */
	public List<Object> getscreenShot() {
		return lScreenShotlink;
	}

	public List<Object> getaccess() {
		// TODO Auto-generated method stub
		return lGetAccess;
	}

}
