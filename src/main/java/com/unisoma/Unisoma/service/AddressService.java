package com.unisoma.Unisoma.service;

import com.unisoma.Unisoma.models.Address;
import com.unisoma.Unisoma.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface AddressService {

    public Address insertAddress(Address address);
    public Address findAddressbyId(UUID id);
    public Address updateAddress(UUID id, Address address);
    public void deleteAddress(UUID id);
}
