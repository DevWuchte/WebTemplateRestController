package at.htlklu.spring.controller;

import at.htlklu.spring.api.LogUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value ="")
public class MainController
{
	private static Logger logger = LogManager.getLogger(MainController.class);

	private static final String CLASS_NAME = "MainController";


	// localhost:8082/mvc/main
	@GetMapping(value = { "/", "/mvc/Main", "/mvc/main"})
	public String main()
	{
		logger.info(LogUtils.info(CLASS_NAME, "main"));
		return "main.html";
	}

}
