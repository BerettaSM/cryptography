package com.ramon.crypt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramon.crypt.domain.dto.TransferDTO;
import com.ramon.crypt.repositories.TransferRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransferService {

    private final TransferRepository transferRepository;

    @Transactional(readOnly = true)
    public Page<TransferDTO> findAll(Pageable pageable) {
        return transferRepository.findAll(pageable)
            .map(TransferDTO::from);
    }
    
}
