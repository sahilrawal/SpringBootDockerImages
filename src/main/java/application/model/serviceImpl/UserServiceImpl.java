package application.model.serviceImpl;

import application.UserRepository;
import application.model.dto.AddressDTO;
import application.model.dto.UserModelDto;
import application.model.exception.types.UserServiceException;
import application.model.io.entity.AddressEntity;
import application.model.io.entity.UserEntity;
import application.model.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    private Environment env;

    @Autowired
    ModelMapper modelMapper;

   /* @Autowired
    BCryptPasswordEncoder bcryptPasswordEncoder;*/


    @Override
    public UserModelDto createUser(UserModelDto userDto) {


        if (userDto.getEmail().isEmpty())
            throw new UserServiceException(env.getProperty("spring.exception.mandatory_field_check"));
        if (userRepository.findByEmail(userDto.getEmail()) != null)
            throw new UserServiceException(env.getProperty("spring.exception.user_already_exists"));
        //UserEntity userEntity = new UserEntity();
        //BeanUtils.copyProperties(userDto,userEntity);

        for (AddressDTO address : userDto.getAddresses()) {
            address.setAddressID("NewAddress123");
            address.setUserDetails(userDto);

        }
        UserEntity userEntity = new UserEntity();
        //BeanUtils.copyProperties(userDto,userEntity);
                userEntity = modelMapper.map(userDto, UserEntity.class);
       userEntity.setEncryptedPassword(userDto.getPassword());
        userEntity.setUserId("NewUser123");

        UserEntity savedUserDetails = userRepository.save(userEntity);

         // UserModelDto returnDto = new UserModelDto();
         //BeanUtils.copyProperties(savedUserDetails,returnDto);

        //Note modelMapper doesnot work with the Test Cases. Returns null objects.

        UserModelDto returnDto = modelMapper.map(savedUserDetails, UserModelDto.class);


        return returnDto;
    }

    @Override
    public UserModelDto updateUser(String id, UserModelDto userDto) {
        UserModelDto dto = new UserModelDto();
        UserEntity userDetails = userRepository.findByUserId(id);
        if (userDetails == null) throw new RuntimeException("User does not exixts !!! ");

        userDetails.setFirstName(userDto.getFirstName());
        userDetails.setLastName(userDto.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userDetails);

        BeanUtils.copyProperties(updatedUserDetails, dto);


        return dto;
    }

    @Override
    public UserModelDto deleteUser(String id) {
        UserModelDto dto = new UserModelDto();
        UserEntity userDetails = userRepository.findByUserId(id);
        userRepository.delete(userDetails);


        return dto;
    }

    @Override
    public UserModelDto getUserbyUserId(String id) {

        UserModelDto dto = new UserModelDto();
        UserEntity userDetails = userRepository.findByUserId(id);
        if (userDetails == null) throw new RuntimeException("User does not exixts !!! ");

        BeanUtils.copyProperties(userDetails, dto);


        return dto;
    }

    @Override
    public UserModelDto findByEmail(String email) {

        UserModelDto returnDto = new UserModelDto();

        UserEntity userDetails = userRepository.findByEmail(email);

        if(userDetails == null) throw new UserServiceException("No record found !!!");

        BeanUtils.copyProperties(userDetails, returnDto);

        return returnDto;
    }

    @Override
    public UserModelDto updateUserAddress(String email, UserModelDto userDto) {



        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity!=null){

            for(int i = 0 ;i<userEntity.getAddresses().size();i++){
                userEntity.getAddresses().get(i).setCity(userDto.getAddresses().get(i).getCity());
                userEntity.getAddresses().get(i).setCountry(userDto.getAddresses().get(i).getCountry());
                userEntity.getAddresses().get(i).setPostalCode(userDto.getAddresses().get(i).getPostalCode());
                userEntity.getAddresses().get(i).setStreetName(userDto.getAddresses().get(i).getStreetName());
                userEntity.getAddresses().get(i).setType(userDto.getAddresses().get(i).getType());

            }

        }else
            {throw new UserServiceException("User not found !!!");}

        UserEntity updatedUserEntity = userRepository.save(userEntity);


       // UserModelDto updatedUserModelDto = modelMapper.map(updatedUserEntity,UserModelDto.class);
        UserModelDto updatedUserModelDto = new UserModelDto();
        BeanUtils.copyProperties(updatedUserEntity,updatedUserModelDto);
        return updatedUserModelDto;
    }
}
