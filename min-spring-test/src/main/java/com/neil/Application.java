package com.neil;

import com.neil.starter.MiniApplication;

public class Application {

	public static void main(String[] args) {
		System.out.println("hello mini spring.");

		MiniApplication.run(Application.class, args);
	}
}
