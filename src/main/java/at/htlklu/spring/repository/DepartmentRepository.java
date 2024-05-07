package at.htlklu.spring.repository;

import at.htlklu.spring.model.Department;
import at.htlklu.spring.model.Teacher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.jpa.repository.EntityGraph.*;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>
{
    @EntityGraph(type = EntityGraphType.FETCH,
            attributePaths = {"teacher",
                    "schoolClasses","schoolClasses.students"})
    List<Department> findByOrderByNameAsc();

    List<Department> findByOrderByDepartmentId();
}
