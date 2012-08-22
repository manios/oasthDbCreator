package com.oasth;

public class BusLine {
	private int id;
	private String number;
	private String name;

	public BusLine(String rawLine) {
		String testio[] = rawLine.split(",");
		this.id = Integer.parseInt(testio[0]);
		this.number = testio[1];
		this.name = testio[2];
	}

	public BusLine(int id, String number, String name) {
		this.id = id;
		this.number = number;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toCsv() {
		return this.id + "," + this.number + "," + this.name;
	}

}
