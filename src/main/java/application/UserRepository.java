package application;

import application.model.io.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {

    UserEntity findByEmail (String email);

    UserEntity findByUserId(String UserId);

    //Sample Native SQL query
    @Query(value = "Select * from user where user_id='NewUser123'",nativeQuery = true)
    List<UserEntity>  getAllUsersByUserId();

    //Example of Positional Parameters
    @Query(value = "Select * from user where last_name=?1",nativeQuery = true)
    UserEntity getUserByLastName_PositionalParam(String lastName);

     //Example of Named Parameter
     @Query(value = "Select * from user where last_name=:lastName",nativeQuery = true)
     UserEntity getUserByLastName_NamedParam(@Param("lastName") String lastName);

    //Example of Named Parameter
    @Transactional
    @Modifying
    @Query(value = "Update user set user_id = :newUserId where last_name=:lastName",nativeQuery = true)
    void updateUserIdByLastName_NamedParam(@Param("lastName") String lastName,@Param("newUserId") String newUserId);


}
