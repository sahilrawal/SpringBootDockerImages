package application.model.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "user")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 5734890850091641171L;


    @Id //This annotation is used as a primary key and will be auto incremented by the framework every time a record is inserted into the DB.
    @GeneratedValue // This annotation will be used to generate the value and to autoincrement one by one.
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false,length=20)
    private String firstName;

    @Column(nullable = false,length=20)
    private String lastName;

    @Column(nullable = false,length=100,unique=true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;


    @OneToMany(mappedBy = "userDetails",cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }
}
