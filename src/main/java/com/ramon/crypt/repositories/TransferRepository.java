package com.ramon.crypt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramon.crypt.domain.entities.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
