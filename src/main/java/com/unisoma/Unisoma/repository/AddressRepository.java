package com.unisoma.Unisoma.repository;

import com.unisoma.Unisoma.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address,UUID> {

    public boolean existsByLogradouroAndCepAndNumber(String logradouro, String cecp, int number);
    public Address findByLogradouroAndCepAndNumber(String logradouro, String cecp, int number);
}
