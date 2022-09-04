package uz.pdp.company.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.company.Entity.Address;
import uz.pdp.company.Entity.Department;
import uz.pdp.company.Entity.Worker;
import uz.pdp.company.Payload.ApiResponse;
import uz.pdp.company.Payload.WorkerDto;
import uz.pdp.company.Repository.AddressRepository;
import uz.pdp.company.Repository.DepartmentRepository;
import uz.pdp.company.Repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AddressRepository addressRepository;

    public List<Worker> getWorker() {
        return workerRepository.findAll();
    }

    public Worker getWorkerById(Integer id) {
        Optional<Worker> workerRepositoryById = workerRepository.findById(id);
        return workerRepositoryById.orElse(null);
    }

    public ApiResponse addWorker(WorkerDto workerDto) {
        if (workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber())) {
            return new ApiResponse("Bunday telefon raqamli ishchi mavjud", false);
        }
        Optional<Department> departmentRepositoryById = departmentRepository.findById(workerDto.getDepartment());
        if (!departmentRepositoryById.isPresent()) {
            return new ApiResponse("Bunday department mavjud emas", false);
        }
        Optional<Address> addressRepositoryById = addressRepository.findById(workerDto.getAddress());
        if (!addressRepositoryById.isPresent()) {
            return new ApiResponse("Bunday address mavjud emas", false);
        }
        Worker worker = new Worker();
        worker.setAddress(addressRepositoryById.get());
        worker.setDepartment(departmentRepositoryById.get());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setName(workerDto.getName());
        workerRepository.save(worker);
        return new ApiResponse("Ishchi xodim qo'shildi", true);
    }

    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {
        boolean existsByPhoneNumberAndIdNot = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (existsByPhoneNumberAndIdNot) {
            return new ApiResponse("Bunday ishchi xodim mavjud", false);
        }
        Optional<Address> addressRepositoryById = addressRepository.findById(workerDto.getAddress());
        if (!addressRepositoryById.isPresent()) {
            return new ApiResponse("Bunday address mavjud emas", false);
        }
        Optional<Department> departmentRepositoryById = departmentRepository.findById(workerDto.getDepartment());
        if (!departmentRepositoryById.isPresent()) {
            return new ApiResponse("Bunday department mavjud emas", false);
        }
        Optional<Worker> workerRepositoryById = workerRepository.findById(id);
        if (!workerRepositoryById.isPresent()) {
            return new ApiResponse("Bunday ishchi xodim mavjud emas", false);
        }
        Worker worker = workerRepositoryById.get();
        worker.setName(workerDto.getName());
        worker.setAddress(addressRepositoryById.get());
        worker.setDepartment(departmentRepositoryById.get());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        workerRepository.save(worker);
        return new ApiResponse("Ishchi xodim ma'lumotlari o'zgardi", true);
    }

    public ApiResponse deleteWorker(Integer id) {
        Optional<Worker> workerRepositoryById = workerRepository.findById(id);
        if (!workerRepositoryById.isPresent()) {
            return new ApiResponse("Bunday ishchi xodim mavjud emas", false);
        }
        workerRepository.deleteById(id);
        return new ApiResponse("Ishchi xodim o'chirildi", true);
    }
}
