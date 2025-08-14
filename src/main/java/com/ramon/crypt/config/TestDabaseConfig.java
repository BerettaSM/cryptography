package com.ramon.crypt.config;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ramon.crypt.domain.entities.Transfer;
import com.ramon.crypt.repositories.TransferRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@Profile(value = { "dev", "test" })
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestDabaseConfig {

    private final TransferRepository transferRepository;

    @Bean
    @SuppressWarnings("unused")
    ApplicationRunner seedDatabase() {
        return args -> {
            List<Transfer> transfers = List.of(
                new Transfer(null, "123.456.789-10", UUID.randomUUID().toString(), 5999L),
                new Transfer(null, "123.456.789-10", UUID.randomUUID().toString(), 1000L),
                new Transfer(null, "123.456.789-10", UUID.randomUUID().toString(), 1500L));
            transferRepository.saveAll(transfers);
        };
    }

}
