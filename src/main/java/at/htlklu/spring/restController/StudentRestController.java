package at.htlklu.spring.restController;

import at.htlklu.spring.api.ErrorsUtils;
import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.*;
import at.htlklu.spring.repository.StudentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/students")
public class StudentRestController
{
    //region Properties
    private static final Logger logger = LogManager.getLogger(StudentRestController.class);
    private static final String className = "StudentRestController";
    //endregion

    @Autowired
    StudentRepository studentRepository;


    @GetMapping(value = "")
    public ResponseEntity<?> show() {
        logger.info(LogUtils.info(className,"show",String.format("Alle Students")));
        ResponseEntity<?> result;
        List<Student> students = studentRepository.findAll();
        result = new ResponseEntity<>(students,HttpStatus.OK);
        return result;
}


    //http://localhost:8082/students/2/studentSubjects
    @GetMapping(value = "{studentId}/studentSubjects")
    public ResponseEntity<?> getStudentSubjectsByIdPV(@PathVariable Integer studentId) {
        logger.info(LogUtils.info(className, "getStudentSubjectsByIdPV", String.format("(%d)", studentId)));

        ResponseEntity<?> result;
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            List<StudentSubject> studentSubjects = student.getStudentSubjects().stream().collect(Collectors.toList());

            result = new ResponseEntity<List<StudentSubject>>(studentSubjects, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(String.format("StudentSubjects mit der Id = %d nicht vorhanden", studentId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }


    // http://localhost:8082/students/1
    @GetMapping(value = "{studentId}")
    public ResponseEntity<?> getByIdPV(@PathVariable Integer studentId) {
        logger.info(LogUtils.info(className, "getByIdPV", String.format("(%d)", studentId)));
        ResponseEntity<?> result;
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            //    addLinks(student);
            result = new ResponseEntity<Student>(student, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(String.format("Student mit der Id = %d nicht vorhanden", studentId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }


    // http://localhost:8082/students/1/address
    @GetMapping(value = "{studentId}/address")
    public ResponseEntity<?> getAddressByIdPV(@PathVariable Integer studentId) {
        logger.info(LogUtils.info(className, "getAddressByIdPV", String.format("(%d)", studentId)));
        ResponseEntity<?> result;
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            List<Address> addresses = student.getAddresses().stream().collect(Collectors.toList());

            result = new ResponseEntity<List<Address>>(addresses, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(String.format("Student mit der Id = %d nicht vorhanden", studentId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }

    // http://localhost:8082/students/1/absence
    @GetMapping(value = "{studentId}/absence")
    public ResponseEntity<?> getAbsenceByIdPV(@PathVariable Integer studentId) {
        logger.info(LogUtils.info(className, "getAddressByIdPV", String.format("(%d)", studentId)));
        ResponseEntity<?> result;
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            List<Absence> absences = student.getAbsences().stream().collect(Collectors.toList());

            result = new ResponseEntity<List<Absence>>(absences, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(String.format("Student mit der Id = %d nicht vorhanden", studentId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }

    // http://localhost:8082/students/1/studentSubject
    @GetMapping(value = "{studentId}/studentSubject")
    public ResponseEntity<?> getStudentByIdPV(@PathVariable Integer studentId) {
        logger.info(LogUtils.info(className, "getAddressByIdPV", String.format("(%d)", studentId)));
        ResponseEntity<?> result;
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            List<StudentSubject> studentSubjects = student.getStudentSubjects().stream().collect(Collectors.toList());

            result = new ResponseEntity<List<StudentSubject>>(studentSubjects, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(String.format("Student mit der Id = %d nicht vorhanden", studentId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }


    // einfügen einer neuen Ressource
    @PostMapping(value = "")
    public ResponseEntity<?> add(@Valid @RequestBody Student student, BindingResult bindingResult) {

        logger.info(LogUtils.info(className, "add", String.format("(%s)", student)));

        boolean error = false;
        String errorMessage = "";

        if (!error) {
            error = bindingResult.hasErrors();
            errorMessage = bindingResult.toString();
        }
        if (!error) {
            try {
                studentRepository.save(student);
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
                errorMessage = e.getCause().getCause().getLocalizedMessage();
            }
        }
        ResponseEntity<?> result;
        if (!error) {
            result = new ResponseEntity<Student>(student, HttpStatus.OK);

        } else {
            result = new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    // ändern einer vorhandenen Ressource
    @PutMapping(value = "")
    public ResponseEntity<?> update(@Valid @RequestBody Student student,
                                    BindingResult bindingResult) {

        logger.info(LogUtils.info(className, "update", String.format("(%s)", student)));

        boolean error = false;
        String errorMessage = "";

        if (!error) {
            error = bindingResult.hasErrors();
            errorMessage = bindingResult.toString();
        }
        if (!error) {
            try {
                studentRepository.save(student);
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
                errorMessage = e.getCause().getCause().getLocalizedMessage();
            }
        }
        ResponseEntity<?> result;
        if (!error) {
            result = new ResponseEntity<Student>(student, HttpStatus.OK);

        } else {
            result = new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    // delete   PV = Path Variable
    // Ziel: http://localhost:8082/teachers/86 (mit Delete)

    @DeleteMapping(value = "{studentId}")
    public ResponseEntity<?> deletePV(@PathVariable Integer studentId) {

        logger.info(LogUtils.info(className, "deletePV", String.format("%d", studentId)));
        boolean error = false;
        String errorMessage = "";
        Student student= null;
        ResponseEntity<?> result;
        if (!error) {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent())
                student = optionalStudent.get();
            else {
                error = true;
                errorMessage = "Student nicht gefunden";
            }
        }
        if (!error) {
            try {
                studentRepository.delete(student);
            } catch (Exception e) {
                error = true;
                errorMessage = ErrorsUtils.getErrorMessage(e);
            }
        }
        if (!error) {
            result = new ResponseEntity<Student>(student, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
