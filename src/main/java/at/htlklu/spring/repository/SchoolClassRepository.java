package at.htlklu.spring.repository;

import at.htlklu.spring.model.SchoolClass;
import at.htlklu.spring.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Integer>
{
    List<SchoolClass> findByOrderByLevelAsc();
}
