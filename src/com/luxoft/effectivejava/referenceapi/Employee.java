package com.luxoft.effectivejava.referenceapi;

public class Employee {

	static Employee resurrectedEmployee;

	private String name;

	public Employee(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Resurrect " + name);
		resurrectedEmployee = this;
	}
}
