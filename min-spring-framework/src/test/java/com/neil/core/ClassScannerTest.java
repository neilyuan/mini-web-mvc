package com.neil.core;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ClassScannerTest {

	@Test
	public void scan() {

		try {
			List<Class<?>> classList = ClassScanner.scan("com.neil");
			System.out.println(classList.size());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}