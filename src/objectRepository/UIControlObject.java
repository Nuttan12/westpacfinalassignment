package objectRepository;

public class UIControlObject {
	private String controlName;
	private String controlProperty;
	private String typeOfProperty;

	/**
	 * Author Name : Nuttan Abhijan Swain : This method is used to return the
	 * name of the ui control
	 */
	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public String getControlProperty() {
		return controlProperty;
	}

	public void setControlProperty(String controlProperty) {
		this.controlProperty = controlProperty;
	}

	public String getTypeOfProperty() {
		return typeOfProperty;
	}

	public void setTypeOfProperty(String typeOfProperty) {
		this.typeOfProperty = typeOfProperty;
	}

}
