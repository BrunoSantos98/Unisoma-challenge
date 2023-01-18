package com.unisoma.Unisoma.service.implementations;

import com.unisoma.Unisoma.exceptions.EntityNotFoundException;
import com.unisoma.Unisoma.models.Address;
import com.unisoma.Unisoma.repository.AddressRepository;
import com.unisoma.Unisoma.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class Addressimplementations implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address insertAddress(Address address) {
        if(verifySameAddress(address)){
            return addressRepository.findByLogradouroAndCepAndNumber(
                    address.getLogradouro(), address.getCep(), address.getNumber());
        }else{
            return addressRepository.save(address);
        }

    }

    @Override
    public boolean verifySameAddress(Address address) {
        if(addressRepository.existsByLogradouroAndCepAndNumber(
                address.getLogradouro(), address.getCep(), address.getNumber())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Address findAddressbyId(UUID id){
        Optional<Address> address = addressRepository.findById(id);
        if(address.isEmpty()){
            throw new EntityNotFoundException("O id: "+ id +" não retornou resultado, por favor tente com outro id");
        }else{
            return address.get();
        }
    }

    @Override
    public Address updateAddress(UUID id, Address address){
        Address updatedAddress = findAddressbyId(id);
        address.setAddressId(updatedAddress.getAddressId());
        BeanUtils.copyProperties(address,updatedAddress);
        return updatedAddress;
    }

    @Override
    public void deleteAddress(UUID id){
        Address address = findAddressbyId(id);
        addressRepository.delete(address);
    }
}
