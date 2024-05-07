package at.htlklu.spring.controller;
import at.htlklu.spring.api.ErrorsUtils;
import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.*;
import at.htlklu.spring.repository.SchoolClassRepository;
import at.htlklu.spring.repository.StudentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value ="mvc/students")
public class StudentController {
    //region Properties
    private static Logger logger = LogManager.getLogger(StudentController.class);
    private static final String CLASS_NAME = "StudentController";
    public static final String FORM_NAME_SINGLE = "StudentSingle";
    public static final String FORM_NAME_LIST = "StudentList";

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SchoolClassRepository schoolClassRepository;
    //endregion

    // localhost:8082/mvc/students
    @GetMapping("")
    public ModelAndView show()
    {
        logger.info(LogUtils.info(CLASS_NAME, "show"));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(StudentController.FORM_NAME_LIST);				// Übergabe der View

        List<Student> students = studentRepository.findAll();			// Sortierung fehlt
        mv.addObject("students", students);					// Übergabe des Models

        return mv;
    }

    @GetMapping("{studentId}/studentSubjects")
    public ModelAndView showStudentSubject(@PathVariable int studentId) {

        logger.info(LogUtils.info(StudentController.class.getSimpleName(),
                "showStudentSubjects", String.format("%d", studentId)));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(StudentSubjectController.FORM_NAME_LIST);
        Optional<Student> optStudent = studentRepository.findById(studentId);

        if (optStudent.isPresent()) { // Teacher wurde gefunden, weil Id in Tabelle vorhanden

            Student student = optStudent.get();
            List<StudentSubject> studentSubjects = student.getStudentSubjects().stream()
                    .sorted(Comparator.comparing(s -> s.getStudentSubjectId()))
                    .collect(Collectors.toList());

            mv.addObject("studentSubjects", studentSubjects);
            mv.addObject("student",student);

        } else {
            // Fehlerbehandlung
        }

        return mv;

    }

    @GetMapping("{studentId}/absences")
    public ModelAndView showStudentAbsence(@PathVariable int studentId) {

        logger.info(LogUtils.info(StudentController.class.getSimpleName(),
                "showStudentAbsences", String.format("%d", studentId)));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(AbsenceController.FORM_NAME_LIST);
        Optional<Student> optStudent = studentRepository.findById(studentId);

        if (optStudent.isPresent()) { // Teacher wurde gefunden, weil Id in Tabelle vorhanden

            Student student = optStudent.get();
            List<Absence> absences = student.getAbsences().stream()
                    .sorted(Comparator.comparing(s -> s.getReason()))
                    .collect(Collectors.toList());

            mv.addObject("absences", absences);
            mv.addObject("student",student);

        } else {
            // Fehlerbehandlung
        }

        return mv;

    }

     @GetMapping("{studentId}/addresses")
    public ModelAndView showAddresses(@PathVariable int studentId) {

        logger.info(LogUtils.info(StudentController.class.getSimpleName(),
                "showStudentAddresses", String.format("%d", studentId)));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(AddressController.FORM_NAME_LIST);
        Optional<Student> optStudent = studentRepository.findById(studentId);

        if (optStudent.isPresent()) { //

            Student student = optStudent.get();
            List<Address> addresses = student.getAddresses().stream()
                    .sorted(Comparator.comparing(s -> s.getCity()))
                    .collect(Collectors.toList());

            mv.addObject("addresses", addresses);
            mv.addObject("student",student);

        } else {
            // Fehlerbehandlung
        }

        return mv;
    }

    @GetMapping({"addEdit", "addEdit/{optStudentId}"})
    public ModelAndView addEdit(@PathVariable Optional<Integer> optStudentId) {

        logger.info(LogUtils.info(StudentController.class.getSimpleName(), "addEdit", String.format("%s", optStudentId)));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(StudentController.FORM_NAME_SINGLE);

        int studentId = optStudentId.orElse(-1);	// wenn in der Url die teacherId übergeben wurde, dann wird diese in der Variable gespeichert
        // wenn in der Url keine teacherId übergeben wurde, dann wird -1 in der teacherId gespeichert

        Student student = studentRepository.findById(studentId).orElse(new Student());
        List<SchoolClass> schoolClasses = schoolClassRepository.findByOrderByLevelAsc();

        mv.addObject("schoolCalsses", schoolClasses);
        mv.addObject("student", student);

        return mv;
    }

    // localhost:8082/mvc/teachers/save
    // @Valid bedeutet, dass die Attribute vom Teacher gegen die Regeln in der Klasse SchoolClass validiert werden
    // Beispiel für "surname"
    // @NotBlank: der surname darf nicht leer sein oder nur aus Leerzeichen bestehen
    // @Length(min = 3, max = 25) : die Länge des Surnames muss zwischen 3 und 25 Zeichen sein

    @PostMapping("save")
    public ModelAndView save(@Valid @ModelAttribute Student student, BindingResult bindingResult) {

        logger.info(LogUtils.info(StudentController.class.getSimpleName(), "save", String.format("%s", student)));

        boolean error = false;
        ModelAndView mv = new ModelAndView();

        if (!error) {
            //Im bindingResult sind die Fehler gespeichert, dei beim Validieren von teacher hinsichtlich der Anotationen auftreten
            error = bindingResult.hasErrors();      // wenn kein Fehler (ist immer true)

        }
        if (!error) {
            try {
                studentRepository.save(student);        // wenn kein Fehler dann speichern (in der Datenbank)

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
            mv.setViewName("redirect:/mvc/students");
        } else {
            mv.setViewName(StudentController.FORM_NAME_SINGLE);

        }

        //die Objekte "teacher" (wegen der Annotation @ModelAttribute) und
        //"bindingResult" werden automatisch als Attribite beim Model "mv" hinzugefügt (add)
        // d.h. die Aufrufe mv.addObject("teacher" teacher); und mv.addObject("bindingResult), bindingResult);
        //sind nicht notwendig und sinnvoll

        return mv;
    }

}
