package com.lc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {
	@RequestMapping("/demo")
	public String demo(ModelMap model){
		model.addAttribute("abc","hello");
		return "success";
	}

	public static void main(String[] args) {
		String str = "${plan.principal}";
		for (int i = 0; i <str.length(); i++) {
			System.out.println((int)str.charAt(i));
			System.out.println(i);
			
		}
	}
}
