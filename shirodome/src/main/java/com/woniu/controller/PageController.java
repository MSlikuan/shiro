package com.woniu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
	@RequestMapping("index")
	public ModelAndView longin() {
		return new ModelAndView("page/index");
	}
}
