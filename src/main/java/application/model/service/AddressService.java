package application.model.service;

import application.model.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    public List<AddressDTO> getUserAddresses(String email);
    public List<AddressDTO> getAddressesByCity(String city);
    public List<AddressDTO> getAllAddresses();
}
