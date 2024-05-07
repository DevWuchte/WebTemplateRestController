package at.htlklu.spring.restController;

import at.htlklu.spring.api.ErrorsUtils;
import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.Department;
import at.htlklu.spring.model.SchoolClass;
import at.htlklu.spring.repository.DepartmentRepository;
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
@RequestMapping("/departments")
public class DepartmentRestController {
    //region Properties
    private static final Logger logger = LogManager.getLogger(DepartmentRestController.class);
    private static final String className = "DepartmentRestController";
    //endregion

    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping(value = "")
    public ResponseEntity<?> show() {
        logger.info(LogUtils.info(className,"show", String.format("Alle Departments")));
        ResponseEntity<?> result;
        List<Department> departments = departmentRepository.findAll();
        result = new ResponseEntity<>(departments, HttpStatus.OK);
        return result;
    }

    //http://localhost:8082/departments/2

    @GetMapping(value = "{departmentId}")
    public ResponseEntity<?> getByIdPV(@PathVariable Integer departmentId) {
        logger.info(LogUtils.info(className, "getByIdPV", String.format("(%d)", departmentId)));

        ResponseEntity<?> result;
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();

            result = new ResponseEntity<Department>(department, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(String.format("SchoolClasses mit der Id = %d nicht vorhanden", departmentId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }



    //http://localhost:8082/departments/2/schoolClasses
   @GetMapping(value = "{departmentId}/schoolClasses")
    public ResponseEntity<?> getSchoolClassByIdPV(@PathVariable Integer departmentId) {
        logger.info(LogUtils.info(className, "getSchoolClassByIdPV", String.format("(%d)", departmentId)));

        ResponseEntity<?> result;
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isPresent()) {
            Department department = optionalDepartment.get();
        List<SchoolClass> schoolClasses = department.getSchoolClasses().stream().sorted(SchoolClass.BY_NAME).collect(Collectors.toList());

            result = new ResponseEntity<List<SchoolClass>>(schoolClasses, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(String.format("SchoolClasses mit der Id = %d nicht vorhanden", departmentId),
                    HttpStatus.NO_CONTENT);
        }
        return result;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> add(@Valid @RequestBody Department department,
                                 BindingResult bindingResult) {

        logger.info(LogUtils.info(className, "add", String.format("(%s)", department)));

        boolean error = false;
        String errorMessage = "";

        if (!error) {
            error = bindingResult.hasErrors();
            errorMessage = bindingResult.toString();
        }

        if (!error) {
            try {
                departmentRepository.save(department);
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
                errorMessage = e.getCause().getCause().getLocalizedMessage();
            }
        }

        ResponseEntity<?> result;
        if (!error) {
            result = new ResponseEntity<Department>(department, HttpStatus.OK);

        } else {
            result = new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;

    }
    @PutMapping(value = "")
    public ResponseEntity<?> update(@Valid @RequestBody Department department,
                                    BindingResult bindingResult) {
        logger.info(LogUtils.info(className, "update", String.format("(%s)", department)));
        boolean error = false;
        String errorMessage = "";
        if (!error) {
            error = bindingResult.hasErrors();
            errorMessage = bindingResult.toString();
        }
        if (!error) {
            try {
                departmentRepository.save(department);
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
                errorMessage = e.getCause().getCause().getLocalizedMessage();
            }
        }
        ResponseEntity<?> result;
        if (!error) {
            result = new ResponseEntity<Department>(department, HttpStatus.OK);
        } else {
            result = new ResponseEntity<String>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    // delete   PV = Path Variable
    // Ziel: http://localhost:8082/teachers/86 (mit Delete)

    @DeleteMapping(value = "{departmentId}")
    public ResponseEntity<?> deletePV(@PathVariable Integer departmentId) {
        logger.info(LogUtils.info(className, "deletePV", String.format("%d", departmentId)));
        boolean error = false;
        String errorMessage = "";
        Department department = null;
        ResponseEntity<?> result;
        if (!error) {
            Optional<Department> optDepartment = departmentRepository.findById(departmentId);
            if (optDepartment.isPresent())
                department = optDepartment.get();
            else {
                error = true;
                errorMessage = "Department nicht gefunden";
            }
        }
        if (!error) {
            try {
                departmentRepository.delete(department);
            } catch (Exception e) {
                error = true;
                errorMessage = ErrorsUtils.getErrorMessage(e);
            }
        }
        if (!error) {
            result = new ResponseEntity<Department>(department, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }




}


