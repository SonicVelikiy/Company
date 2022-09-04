package uz.pdp.company.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.company.Entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
