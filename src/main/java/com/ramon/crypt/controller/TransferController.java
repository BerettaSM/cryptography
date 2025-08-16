package com.ramon.crypt.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ramon.crypt.domain.dto.TransferDTO;
import com.ramon.crypt.services.TransferService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/transfers", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransferController {

    private final TransferService transferService;

    @GetMapping
    public ResponseEntity<Page<TransferDTO>> findAll(Pageable pageable) {
        Page<TransferDTO> transfers = transferService.findAll(pageable);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TransferDTO> findById(@PathVariable Long id) {
        TransferDTO transfer = transferService.findById(id);
        return ResponseEntity.ok(transfer);
    }

    @PostMapping
    public ResponseEntity<TransferDTO> save(@RequestBody TransferDTO dto) {
        TransferDTO saved = transferService.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .build(saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<TransferDTO> putMethodName(
            @PathVariable Long id,
            @RequestBody TransferDTO dto) {
        TransferDTO updated = transferService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        transferService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
