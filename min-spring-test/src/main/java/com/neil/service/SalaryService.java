package com.neil.service;

import com.neil.beans.Bean;

@Bean
public class SalaryService {

	public int calSalary(String year) {
		int base = 1000;
		return base * Integer.parseInt(year);
	}
}
