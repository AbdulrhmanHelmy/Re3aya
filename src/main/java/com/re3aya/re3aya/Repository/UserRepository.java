package com.re3aya.re3aya.Repository;


import com.re3aya.re3aya.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);
}
