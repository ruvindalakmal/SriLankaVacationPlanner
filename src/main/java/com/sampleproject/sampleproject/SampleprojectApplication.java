package com.sampleproject.sampleproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
public class SampleprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleprojectApplication.class, args);
		System.out.println("Hello World!");
	}

	@RequestMapping(value = {"/index" , "/"})
	public ModelAndView uiIndexPage() {
		ModelAndView indexPage = new ModelAndView();
		indexPage.setViewName("index.html");
		return indexPage;
	}


}
