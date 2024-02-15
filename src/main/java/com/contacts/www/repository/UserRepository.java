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

    @Query("SELECT DISTINCT u FROM UserEntity u WHERE " +
            "(:fName is null or u.firstName LIKE %:fName%) " +
            "AND (:lName is null or u.lastName LIKE %:lName%)")
    List<UserEntity> findAllOrFilter(
            @Param("fName") String firstName,
            @Param("lName") String lastName
    );

//    @Query("SELECT DISTINCT u FROM UserEntity u  " +
//            "LEFT JOIN FETCH u.otherEmailAddressEntities " +
//            "WHERE (:fName is null or u.firstName LIKE %:fName%) " +
//            "AND (:lName is null or u.lastName LIKE %:lName%)")
//    List<UserEntity> findAllOrFilter(
//            @Param("fName") String firstName,
//            @Param("lName") String lastName
//    );


}
