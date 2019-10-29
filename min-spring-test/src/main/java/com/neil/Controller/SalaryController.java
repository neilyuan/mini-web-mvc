package com.neil.Controller;

import com.neil.beans.Autowired;
import com.neil.service.SalaryService;
import com.neil.web.mvc.Controller;
import com.neil.web.mvc.RequestMapping;
import com.neil.web.mvc.RequestParam;

@Controller
public class SalaryController {

	@Autowired
	SalaryService salaryService;

	@RequestMapping("/salary")
	public String getSalary(@RequestParam("name") String name, @RequestParam("year") String year) {
		int total = salaryService.calSalary(year);
		return "Hi " + name + ", your total salary is " + total;
	}
}
