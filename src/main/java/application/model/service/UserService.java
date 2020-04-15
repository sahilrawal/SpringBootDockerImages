package application.model.service;

import application.UserRepository;
import application.model.dto.UserModelDto;
import org.hibernate.boot.archive.scan.spi.ScanOptions;
import org.springframework.stereotype.Service;


public interface UserService {

    public UserModelDto createUser(UserModelDto userDto);
    public UserModelDto updateUser (String id,UserModelDto userDto);
    public UserModelDto deleteUser (String id);
    public UserModelDto getUserbyUserId(String id);
    public UserModelDto findByEmail (String email);
    public UserModelDto updateUserAddress(String email,UserModelDto userDto);


}
