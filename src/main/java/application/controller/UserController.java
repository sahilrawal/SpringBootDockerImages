package application.controller;


import application.model.dto.AddressDTO;
import application.model.dto.UserModelDto;
import application.model.request.UserDetailsRequestBody;
import application.model.response.AddressDetailsResponse;
import application.model.response.UserDetailsResponseBody;
import application.model.service.AddressService;
import application.model.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AddressService addressService;

    @Autowired
    DataSource dataSource;


    /*@GetMapping(path = "/{id}",
    produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public UserDetailsResponseBody getUser(@PathVariable String id) {

        UserDetailsResponseBody res = new UserDetailsResponseBody();
        UserModelDto requestDto = userService.getUserbyUserId(id);

        BeanUtils.copyProperties(requestDto,res);
        return res;
    }*/
    //@PreAuthorize("permitAll()")
    @GetMapping(path = "/{email}/addresses",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<AddressDetailsResponse> getUserAddresses(@PathVariable String email) {

        //List<AddressDetailsResponse> addressResponseList = new ArrayList();
        List<AddressDTO> addressListDTO = addressService.getUserAddresses(email);

        java.lang.reflect.Type listType = new TypeToken<List<AddressDetailsResponse>>() {}.getType();
        List<AddressDetailsResponse> addressResponseList = modelMapper.map(addressListDTO, listType);

       // BeanUtils.copyProperties(requestDto, res);
        return addressResponseList;
    }

    @GetMapping(path = "/addresses/{city}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<AddressDetailsResponse> getAddressesByCity(@PathVariable String city) {

        //List<AddressDetailsResponse> addressResponseList = new ArrayList();
        List<AddressDTO> addressListDTO = addressService.getAddressesByCity(city);

        java.lang.reflect.Type listType = new TypeToken<List<AddressDetailsResponse>>() {}.getType();
        List<AddressDetailsResponse> addressResponseList = modelMapper.map(addressListDTO, listType);

        /*try {
            System.out.println(dataSource.getConnection());

        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        // BeanUtils.copyProperties(requestDto, res);
        return addressResponseList;
    }

    //Restrict access to /addresses to only MANAGER.
   // @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/addresses",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<AddressDetailsResponse> getAllAddresses() {

        //List<AddressDetailsResponse> addressResponseList = new ArrayList();
        List<AddressDTO> addressListDTO = addressService.getAllAddresses();

        java.lang.reflect.Type listType = new TypeToken<List<AddressDetailsResponse>>() {}.getType();
        List<AddressDetailsResponse> addressResponseList = modelMapper.map(addressListDTO, listType);

        // BeanUtils.copyProperties(requestDto, res);
        return addressResponseList;
    }

    @GetMapping(path = "/{email}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserDetailsResponseBody getUser(@PathVariable String email) {

        //UserDetailsResponseBody res = new UserDetailsResponseBody();
        UserModelDto requestDto = userService.findByEmail(email);

       // BeanUtils.copyProperties(requestDto, res);
        UserDetailsResponseBody res= modelMapper.map(requestDto,UserDetailsResponseBody.class);

        return res;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}

    )
    public UserDetailsResponseBody createUser(@RequestBody UserDetailsRequestBody userDetails {

        //UserModelDto requestDto = new UserModelDto();
        // BeanUtils.copyProperties(userDetails, requestDto);
        UserModelDto requestDto = modelMapper.map(userDetails, UserModelDto.class);
        UserModelDto responseDto = userService.createUser(requestDto);
       // UserDetailsResponseBody res = new UserDetailsResponseBody();
        //BeanUtils.copyProperties(responseDto, res);
        UserDetailsResponseBody res = modelMapper.map(responseDto, UserDetailsResponseBody.class);
        return res;
    }

    @PutMapping(path = "/{userId}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserDetailsResponseBody updateUser(@PathVariable String userId, @RequestBody UserDetailsRequestBody userDetails) {

        UserDetailsResponseBody res = new UserDetailsResponseBody();
        UserModelDto requestDto = new UserModelDto();
        BeanUtils.copyProperties(userDetails, requestDto);
        UserModelDto updatedDto = userService.updateUser(userId, requestDto);
        BeanUtils.copyProperties(updatedDto, res);
        return res;
    }


    @PutMapping(path = "/{email}/addresses",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserDetailsResponseBody updateUserAddress(@PathVariable String email, @RequestBody UserDetailsRequestBody userDetails) {


        UserModelDto requestDto = modelMapper.map(userDetails,UserModelDto.class);
       // BeanUtils.copyProperties(userDetails, requestDto);
        UserModelDto updatedDto = userService.updateUserAddress(email,requestDto);
        //BeanUtils.copyProperties(updatedDto, res);
        UserDetailsResponseBody res =modelMapper.map(updatedDto,UserDetailsResponseBody.class);
        return res;
    }

    //Restrict Delete to only ADMIN.
    @DeleteMapping(path = "/{userId}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserDetailsResponseBody deleteUser(@PathVariable String userId) {

        UserDetailsResponseBody res = new UserDetailsResponseBody();
        UserModelDto updatedDto = userService.deleteUser(userId);
        BeanUtils.copyProperties(updatedDto, res);
        return res;
    }
}
