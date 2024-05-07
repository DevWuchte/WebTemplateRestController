package at.htlklu.spring.controller;

import at.htlklu.spring.api.ErrorsUtils;
import at.htlklu.spring.model.*;
import at.htlklu.spring.repository.*;

import at.htlklu.spring.api.LogUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// Ziel:
// localhost:8082/mvc/teachers/
// localhost:8082/mvc/teachers/id/departments
// localhost:8082/mvc/teachers/id/schoolClasses

@Controller
@RequestMapping(value = "mvc/teachers")
public class TeacherController {
    //region Properties
    private static Logger logger = LogManager.getLogger(TeacherController.class);
    private static final String CLASS_NAME = "TeacherController";
    public static final String FORM_NAME_SINGLE = "TeacherSingle";
    public static final String FORM_NAME_LIST = "TeacherList";

    @Autowired
    TeacherRepository teacherRepository;
    //endregion

    //region show
    // localhost:8082/mvc/teachers
    @GetMapping("")
    public ModelAndView show() {
        logger.info(LogUtils.info(CLASS_NAME, "show"));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(TeacherController.FORM_NAME_LIST);                // Übergabe der View

        List<Teacher> teachers = teacherRepository.findAll();            // Sortierung fehlt
        mv.addObject("teachers", teachers);                  // Übergabe des Models

        return mv;
    }

    //localhost:8082/mvc/teachers/105/departments
    @GetMapping("{teacherId}/departments")
    public ModelAndView showDepartments(@PathVariable int teacherId) {

        logger.info(LogUtils.info(TeacherController.class.getSimpleName(),
                "showDepartments", String.format("%d", teacherId)));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(DepartmentController.FORM_NAME_LIST);
        Optional<Teacher> optTeacher = teacherRepository.findById(teacherId);

        if (optTeacher.isPresent()) { // Teacher wurde gefunden, weil Id in Tabelle vorhanden

            Teacher teacher = optTeacher.get();
            List<Department> departments = teacher.getDepartments().stream()
                    .sorted(Department.BY_NAME)
                    .collect(Collectors.toList());

            mv.addObject("departments", departments);

        } else {
            // Fehlerbehandlung
        }

        return mv;

    }

    //localhost:8082/mvc/teachers
    @GetMapping("{teacherId}/schoolClasses")
    public ModelAndView showSchoolClasses(@PathVariable int teacherId) {

        logger.info(LogUtils.info(TeacherController.class.getSimpleName(),
                "showSchoolClasses", String.format("%d", teacherId)));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(SchoolClassController.FORM_NAME_LIST);
        Optional<Teacher> optTeacher = teacherRepository.findById(teacherId);

        if (optTeacher.isPresent()) { // Teacher wurde gefunden, weil Id in Tabelle vorhanden

            Teacher teacher = optTeacher.get();
            List<SchoolClass> schoolClasses = teacher.getSchoolClasses().stream()
                    .sorted(SchoolClass.BY_NAME)
                    .collect(Collectors.toList());

            mv.addObject("schoolClasses", schoolClasses);

        } else {
            // Fehlerbehandlung
        }

        return mv;

    }
    //endregion

    //region add and edit
    // localhost:8082/mvc/teachers/add
    @GetMapping("add")
    public ModelAndView add() {

        logger.info(LogUtils.info(TeacherController.class.getSimpleName(), "add"));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(TeacherController.FORM_NAME_SINGLE);

        Teacher teacher = new Teacher();

        mv.addObject("teacher", teacher);
        return mv;
    }

    // localhost:8082/mvc/teachers/edit/79
    @GetMapping("edit/{teacherId}")
    public ModelAndView edit(@PathVariable int teacherId) {

        logger.info(LogUtils.info(TeacherController.class.getSimpleName(), "edit", String.format("%d", teacherId)));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(TeacherController.FORM_NAME_SINGLE);

        Optional<Teacher> optTeacher = teacherRepository.findById(teacherId);
        if (optTeacher.isPresent()) {
            Teacher teacher = optTeacher.get();
            mv.addObject("teacher", teacher);
        } else {
            //to do error handling
        }

        Teacher teacher = new Teacher();

        mv.addObject("teacher", teacher);
        return mv;
    }

    //region add und edit Variante 1
    // localhost:8082/mvc/teachers/addEdit
    // localhost:8082/mvc/teachers/addEdit/79

    @GetMapping({"addEdit", "addEdit/{optTeacherId}"})
    public ModelAndView addEdit(@PathVariable Optional<Integer> optTeacherId) {

        logger.info(LogUtils.info(TeacherController.class.getSimpleName(), "addEdit", String.format("%s", optTeacherId)));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(TeacherController.FORM_NAME_SINGLE);

        int teacherId = optTeacherId.orElse(-1);       // wenn in der Url die teacherId übergeben wurde, dann wird diese in der Variable gespeichert
                                                            // wenn in der Url keine teacherId übergeben wurde, dann wird -1 in der teacherId gespeichert

        Teacher teacher = teacherRepository.findById(teacherId).orElse(new Teacher());

        mv.addObject("teacher", teacher);

        return mv;
    }

    // localhost:8082/mvc/teachers/save
    // @Valid bedeutet, dass die Attribute vom Teacher gegen die Regeln in der Klasse Teacehr validiert werden
    // Beispiel für "surname"
    // @NotBlank: der surname darf nicht leer sein oder nur aus Leerzeichen bestehen
    // @Length(min = 3, max = 25) : die Länge des Surnames muss zwischen 3 und 25 Zeichen sein

    @PostMapping("save")
    public ModelAndView save(@Valid @ModelAttribute Teacher teacher, BindingResult bindingResult) {

        logger.info(LogUtils.info(TeacherController.class.getSimpleName(), "save", String.format("%s", teacher)));

        boolean error = false;
        ModelAndView mv = new ModelAndView();

        if (!error) {
                                                    // Im bindingResult sind die Fehler gespeichert, dei beim Validieren von teacher hinsichtlich der Anotationen auftreten
            error = bindingResult.hasErrors();      // wenn kein Fehler (ist immer true)

        }
        if (!error) {
            try {
                teacherRepository.save(teacher);        // wenn kein Fehler dann speichern (in der Datenbank)

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
            mv.setViewName("redirect:/mvc/teachers");
        } else {
            mv.setViewName(TeacherController.FORM_NAME_SINGLE);

        }

        //die Objekte "teacher" (wegen der Annotation @ModelAttribute) und
        //"bindingResult" werden automatisch als Attribite beim Model "mv" hinzugefügt (add)
        // d.h. die Aufrufe mv.addObject("teacher" teacher); und mv.addObject("bindingResult), bindingResult);
        //sind nicht notwendig und sinnvoll

        return mv;
    }
    //endregion
    //endregion

}
