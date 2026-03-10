package org.ihab.patientservice.service;

import org.ihab.patientservice.dto.PatientRequestDTO;
import org.ihab.patientservice.dto.PatientResponseDTO;
import org.ihab.patientservice.exception.EmailAlreadyExistsException;
import org.ihab.patientservice.mapper.PatientMapper;
import org.ihab.patientservice.model.Patient;
import org.ihab.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(patientMapper::toDTO)
                .toList();

    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email already exists: " + patientRequestDTO.getEmail());
        }

        Patient patient = patientRepository
                .save(
                        patientMapper.toEntity(patientRequestDTO)
                );

        return patientMapper.toDTO(patient);
    }

    public PatientResponseDTO getPatientById(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow();

        return patientMapper.toDTO(patient);
    }
}
