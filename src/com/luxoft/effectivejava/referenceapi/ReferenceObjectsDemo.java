package com.luxoft.effectivejava.referenceapi;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ReferenceObjectsDemo {
	
	static final List<Employee> listOfEmployees = new ArrayList<Employee>();

	public static void main(String[] args) throws InterruptedException {
		Employee e = new Employee("John");
		Employee e2 = e;
		SoftReference sr = new SoftReference(e);
		WeakReference wr = new WeakReference(e);
		//PhantomReference phantom = new PhantomReference(e); //ERROR! ReferenceQueue needed

		System.out.println("--------------------Object is strongly reachable through e, e2, and softly reachable through sr, and weakly reachable through wr-------------------------");
		tryToResurrectEmployee();
		e = null;

		System.out.println("--------------------Object is still strongly reachable through e2, and softly reachable through sr, and weakly reachable through wr----------------------");
		tryToResurrectEmployee();
		e2 = null;
		
		// now Employee object is eligible for garbage collection but only be collected when JVM absolutely needs memory
		System.out.println("--------------------Object is still softly reachable through sr, and weakly reachable through wr----------------------");
		tryToResurrectEmployee();
		sr = null;
		
		System.out.println("--------------------Object is weakly reachable through wr and becomes resurrectable--------------------------------------------");
		tryToResurrectEmployee();
		
		System.out.println("--------------------Object is unreachable----------------------------------------------");
		tryToResurrectEmployee();
		
	}

	private static void tryToResurrectEmployee() throws InterruptedException {
		System.gc();
		Thread.sleep(100);		
		System.out.println("Resurrected employee: " + Employee.resurrectedEmployee);
		Employee.resurrectedEmployee = null;
	}
	
}

