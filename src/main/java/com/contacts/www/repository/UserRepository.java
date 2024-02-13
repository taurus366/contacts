package com.contacts.www.repository;

import com.contacts.www.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAddress(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.firstName LIKE %:fName%")
    List<UserEntity> findByFirstNameContaining(@Param("fName") String firstName);
}
