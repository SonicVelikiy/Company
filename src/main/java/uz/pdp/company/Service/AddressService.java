package uz.pdp.company.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.company.Entity.Address;
import uz.pdp.company.Payload.AddressDto;
import uz.pdp.company.Payload.ApiResponse;
import uz.pdp.company.Repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAddress() {
        return addressRepository.findAll();
    }


    public Address getAddressById(Integer id) {
        Optional<Address> addressRepositoryById = addressRepository.findById(id);
        return addressRepositoryById.orElse(null);
    }

    public ApiResponse addAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address added", true);
    }

    public ApiResponse editAddress(AddressDto addressDto, Integer id) {
        Optional<Address> addressRepositoryById = addressRepository.findById(id);
        if (addressRepositoryById.isPresent()) {
            Address address = addressRepositoryById.get();
            address.setHomeNumber(addressDto.getHomeNumber());
            address.setStreet(addressDto.getStreet());
            addressRepository.save(address);
            return new ApiResponse("Address edited", true);
        }
        return new ApiResponse("Address not found", false);
    }

    public ApiResponse deleteAddress(Integer id) {
        Optional<Address> addressRepositoryById = addressRepository.findById(id);
        if (addressRepositoryById.isPresent()) {
            addressRepository.deleteById(id);
            return new ApiResponse("Address deleted", true);
        }
        return new ApiResponse("Address not found", false);
    }
}
