package at.htlklu.spring.repository;

import at.htlklu.spring.model.Absence;
import at.htlklu.spring.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>
{
}
