package at.htlklu.spring.controller;

import at.htlklu.spring.model.*;
import at.htlklu.spring.repository.*;

import at.htlklu.spring.api.LogUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// Ziel:
// localhost:8082/mvc/teachers/
// localhost:8082/mvc/teachers/id/departments
// localhost:8082/mvc/teachers/id/schoolClasses

@Controller
@RequestMapping(value = "mvc/absences")
public class AbsenceController {
    //region Properties
    private static Logger logger = LogManager.getLogger(AbsenceController.class);
    private static final String CLASS_NAME = "AbsenceController";
    public static final String FORM_NAME_SINGLE = "AbsenceSingle";
    public static final String FORM_NAME_LIST = "AbsenceList";

    @Autowired
    AbsenceRepository absenceRepository;
    //endregion


    // localhost:8082/mvc/absences

    @GetMapping("")
    public ModelAndView show() {
        logger.info(LogUtils.info(CLASS_NAME, "show"));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(AbsenceController.FORM_NAME_LIST);                // Übergabe der View

        List<Absence> absences = absenceRepository.findAll();            // Sortierung fehlt
        mv.addObject("absences", absences);                  // Übergabe des Models

        return mv;
    }
}

