package com.unisoma.Unisoma.service;

import com.unisoma.Unisoma.exceptions.EntityNotFoundException;
import com.unisoma.Unisoma.models.Address;
import com.unisoma.Unisoma.repository.AddressRepository;
import com.unisoma.Unisoma.service.implementations.Addressimplementations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @InjectMocks
    private Addressimplementations implementation;
    @Mock
    private AddressRepository repository;

    String sp = "São Paulo";
    Address address = new Address(UUID.randomUUID(),"pracinha da se","se","01001010",14,sp,sp);

    @Test
    public void shouldBeinsertNewAddress(){
        given(repository.save(any(Address.class))).willReturn(address);

        Address testAddress = implementation.insertAddress(address);

        verify(repository).save(address);
        assertEquals(address.getCep(),testAddress.getCep());
    }

    @Test
    public void shouldBeFoundAddressById(){
        given(repository.findById(any(UUID.class))).willReturn(Optional.of(address));

        implementation.findAddressbyId(address.getAddressId());

        verify(repository).findById(address.getAddressId());
    }

    @Test
    public void shouldBeEntityNotFoundException(){
        assertThrows(EntityNotFoundException.class,() -> implementation.findAddressbyId(address.getAddressId()));
    }

    @Test
    public void shouldBeDeleteAddress(){
        given(repository.save(any(Address.class))).willReturn(address);
        given(repository.findById(any(UUID.class))).willReturn(Optional.of(address));

        implementation.insertAddress(address);
        implementation.deleteAddress(address.getAddressId());

        verify(repository).delete(address);
    }

    @Test
    public void shouldBeUpdatedAddress(){
        given(repository.findById(any(UUID.class))).willReturn(Optional.of(address));
        given(repository.save(any(Address.class))).willReturn(address);

        implementation.insertAddress(address);
        address.setBairro("Davilla");
        implementation.updateAddress(address.getAddressId(),address);
        String bairro = implementation.findAddressbyId(address.getAddressId()).getBairro();

        assertEquals("Davilla",bairro);
        verify(repository,times(1)).save(address);
        verify(repository,times(2)).findById(address.getAddressId());

    }
}