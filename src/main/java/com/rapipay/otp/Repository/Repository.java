package com.rapipay.otp.Repository;



import org.springframework.data.jpa.repository.JpaRepository;


import com.rapipay.otp.Storage.Data;

public interface Repository extends JpaRepository<Data, String> {

}
