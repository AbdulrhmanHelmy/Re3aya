package com.re3aya.re3aya.Repository.Users;

import com.re3aya.re3aya.Model.USERS.Assistant;
import com.re3aya.re3aya.Model.USERS.Doctor;
import com.re3aya.re3aya.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssistantRepository extends JpaRepository<Assistant, Long> {
    Optional<Assistant> findByUser(User user);
}
