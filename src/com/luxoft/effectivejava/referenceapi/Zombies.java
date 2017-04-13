package com.luxoft.effectivejava.referenceapi;

import java.util.ArrayList;
import java.util.List;

public class Zombies {
	private int num;
	static final List<Zombies> ZOMBIES = new ArrayList<Zombies>();

	public Zombies(int num) {
		this.num = num;
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Resurrect " + num);
		ZOMBIES.add(this);
	}

	@Override
	public String toString() {
		return "Zombies{" + "num=" + num + '}';
	}

	public static void main(String... args) throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			ZOMBIES.add(new Zombies(i));
		}
		for (int j = 0; j < 5; j++) {
			System.out.println("Zombies: " + ZOMBIES);
			ZOMBIES.clear();
			System.gc();
			Thread.sleep(100);
		}
	}

}
