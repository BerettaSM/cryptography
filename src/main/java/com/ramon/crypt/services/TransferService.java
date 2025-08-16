package com.ramon.crypt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ramon.crypt.domain.dto.TransferDTO;
import com.ramon.crypt.domain.entities.Transfer;
import com.ramon.crypt.repositories.TransferRepository;
import com.ramon.crypt.services.exceptions.DatabaseException;
import com.ramon.crypt.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransferService {

    private final TransferRepository transferRepository;
    private final SensitiveDataService sensitiveDataService;

    @Transactional(readOnly = true)
    public Page<TransferDTO> findAll(Pageable pageable) {
        return transferRepository.findAll(pageable)
            .map(TransferDTO::from)
            .map(sensitiveDataService::decrypt);
    }

    @Transactional(readOnly = true)
    public TransferDTO findById(Long id) {
        return transferRepository.findById(id)
            .map(TransferDTO::from)
            .map(sensitiveDataService::decrypt)
            .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public TransferDTO save(TransferDTO dto) {
        TransferDTO encryptedDto = sensitiveDataService.encrypt(dto);
        Transfer transfer = encryptedDto.toEntity();
        try {
            Transfer saved = transferRepository.save(transfer);
            encryptedDto = TransferDTO.from(saved);
            return sensitiveDataService.decrypt(encryptedDto);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Database constraint violation.", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!transferRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        try {
            transferRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e, HttpStatus.CONFLICT);
        }
    }
    
}
