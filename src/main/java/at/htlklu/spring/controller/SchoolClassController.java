package at.htlklu.spring.controller;
import at.htlklu.spring.api.ErrorsUtils;
import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.*;
import at.htlklu.spring.model.SchoolClass;
import at.htlklu.spring.repository.DepartmentRepository;
import at.htlklu.spring.repository.SchoolClassRepository;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping(value ="mvc/schoolClasses")
public class SchoolClassController {
    //region Properties
	private static Logger logger = LogManager.getLogger(SchoolClassController.class);
	private static final String CLASS_NAME = "SchoolClassController";
	public static final String FORM_NAME_SINGLE = "SchoolClassSingle";
	public static final String FORM_NAME_LIST = "SchoolClassList";

	@Autowired
    SchoolClassRepository schoolClassRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	TeacherRepository teacherRepository;
	//endregion

	// localhost:8082/mvc/schoolClasses
	@GetMapping("")
	public ModelAndView show()
	{
		logger.info(LogUtils.info(CLASS_NAME, "show"));

		ModelAndView mv = new ModelAndView();

		mv.setViewName(SchoolClassController.FORM_NAME_LIST);						// Übergabe der View

		List<SchoolClass> schoolClasses = schoolClassRepository.findAll();			// Sortierung fehlt
		mv.addObject("schoolClasses", schoolClasses);					// Übergabe des Models

	    return mv;
	}


    @GetMapping("{schoolClassId}/students")
    public ModelAndView showStudents(@PathVariable int schoolClassId) {

        logger.info(LogUtils.info(TeacherController.class.getSimpleName(),
                "showStudents", String.format("%d", schoolClassId)));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(StudentController.FORM_NAME_LIST);
        Optional<SchoolClass> optSchoolClass = schoolClassRepository.findById(schoolClassId);

        if (optSchoolClass.isPresent()) { 														// Klasse wurde gefunden, weil Id in Tabelle vorhanden

			SchoolClass schoolClass = optSchoolClass.get();
            List<Student> students = schoolClass.getStudents().stream()
                    .sorted(Student.BY_SURNAME_FIRSTNAME)
                    .collect(Collectors.toList());

			mv.addObject("schoolClass",schoolClass);
			mv.addObject("teacher", schoolClass.getTeacher());
			mv.addObject("students", students);

        } else {
            // Fehlerbehandlung
        }

        return mv;

    }

	//region add und edit Variante 1
	// localhost:8082/mvc/teachers/addEdit
	// localhost:8082/mvc/teachers/addEdit/79

	@GetMapping({"addEdit", "addEdit/{optSchoolClassId}"})
	public ModelAndView addEdit(@PathVariable Optional<Integer> optSchoolClassId) {

		logger.info(LogUtils.info(SchoolClassController.class.getSimpleName(), "addEdit", String.format("%s", optSchoolClassId)));

		ModelAndView mv = new ModelAndView();

		mv.setViewName(SchoolClassController.FORM_NAME_SINGLE);

		int schoolClassId = optSchoolClassId.orElse(-1);	// wenn in der Url die teacherId übergeben wurde, dann wird diese in der Variable gespeichert
																// wenn in der Url keine teacherId übergeben wurde, dann wird -1 in der teacherId gespeichert

		SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId).orElse(new SchoolClass());
		List<Teacher> teachers = teacherRepository.findByOrderBySurnameAscFirstnameAsc();
		List<Department> departments = departmentRepository.findByOrderByDepartmentId();

		mv.addObject("schoolClass", schoolClass);
		mv.addObject("departments", departments);
		mv.addObject("teachers", teachers);

		return mv;
	}

	// localhost:8082/mvc/teachers/save
	// @Valid bedeutet, dass die Attribute vom Teacher gegen die Regeln in der Klasse SchoolClass validiert werden
	// Beispiel für "surname"
	// @NotBlank: der surname darf nicht leer sein oder nur aus Leerzeichen bestehen
	// @Length(min = 3, max = 25) : die Länge des Surnames muss zwischen 3 und 25 Zeichen sein

	@PostMapping("save")
	public ModelAndView save(@Valid @ModelAttribute SchoolClass schoolClass, BindingResult bindingResult) {

		logger.info(LogUtils.info(SchoolClassController.class.getSimpleName(), "save", String.format("%s", schoolClass)));

		boolean error = false;
		ModelAndView mv = new ModelAndView();

		if (!error) {
			//Im bindingResult sind die Fehler gespeichert, dei beim Validieren von teacher hinsichtlich der Anotationen auftreten
			error = bindingResult.hasErrors();      // wenn kein Fehler (ist immer true)

		}
		if (!error) {
			try {
				schoolClassRepository.save(schoolClass);        // wenn kein Fehler dann speichern (in der Datenbank)

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
			mv.setViewName("redirect:/mvc/schoolClasses");
		} else {
			mv.setViewName(SchoolClassController.FORM_NAME_SINGLE);

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
