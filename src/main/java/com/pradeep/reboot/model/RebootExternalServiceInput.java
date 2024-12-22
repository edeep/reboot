package com.pradeep.reboot.model;

public class RebootExternalServiceInput {
	
	String serialNumber;
	String oui;
	String productedClass;
	String model;
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getOui() {
		return oui;
	}
	public void setOui(String oui) {
		this.oui = oui;
	}
	public String getProductedClass() {
		return productedClass;
	}
	public void setProductedClass(String productedClass) {
		this.productedClass = productedClass;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

}
