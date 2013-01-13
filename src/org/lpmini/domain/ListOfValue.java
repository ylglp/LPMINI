package org.lpmini.domain;

public class ListOfValue {
	String	LOVType;
	String	localeString;
	String	key;
	String	stringValue;
	double	decimalValue;
	int		displayOrder;
	String	notes;
	
	public ListOfValue() {}
	
	public ListOfValue(String LOVType, String localeString, String key, String stringValue) {
		this.LOVType = LOVType;
		this.localeString = localeString;
		this.key = key;
		this.stringValue = stringValue;
	}
	
	public ListOfValue(String LOVType, String localeString, String key) {
		this.LOVType = LOVType;
		this.localeString = localeString;
		this.key = key;
	}
	
	public ListOfValue(String LOVType, String localeString, String key, double decimalValue) {
		this.LOVType = LOVType;
		this.localeString = localeString;
		this.key = key;
		this.decimalValue = decimalValue;
	}
	
	public String getLOVType() {
		return LOVType;
	}
	public void setLOVType(String lOVType) {
		LOVType = lOVType;
	}
	public String getLocaleString() {
		return localeString;
	}
	public void setLocaleString(String localeString) {
		this.localeString = localeString;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public double getDecimalValue() {
		return decimalValue;
	}
	public void setDecimalValue(double decimalValue) {
		this.decimalValue = decimalValue;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
