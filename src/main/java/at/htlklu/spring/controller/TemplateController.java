package at.htlklu.spring.controller;

import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.Teacher;
import at.htlklu.spring.repository.TeacherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value ="/mvc/templates")
public class TemplateController
{
	//region Properties
	private static Logger logger = LogManager.getLogger(TemplateController.class);
	private static final String CLASS_NAME = "TemplateController";
	public static final String FORM_NAME_SINGLE = "TemplateSingle";
	public static final String FORM_NAME_LIST = "TemplateList";
	//endregion
}
