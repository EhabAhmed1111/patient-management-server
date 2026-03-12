package org.ihab.patientservice.repository;

import org.ihab.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);

    // this check for email if exist it will go and check for id if it is not same it will return true if it is same it will return false because it is the same patient that we want to update
//    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END" +
//            " FROM Patient p WHERE p.email= :email AND p.id <> :id")
    boolean existsByEmailAndIdNot(String email, UUID id);
}
