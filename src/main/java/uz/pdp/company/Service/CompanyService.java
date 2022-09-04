package uz.pdp.company.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.company.Entity.Address;
import uz.pdp.company.Entity.Company;
import uz.pdp.company.Payload.ApiResponse;
import uz.pdp.company.Payload.CompanyDto;
import uz.pdp.company.Repository.AddressRepository;
import uz.pdp.company.Repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;

    public List<Company> getCompany() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer id) {
        Optional<Company> companyRepositoryById = companyRepository.findById(id);
        return companyRepositoryById.orElse(null);
    }

    public ApiResponse addCompany(CompanyDto companyDto) {
        Integer addressId = companyDto.getAddress();
        Optional<Address> addressRepositoryById = addressRepository.findById(addressId);
        if (!addressRepositoryById.isPresent()) {
            return new ApiResponse("Address not found", false);
        }
        if (companyRepository.existsByCorpName(companyDto.getCorpName())) {
            return new ApiResponse("Bunday corpName mavjud", false);
        }

        Company company = new Company();
        company.setAddress(addressRepositoryById.get());
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);
        return new ApiResponse("Company muvvafiqiyayli qo'shildi", true);
    }

    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        boolean existsByCorpNameAndIdNot = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (existsByCorpNameAndIdNot) {
            return new ApiResponse("Bunday company nomi mavjud", false);
        }
        Optional<Address> addressRepositoryById = addressRepository.findById(companyDto.getAddress());
        if (!addressRepositoryById.isPresent()) {
            return new ApiResponse("Bunday address mavjud emas", false);
        }
        Optional<Company> companyRepositoryById = companyRepository.findById(id);
        if (!companyRepositoryById.isPresent()){
            return new ApiResponse("Bunday company mavjud emas", false);
        }
        Company company = companyRepositoryById.get();
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(addressRepositoryById.get());
        company.setCorpName(companyDto.getCorpName());
        companyRepository.save(company);
        return new ApiResponse("Company o'zgartirildi", true);
    }

    public ApiResponse deleteCompany(Integer id) {
        Optional<Company> companyRepositoryById = companyRepository.findById(id);
        if (!companyRepositoryById.isPresent()) {
            return new ApiResponse("Company topilmadi", false);
        }

        companyRepository.deleteById(id);
        return new ApiResponse("Company o'chirildi", true);
    }
}
