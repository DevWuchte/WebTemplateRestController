package at.htlklu.spring.restController;

import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.StudentSubject;
import at.htlklu.spring.repository.StudentSubjectRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/studentSubjects")
public class StudentSubjectRestController {
    private static final Logger logger = LogManager.getLogger(StudentSubjectRestController.class);
    private static final String className = "StudentSubjectRestController";

    @Autowired
    StudentSubjectRepository studentSubjectRepository;

    @GetMapping(value = "")
    public ResponseEntity<?> show() {
        logger.info(LogUtils.info(className, "show", "Alle StudentSubjects"));
        ResponseEntity<?> result;
        List<StudentSubject> studentSubjects = studentSubjectRepository.findAll();
        result = new ResponseEntity<>(studentSubjects, HttpStatus.OK);
        return result;
    }

    @GetMapping(value = "/{studentSubjectId}")
    public ResponseEntity<?> getByIdPV(@PathVariable Integer studentSubjectId) {
        logger.info(LogUtils.info(className, "getByIdPV", String.format("(%d)", studentSubjectId)));

        ResponseEntity<?> result;
        Optional<StudentSubject> optionalStudentSubject = studentSubjectRepository.findById(studentSubjectId);
        if (optionalStudentSubject.isPresent()) {
            StudentSubject studentSubject = optionalStudentSubject.get();
            result = new ResponseEntity<>(studentSubject, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(String.format("StudentSubject mit der Id = %d nicht vorhanden", studentSubjectId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }
}
