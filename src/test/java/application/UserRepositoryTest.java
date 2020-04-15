package application;

import application.model.io.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    final void testGetAllUsersByUserId(){
    List<UserEntity> list = userRepository.getAllUsersByUserId();

    assertNotNull(list);

    assertEquals(list.size(),1);
    }

    @Test
    final void testGetUserByLastName_PositionalParam( ){

        UserEntity user= userRepository.getUserByLastName_PositionalParam("Rawal");

        assertNotNull(user);


        assertTrue(user.getEmail().equals("sahilrawal89@gmail.com"));
        assertTrue(user.getFirstName().equals("Sahil"));

    }

    @Test
    final void testGetUserByLastName_NamedParam( ){

        UserEntity user= userRepository.getUserByLastName_NamedParam("Rawal");

        assertNotNull(user);


        assertTrue(user.getEmail().equals("sahilrawal89@gmail.com"));
        assertTrue(user.getFirstName().equals("Sahil"));

    }

    @Test
    final void testUpdateUserIdByLastName_NamedParam(){

        userRepository.updateUserIdByLastName_NamedParam("Rawal","SAHIL_RAWAK");

        UserEntity user= userRepository.getUserByLastName_NamedParam("Rawal");
        assertNotNull(user);


        assertTrue(user.getEmail().equals("sahilrawal89@gmail.com"));
        assertTrue(user.getFirstName().equals("Sahil"));
        assertTrue(user.getUserId().equals("SAHIL_RAWAK"));


    }


}