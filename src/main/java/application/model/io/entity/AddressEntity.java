package application.model.io.entity;

import application.model.dto.UserModelDto;
import com.sun.javafx.beans.IDProperty;


import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "address")
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = 3320828603942177666L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable=false)
    private String addressID;


    @Column(nullable=false)
    private String city;

    @Column(nullable=false)
    private String country;

    @Column(nullable=false)
    private String streetName;

    @Column(nullable=false)
    private String postalCode;

    @Column(nullable=false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
}
