package at.htlklu.spring.repository;

import at.htlklu.spring.model.SchoolClass;
import at.htlklu.spring.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>
{
}
