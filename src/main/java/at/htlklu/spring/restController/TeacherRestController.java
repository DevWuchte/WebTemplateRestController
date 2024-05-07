package at.htlklu.spring.restController;

import at.htlklu.spring.api.ErrorsUtils;
import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.SchoolClass;
import at.htlklu.spring.model.Teacher;
import at.htlklu.spring.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("teachers")
public class TeacherRestController {
    private static final Logger logger = LogManager.getLogger(TeacherRestController.class);
    private static final String className = "TeacherRestController";

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping(value = "")
    public ResponseEntity<?> show() {
        logger.info(LogUtils.info(className,"show", String.format("Alle Teacher")));
        ResponseEntity<?> result;
        List<Teacher> teachers = teacherRepository.findAll();
        result = new ResponseEntity<>(teachers, HttpStatus.OK);
        return result;
    }


    // http://localhost:8082/teachers/1
    @GetMapping(value = "{teacherId}")
    public ResponseEntity<?> getByIdPV(@PathVariable Integer teacherId) {
        logger.info(LogUtils.info(className, "getByIdPV", String.format("(%d)", teacherId)));
        ResponseEntity<?> result;
        Optional<Teacher> optTeacher = teacherRepository.findById(teacherId);
        if (optTeacher.isPresent()) {
            Teacher teacher = optTeacher.get();
            //    addLinks(teacher);
            result = new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(String.format("Lehrer/in mit der Id = %d nicht vorhanden", teacherId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }


    // http://localhost:8082/teachers/1/schoolClasses
    @GetMapping(value = "{teacherId}/schoolClasses")
    public ResponseEntity<?> getSchoolClassByIdPV(@PathVariable Integer teacherId) {
        logger.info(LogUtils.info(className, "getSchoolClassByIdPV", String.format("(%d)", teacherId)));
        ResponseEntity<?> result;
        Optional<Teacher> optTeacher = teacherRepository.findById(teacherId);
        if (optTeacher.isPresent()) {
            Teacher teacher = optTeacher.get();
            List<SchoolClass> schoolClasses = teacher.getSchoolClasses().stream()
                    .sorted(SchoolClass.BY_NAME)
                    .collect(Collectors.toList());
            result = new ResponseEntity<List<SchoolClass>>(schoolClasses, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(String.format("Lehrer/in mit der Id = %d nicht vorhanden", teacherId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }

    // einfügen einer neuen Ressource
    @PostMapping(value = "")
    public ResponseEntity<?> add(@Valid @RequestBody Teacher teacher,
                                 BindingResult bindingResult) {

        logger.info(LogUtils.info(className, "add", String.format("(%s)", teacher)));

        boolean error = false;
        String errorMessage = "";

        if (!error) {
            error = bindingResult.hasErrors();
            errorMessage = bindingResult.toString();
        }

        if (!error) {
            try {
                teacherRepository.save(teacher);
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
                errorMessage = e.getCause().getCause().getLocalizedMessage();
            }
        }

        ResponseEntity<?> result;
        if (!error) {
            result = new ResponseEntity<Teacher>(teacher, HttpStatus.OK);

        } else {
            result = new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;

    }

    // ändern einer vorhandenen Ressource
    @PutMapping(value = "")
    public ResponseEntity<?> update(@Valid @RequestBody Teacher teacher,
                                    BindingResult bindingResult) {

        logger.info(LogUtils.info(className, "update", String.format("(%s)", teacher)));

        boolean error = false;
        String errorMessage = "";

        if (!error) {
            error = bindingResult.hasErrors();
            errorMessage = bindingResult.toString();
        }
        if (!error) {
            try {
                teacherRepository.save(teacher);
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
                errorMessage = e.getCause().getCause().getLocalizedMessage();
            }
        }
        ResponseEntity<?> result;
        if (!error) {
            result = new ResponseEntity<Teacher>(teacher, HttpStatus.OK);

        } else {
            result = new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    // delete   PV = Path Variable
    // Ziel: http://localhost:8082/teachers/86 (mit Delete)

    @DeleteMapping(value = "{teacherId}")
    public ResponseEntity<?> deletePV(@PathVariable Integer teacherId) {

        logger.info(LogUtils.info(className, "deletePV", String.format("%d", teacherId)));
        boolean error = false;
        String errorMessage = "";
        Teacher teacher = null;
        ResponseEntity<?> result;
        if (!error) {
            Optional<Teacher> optTeacher = teacherRepository.findById(teacherId);
            if (optTeacher.isPresent())
                teacher = optTeacher.get();
            else {
                error = true;
                errorMessage = "Teacher nicht gefunden";
            }
        }
        if (!error) {
            try {
                teacherRepository.delete(teacher);
            } catch (Exception e) {
                error = true;
                errorMessage = ErrorsUtils.getErrorMessage(e);
            }
        }
        if (!error) {
            result = new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



}

