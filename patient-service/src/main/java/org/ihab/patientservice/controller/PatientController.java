package org.ihab.patientservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.ihab.patientservice.dto.PatientRequestDTO;
import org.ihab.patientservice.dto.PatientResponseDTO;
import org.ihab.patientservice.dto.validators.CreatePatientValidationGroup;
import org.ihab.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@Tag(name = "Patient", description = "API for managing patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping()
    @Operation(summary = "Get all patients", description = "Returns a list of all patients in the system")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    // @Valid -> to validate the incoming request body based on the constraints defined in the PatientRequestDTO class
    @PostMapping()
    @Operation(summary = "Create a new patient", description = "Creates a new patient in the system with the provided details")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patient = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patient);
    }

    // the different between @Valid and @Validated is that @Valid is used for validating a single object, while @Validated is used for validating a group of objects. In this case, we are validating a single object (the PatientRequestDTO), so we can use @Valid. However, if we want to validate a group of objects (for example, if we have multiple DTOs that need to be validated together), we can use @Validated with a specific validation group.
    // valid check every thing in the DTO, while validated check only the fields that are annotated with the specified validation group. In this case, we are using the Default validation group, which means that all fields will be validated.
    @PutMapping("/{id}")
    @Operation(summary = "Update a patient", description = "Updates the details of an existing patient in the system")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable("id") UUID id, @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patient = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient", description = "Deletes an existing patient from the system")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
