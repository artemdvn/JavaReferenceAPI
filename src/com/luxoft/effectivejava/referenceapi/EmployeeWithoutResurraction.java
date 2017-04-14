package com.luxoft.effectivejava.referenceapi;

public class EmployeeWithoutResurraction {

	private String name;

	public EmployeeWithoutResurraction(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("Finalize " + name);
	}
}
