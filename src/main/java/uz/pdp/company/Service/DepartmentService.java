package uz.pdp.company.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.company.Entity.Company;
import uz.pdp.company.Entity.Department;
import uz.pdp.company.Payload.ApiResponse;
import uz.pdp.company.Payload.DepartmentDto;
import uz.pdp.company.Repository.CompanyRepository;
import uz.pdp.company.Repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getDepartment() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Integer id) {
        Optional<Department> departmentRepositoryById = departmentRepository.findById(id);
        return departmentRepositoryById.orElse(null);
    }

    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean existsByName = departmentRepository.existsByName(departmentDto.getName());
        if (existsByName) {
            return new ApiResponse("Bunday departament mavjud", false);
        }

        Optional<Company> companyRepositoryById = companyRepository.findById(departmentDto.getCompany());
        if (!companyRepositoryById.isPresent()) {
            return new ApiResponse("Bunday company mavjud emas", false);
        }
        Department department = new Department();
        department.setCompany(companyRepositoryById.get());
        department.setName(departmentDto.getName());
        departmentRepository.save(department);
        return new ApiResponse("Departament qo'shildi", true);
    }

    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {
        Optional<Department> departmentRepositoryById = departmentRepository.findById(id);
        if (!departmentRepositoryById.isPresent()) {
            return new ApiResponse("Bunday department topilmadi", false);
        }
        boolean existsByNameAndIdNot = departmentRepository.existsByNameAndIdNot(departmentDto.getName(), id);
        if (existsByNameAndIdNot) {
            return new ApiResponse("Bunday department mavjud", false);
        }

        Optional<Company> companyRepositoryById = companyRepository.findById(departmentDto.getCompany());
        if (!companyRepositoryById.isPresent()) {
            return new ApiResponse("Bunday company mavjud emas", false);
        }
        Department department = departmentRepositoryById.get();
        department.setCompany(companyRepositoryById.get());
        department.setName(departmentDto.getName());
        departmentRepository.save(department);
        return new ApiResponse("Department o'zgartirildi", true);
    }

    public ApiResponse deleteDepartment(Integer id) {
        Optional<Department> departmentRepositoryById = departmentRepository.findById(id);
        if (!departmentRepositoryById.isPresent()) {
            return new ApiResponse("Bunday department topilmadi", false);
        }

        departmentRepository.deleteById(id);

        return new ApiResponse("Department o'chirildi", true);
    }
}
