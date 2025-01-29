package com.fcc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping("/privacypolicy.html")
	public String privacypolicy() {
		return "privacy-policy";
	}
	
	@GetMapping("/t&c.html")
	public String index() {
		return "tc";
		
	}
	
//	@GetMapping("/index")
//	public String home() {
//		return "index";
//		
//	}

}
