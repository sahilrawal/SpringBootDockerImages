package application.model.serviceImpl;

import application.AddressRepository;
import application.UserRepository;
import application.model.dto.AddressDTO;
import application.model.exception.types.UserServiceException;
import application.model.io.entity.AddressEntity;
import application.model.io.entity.UserEntity;
import application.model.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public List<AddressDTO> getUserAddresses(String email) {

        List<AddressDTO> lst = new ArrayList<>();

        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UserServiceException("Record not found !!!");

        List<AddressEntity> addressList = addressRepository.findAllByUserDetails(userEntity);

        for (AddressEntity addressEntity : addressList){

            lst.add(modelMapper.map(addressEntity,AddressDTO.class));

        }


        return lst;
    }

    @Override
    public List<AddressDTO> getAddressesByCity(String city) {
        List<AddressDTO> addresses = new ArrayList<>();

        List<AddressEntity> addressEntitiesList = addressRepository.findAllBycity(city);

        for(AddressEntity entity : addressEntitiesList){

            addresses.add(modelMapper.map(entity,AddressDTO.class));

        }

        return addresses;
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        List<AddressDTO> addresses = new ArrayList<>();

        Iterable<AddressEntity> addressEntitiesList = addressRepository.findAll();

        for(AddressEntity addressEntity : addressEntitiesList){
            addresses.add(modelMapper.map(addressEntity,AddressDTO.class));
        }




        return addresses;
    }
}
