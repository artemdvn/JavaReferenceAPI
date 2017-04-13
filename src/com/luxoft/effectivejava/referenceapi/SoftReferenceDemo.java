package com.luxoft.effectivejava.referenceapi;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class SoftReferenceDemo {

	public static void main(String[] args) throws InterruptedException {
		// Create two Employee objects that are strongly reachable from e1 and
		// e2.
		Employee e1 = new Employee("John Doe");
		Employee e2 = new Employee("Jane Doe");
		// Create a ReferenceQueue object that is strongly reachable from q.
		ReferenceQueue q = new ReferenceQueue();
		// Create a SoftReference array with room for two references to
		// SoftReference objects. The array is strongly reachable from sr.
		SoftReference[] sr = new SoftReference[2];
		// Assign a SoftReference object to each array element. That object
		// is strongly reachable from that element. Each SoftReference object
		// encapsulates an Employee object that is referenced by e1 or e2 (so
		// the Employee object is softly reachable from the SoftReference
		// object), and associates the ReferenceQueue object, referenced by
		// q, with the SoftReference object.
		sr[0] = new SoftReference(e1, q);
		sr[1] = new SoftReference(e2, q);
		// Remove the only strong references to the Employee objects.
		e1 = null;
		e2 = null;
		// Poll reference queue until SoftReference object arrives.
		Reference r;
		while ((r = q.poll()) == null) {
			System.out.println("Polling reference queue");
			// Suggest that the garbage collector should run.
			System.gc();
			// Thread.sleep(1000);

			System.out.println("\n*** Simulating OOME ... ***\n");
			simulateOutOfMemoryError();
			System.out.println("\n*** After OOME was thrown ***\n");

		}
		// Identify the SoftReference object whose soft reference was
		// cleared, and print an appropriate message.
		if (r == sr[0])
			System.out.println("John Doe Employee object's soft reference " + "cleared");
		else
			System.out.println("Jane Doe Employee object's soft reference " + "cleared");
		// Attempt to retrieve a reference to the Employee object.

		Employee e = (Employee) r.get();
		// e will always be null because soft references are cleared before
		// references to their containing SoftReference objects are queued
		// onto a reference queue.
		if (e != null)
			System.out.println(e.toString());
	}

	private static void simulateOutOfMemoryError() throws InterruptedException {
		try {
			final ArrayList<Object[]> allocations = new ArrayList<>();
			while (true) {
				allocations.add(new Object[Math.abs((int) Runtime.getRuntime().maxMemory())]);
			}
		} catch (OutOfMemoryError e) {
			System.out.printf("Great, we have got an [%s] ! \t GC will clean all [%s] (s) ! \n",
					OutOfMemoryError.class.getSimpleName(), SoftReference.class.getName());
		}
		Thread.sleep(2000);
	}
}
