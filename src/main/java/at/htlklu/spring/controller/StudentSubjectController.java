package at.htlklu.spring.controller;


import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.SchoolClass;
import at.htlklu.spring.model.StudentSubject;
import at.htlklu.spring.repository.SchoolClassRepository;
import at.htlklu.spring.repository.StudentSubjectRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value ="mvc/studentSubjects")
public class StudentSubjectController {
    //region Properties
    private static Logger logger = LogManager.getLogger(StudentSubjectController.class);
    private static final String CLASS_NAME = "StudentSubjectController";
    public static final String FORM_NAME_SINGLE = "StudentSubjectSingle";
    public static final String FORM_NAME_LIST = "StudentSubjectList";

    @Autowired
    StudentSubjectRepository studentSubjectRepository;
    //endregion

    // localhost:8082/mvc/studentSubjects
    @GetMapping("")
    public ModelAndView show()
    {
        logger.info(LogUtils.info(CLASS_NAME, "show"));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(StudentSubjectController.FORM_NAME_LIST);						// Übergabe der View

        List<StudentSubject> studentSubjects = studentSubjectRepository.findAll();		// Sortierung fehlt
        mv.addObject("studentSubjects", studentSubjects);					// Übergabe des Models

        return mv;
    }
}