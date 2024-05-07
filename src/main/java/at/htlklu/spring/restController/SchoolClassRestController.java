package at.htlklu.spring.restController;

import at.htlklu.spring.api.ErrorsUtils;
import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.SchoolClass;
import at.htlklu.spring.model.Student;
import at.htlklu.spring.repository.SchoolClassRepository;
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
@RequestMapping("schoolClasses")
public class SchoolClassRestController {
    private static final Logger logger = LogManager.getLogger(SchoolClassRestController.class);
    private static final String className = "SchoolClassRestController";

    @Autowired
    SchoolClassRepository schoolClassRepository;


    @GetMapping(value = "")
    public ResponseEntity<?> show() {
        logger.info(LogUtils.info(className,"show", String.format("Alle SchoolClasses")));
        ResponseEntity<?> result;
        List<SchoolClass> schoolClasses = schoolClassRepository.findAll();
        result = new ResponseEntity<>(schoolClasses, HttpStatus.OK);
        return result;
    }


    // http://localhost:8082/schoolClasses/1
    @GetMapping(value = "{schoolClassId}")
    public ResponseEntity<?> getByIdPV(@PathVariable Integer schoolClassId){
        logger.info(LogUtils.info(className, "getByIdPV", String.format("(%d)",schoolClassId)));
        ResponseEntity<?> result;
        Optional<SchoolClass> optionalSchoolClass = schoolClassRepository.findById(schoolClassId);
        if (optionalSchoolClass.isPresent()){
            SchoolClass schoolClass = optionalSchoolClass.get();
            //    addLinks(teacher);
            result = new ResponseEntity<SchoolClass>(schoolClass, HttpStatus.OK);
        }
        else {
            result = new ResponseEntity<>(String.format("Schulklasse mit der Id = %d nicht vorhanden", schoolClassId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }


    // http://localhost:8082/schoolClasses/1/students
    @GetMapping(value = "{schoolClassId}/students")
    public ResponseEntity<?> getStudentsByIdPV(@PathVariable Integer schoolClassId){
        logger.info(LogUtils.info(className, "getStudentsByIdPV", String.format("(%d)",schoolClassId)));

        ResponseEntity<?> result;
        Optional<SchoolClass> optionalSchoolClass = schoolClassRepository.findById(schoolClassId);
        if (optionalSchoolClass.isPresent()){
            SchoolClass schoolClass = optionalSchoolClass.get();
            //    addLinks(teacher);
            List<Student> students = schoolClass.getStudents().stream()
                    .sorted(Student.BY_SURNAME_FIRSTNAME)
                    .collect(Collectors.toList());
            result = new ResponseEntity<List<Student>>(students, HttpStatus.OK);
        }
        else {
            result = new ResponseEntity<>(String.format("Schulklasse mit der Id = %d nicht vorhanden", schoolClassId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }

    // einfügen einer neuen Ressource
    @PostMapping(value = "")
    public ResponseEntity<?> add(@Valid @RequestBody SchoolClass schoolClass,
                                 BindingResult bindingResult) {
        logger.info(LogUtils.info(className, "add", String.format("(%s)", schoolClass)));
        boolean error = false;
        String errorMessage = "";
        if (!error) {
            error = bindingResult.hasErrors();
            errorMessage = bindingResult.toString();
        }
        if (!error) {
            try {
                schoolClassRepository.save(schoolClass);
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
                errorMessage = e.getCause().getCause().getLocalizedMessage();
            }
        }
        ResponseEntity<?> result;
        if (!error) {
            result = new ResponseEntity<SchoolClass>(schoolClass, HttpStatus.OK);

        } else {
            result = new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    // ändern einer vorhandenen Ressource
    @PutMapping(value = "")
    public ResponseEntity<?> update(@Valid @RequestBody SchoolClass schoolClass,
                                    BindingResult bindingResult) {
        logger.info(LogUtils.info(className, "update", String.format("(%s)", schoolClass)));
        boolean error = false;
        String errorMessage = "";
        if (!error) {
            error = bindingResult.hasErrors();
            errorMessage = bindingResult.toString();
        }
        if (!error) {
            try {
                schoolClassRepository.save(schoolClass);
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
                errorMessage = e.getCause().getCause().getLocalizedMessage();
            }
        }
        ResponseEntity<?> result;
        if (!error) {
            result = new ResponseEntity<SchoolClass>(schoolClass, HttpStatus.OK);
        } else {
            result = new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    // delete   PV = Path Variable
    // Ziel: http://localhost:8082/schoolClass/1 (mit Delete)
    @DeleteMapping(value = "{schoolClassId}")
    public ResponseEntity<?> deletePV(@PathVariable Integer schoolClassId) {
        logger.info(LogUtils.info(className, "deletePV", String.format("%d", schoolClassId)));
        boolean error = false;
        String errorMessage = "";
        SchoolClass schoolClass  = null;
        ResponseEntity<?> result;
        if (!error) {
            Optional<SchoolClass> optionalSchoolClass = schoolClassRepository.findById(schoolClassId);
            if (optionalSchoolClass.isPresent())
                schoolClass = optionalSchoolClass.get();
            else {
                error = true;
                errorMessage = "SchoolClass nicht gefunden";
            }
        }
        if (!error) {
            try {
                schoolClassRepository.delete(schoolClass);
            } catch (Exception e) {
                error = true;
                errorMessage = ErrorsUtils.getErrorMessage(e);
            }
        }
        if (!error) {
            result = new ResponseEntity<SchoolClass>(schoolClass, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }



}

