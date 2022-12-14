package uz.pdp.company.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.company.Entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
