package com.luxoft.effectivejava.referenceapi;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceDemo {

	public static void main(String[] args) throws InterruptedException {
		// Create an Employee object that is strongly reachable from e.
		EmployeeWithoutResurraction e = new EmployeeWithoutResurraction("John Doe");
		
		// Create a ReferenceQueue object that is strongly reachable from q.
		ReferenceQueue<EmployeeWithoutResurraction> q = new ReferenceQueue<EmployeeWithoutResurraction>();
		
		// Create a PhantomReference object that is strongly reachable from
		// pr. The PhantomReference object encapsulates the Employee object
		// (so the Employee object is phantomly reachable from the
		// PhantomReference object), and associates the ReferenceQueue object,
		// referenced by q, with the PhantomReference object.
		PhantomReference<EmployeeWithoutResurraction> pr = new PhantomReference<EmployeeWithoutResurraction>(e, q);
		
		// Remove the only strong reference to the Employee object.
		e = null;
		
		// Poll reference queue until PhantomReference object arrives.
		Reference r;
		while ((r = q.poll()) == null) {
			System.out.println("Polling reference queue");
			// Suggest that the garbage collector should run.
			System.gc();
			Thread.sleep(1000);
		}
		System.out.println("Employee referent in phantom-reachable state.");
		
		// Clear the PhantomReference object's phantom reference, so that
		// the Employee referent enters the unreachable state.
		pr.clear();
		
		// Clear the strong reference to the PhantomReference object, so the
		// PhantomReference object is eligible for garbage collection. (The
		// same could be done for the ReferenceQueue and Reference objects --
		// referenced by q and r, respectively.) Although not necessary in
		// this trivial program, you might consider doing such clearing in a
		// long-running loop, so that objects not needed can be collected.
		pr = null;
	}

}
