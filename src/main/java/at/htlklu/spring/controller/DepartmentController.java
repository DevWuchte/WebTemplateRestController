package at.htlklu.spring.controller;
import at.htlklu.spring.api.ErrorsUtils;
import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.Department;
import at.htlklu.spring.model.SchoolClass;
import at.htlklu.spring.model.Teacher;
import at.htlklu.spring.repository.DepartmentRepository;
import at.htlklu.spring.repository.TeacherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value ="mvc/departments")

public class DepartmentController {

    //region Properties
    private static Logger logger = LogManager.getLogger(DepartmentController.class);
    private static final String CLASS_NAME = "DepartmentController";
    public static final String FORM_NAME_SINGLE = "DepartmentSingle";
    public static final String FORM_NAME_LIST = "DepartmentList";

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    //endregion

    // localhost:8082/mvc/departments
    @GetMapping("")
    public ModelAndView show()
    {
        logger.info(LogUtils.info(CLASS_NAME, "show"));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(DepartmentController.FORM_NAME_LIST);                        // Übergabe der View

        // Variante 1: ohne Optimierung
        // List<Department> departments = departmentRepository.findAll();

        // Variante 2: mit Optimierung
        List<Department> departments = departmentRepository.findByOrderByNameAsc(); // Sortierung fehlt

        mv.addObject("departments", departments);                       // Übergabe des Models

        return mv;
    }


    @GetMapping("{departmentId}/schoolClasses")
    public ModelAndView showSchoolClasses(@PathVariable int departmentId) {

        logger.info(LogUtils.info(DepartmentController.class.getSimpleName(),
                "show", String.format("%d", departmentId)));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(SchoolClassController.FORM_NAME_LIST);
        Optional<Department> optDepartment = departmentRepository.findById(departmentId);

        if (optDepartment.isPresent()) { // Teacher wurde gefunden, weil Id in Tabelle vorhanden

            Department department = optDepartment.get();
            List<SchoolClass> schoolClasses = department.getSchoolClasses().stream()
                    .sorted(SchoolClass.BY_NAME)
                    .collect(Collectors.toList());

            mv.addObject("department", department);
            mv.addObject("schoolClasses", schoolClasses);

        } else {
            // Fehlerbehandlung
        }

        return mv;

    }

    // region AddEdit
    @GetMapping({"addEdit", "addEdit/{optDepartmentId}"})
    public ModelAndView addEdit(@PathVariable Optional<Integer> optDepartmentId) {

        logger.info(LogUtils.info(DepartmentController.class.getSimpleName(), "addEdit", String.format("%s", optDepartmentId)));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(DepartmentController.FORM_NAME_SINGLE);

        int departmentId = optDepartmentId.orElse(-1);       // wenn in der Url die teacherId übergeben wurde, dann wird diese in der Variable gespeichert
        // wenn in der Url keine teacherId übergeben wurde, dann wird -1 in der teacherId gespeichert

        Department department = departmentRepository.findById(departmentId).orElse(new Department());

        // Variante 1: Nachteil -> nicht sortiert!!
        // List<Teacher> teachers = teacherRepository.findAll();

        // Variante 2: mehr oder weniger optimal, kleiner Nachteil -> Sortierung erfolgt erst in Controller
//        List<Teacher> teachers = teacherRepository.findAll()
//                                                  .stream().sorted(Teacher.BY_SurnameFirstname)
//                                                  .collect(Collectors.toList());

        // Variante 3: Sortierung geschieht in Teacher Respository -> optimal
        List<Teacher> teachers = teacherRepository.findByOrderBySurnameAscFirstnameAsc();

        mv.addObject("department", department);
        mv.addObject("teachers", teachers);

        return mv;
    }

    @PostMapping("save")
    public ModelAndView save(@Valid @ModelAttribute Department department, BindingResult bindingResult) {

        logger.info(LogUtils.info(DepartmentController.class.getSimpleName(), "save", String.format("%s", department)));

        boolean error = false;
        ModelAndView mv = new ModelAndView();

        if (!error) {
            //Im bindingResult sind die Fehler gespeichert, dei beim Validieren von teacher hinsichtlich der Anotationen auftreten
            error = bindingResult.hasErrors();      // wenn kein Fehler (ist immer true)

        }
        if (!error) {
            try {
                departmentRepository.save(department);        // wenn kein Fehler dann speichern (in der Datenbank)

            } catch (Exception e) {

                //mögliche Fehler, die auftreten können
                //kein Netzwerk (Internet)
                //keine Berechtigung
                //ShortName nicht eindeutig
                error = true;
                logger.error(LogUtils.info(CLASS_NAME, "save", ErrorsUtils.getErrorMessage(e)));
                bindingResult.addError(new ObjectError("globalError", ErrorsUtils.getErrorMessage(e)));

            }
        } if (!error){
            mv.setViewName("redirect:/mvc/departments");
        } else {
            mv.setViewName(DepartmentController.FORM_NAME_SINGLE);

        }

        return mv;
    }
    // end region

}
