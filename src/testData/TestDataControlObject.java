package testData;

/**
 * 
 * Represents the test results to be returned
 */
public class TestDataControlObject {
	private String controlName;
	private String expectedResult;

	/**
	 * 
	 * : Gets the control name
	 */
	public String getControlName() {
		return controlName;
	}

	/**
	 * 
	 * : Sets the control name
	 */
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	/**
	 * 
	 * : Gets the expected result
	 */
	public String getExpectedResult() {
		return expectedResult;
	}

	/**
	 * 
	 * : Sets the expected result
	 */
	public void setExpectedResults(String expectedResult) {
		this.expectedResult = expectedResult;
	}
}
