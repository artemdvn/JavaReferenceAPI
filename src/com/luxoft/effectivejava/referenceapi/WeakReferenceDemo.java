package com.luxoft.effectivejava.referenceapi;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class WeakReferenceDemo {

	public static void main(String[] args) {
		// Create a String object that is strongly reachable from key.
		String key = new String("key");
		/*
		 * Note: For this program, you cannot say String key = "key";. You
		 * cannot do that because (by itself), "key" is strongly referenced from
		 * an internal constant pool data structure (that I will discuss in a
		 * future article). There is no way for the program to nullify that
		 * strong reference. As a result, that object will never be garbage
		 * collected, and the polling loop will be infinite.
		 */
		
		// Create a ReferenceQueue object that is strongly reachable from q.
		ReferenceQueue<String> q = new ReferenceQueue<String>();
		
		// Create a WeakReference object that is strongly reachable from wr.
		// The WeakReference object encapsulates the String object that is
		// referenced by key (so the String object is weakly-reachable from
		// the WeakReference object), and associates the ReferenceQueue
		// object, referenced by q, with the WeakReference object.
		WeakReference<String> wr = new WeakReference<String>(key, q);
		
		// Create an Employee object that is strongly reachable from value.
		Employee value = new Employee("John Doe");
		
		// Create a WeakHashMap object that is strongly reachable from map.
		WeakHashMap<WeakReference<String>, Employee> map = new WeakHashMap<WeakReference<String>, Employee>();
		
		// Place the WeakReference and Employee object to the map.
		map.put(wr, value);
		
		// Remove the only strong reference to the String object.
		key = null;
		
		// Poll reference queue until WeakReference object arrives.
		Reference r;
		while ((r = q.poll()) == null) {
			System.out.println("Polling reference queue");
			// Suggest that the garbage collector should run.
			System.gc();
		}
		System.out.println("Weak reference to " + value + " cleared");
		
		// Using strong reference to the Reference object, remove the entry
		// from the WeakHashMap where the WeakReference object serves as that
		// entry's key.
		value = map.remove(r);
		
		// Remove the strong reference to the Employee object, so that object
		// is eligible for garbage collection. Although not necessary in this
		// program, because we are about to exit, imagine a continuously-
		// running program and that this code is in some kind of long-lasting
		// loop.
		value = null;
	}

}
