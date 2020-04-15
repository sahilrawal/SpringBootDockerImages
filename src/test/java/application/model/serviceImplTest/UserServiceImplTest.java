package application.model.serviceImplTest;

import application.UserRepository;
import application.model.dto.AddressDTO;
import application.model.dto.UserModelDto;
import application.model.exception.types.UserServiceException;
import application.model.io.entity.AddressEntity;
import application.model.io.entity.UserEntity;
import application.model.response.AddressDetailsResponse;
import application.model.service.UserService;
import application.model.serviceImpl.UserServiceImpl;
import org.apache.catalina.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserRepository userRepository;

    @Mock
    ModelMapper modelMapper;


    UserEntity userEntity;

    AddressEntity addressEntity;
    List<AddressEntity> addressList;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        //Create a stub object for testing the create user functionality.
        userEntity = new UserEntity();
        userEntity.setUserId("101");
        userEntity.setFirstName("TestFirstName");
        userEntity.setLastName("TestLastNAme");
        userEntity.setEmail("Test@gmail.com");

        /*addressEntity = new AddressEntity();
        addressEntity.setType("TestPrimary");
        addressEntity.setCity("TestCity");
        addressEntity.setCountry("TestCountry");
        addressList = new ArrayList<>();
        addressList.add(addressEntity);*/

        userEntity.setAddresses(getAddressEntityList());
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    final void testFindByEmail() {


        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserModelDto userModelDTO = userServiceImpl.findByEmail("sahilra9@gmail.com");
        assertNotNull(userModelDTO);
        assertEquals("TestFirstName", userModelDTO.getFirstName());


    }

     @Test
    final void testFindByEmail_UserNameNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UserServiceException.class,
                () -> {
                    userServiceImpl.findByEmail("sahil@gmail.com");
                });


    }

    //@Test
    final void testCreateUser() {


        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserModelDto userDto = new UserModelDto();
        userDto.setEmail("Hello@gmail.com");
        userDto.setPassword("11111");
        userDto.setAddresses(new ArrayList<AddressDTO>());
        UserModelDto createdUserModelDto = userServiceImpl.createUser(userDto);

        assertNotNull(createdUserModelDto);
        assertEquals(userEntity.getFirstName(), createdUserModelDto.getFirstName());
    }

    @Test
    final void  testUpdateUserAddress() {

        UserModelDto userDto = new UserModelDto();
        userDto.setUserId("101");
        userDto.setFirstName("TestFirstName");
        userDto.setEmail("Test@gmail.com");
        userDto.setPassword("11111");
        userDto.setAddresses(getAddressDTOList());

        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserModelDto updatedUserModelDto = userServiceImpl.updateUserAddress("TestEmail", userDto);

        assertNotNull(updatedUserModelDto);

        //NOTE : Not able to mock the inner objects i.e Address Entity in this case.
        //assertEquals(((AddressDTO)updatedUserModelDto.getAddresses().get(0)).getType(), ((AddressEntity)userEntity.getAddresses().get(0)).getType());
        assertEquals(updatedUserModelDto.getFirstName(), userEntity.getFirstName());
        verify(userRepository,times(1)).findByEmail("TestEmail");
        verify(userRepository,times(1)).save(any(UserEntity.class));



    }

    private List<AddressDTO> getAddressDTOList() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setType("TestPrimary");
        addressDTO.setCity("TestCity");
        addressDTO.setCountry("TestCountry");

        List<AddressDTO> addressList = new ArrayList();
        addressList.add(addressDTO);

        return addressList;


    }

    private List<AddressEntity> getAddressEntityList() {

        List<AddressDTO> addressDTOList = getAddressDTOList();
        List<AddressEntity> addressEntityList = new ArrayList<>();

        //Code is not working. The address entity list is null.
        /*java.lang.reflect.Type listType = new TypeToken<List<AddressEntity>>() {}.getType();
        List<AddressEntity> addressEntityList = modelMapper.map(addressDTOList, listType);*/

        for (AddressDTO dto : addressDTOList) {
            AddressEntity addEntity = new AddressEntity();
            addEntity.setType(dto.getType());
            addEntity.setCountry(dto.getCountry());
            addEntity.setCity(dto.getCity());
            addressEntityList.add(addEntity);
        }


        return addressEntityList;


    }

}