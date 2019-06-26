package com.icefire.api.information.domain.repository;

import com.icefire.api.information.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
