package com.ramon.crypt.domain.dto;

import org.hibernate.validator.constraints.UUID;
import org.hibernate.validator.constraints.br.CPF;

import com.ramon.crypt.domain.entities.Transfer;
import com.ramon.crypt.services.annotations.SensitiveData;
import com.ramon.crypt.validation.groups.PostGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TransferDTO {

    private Long id;

    @SensitiveData
    @CPF(message = "Must be a valid CPF.")
    @NotNull(groups = PostGroup.class, message = "Must not be null.")
    private String userDocument;

    @SensitiveData
    @UUID(message = "Must a be a valid UUID.")
    @NotBlank(groups = PostGroup.class, message = "Must not be null.")
    private String creditCardToken;

    @Positive(message = "Must be a positive number.")
    @NotNull(groups = PostGroup.class, message = "Must not be null.")
    private Long value;

    public TransferDTO(Transfer entity) {
        id = entity.getId();
        userDocument = entity.getUserDocument();
        creditCardToken = entity.getCreditCardToken();
        value = entity.getValue();
    }

    public Transfer toEntity() {
        return new Transfer(null, userDocument, creditCardToken, value);
    }

    public static TransferDTO from(Transfer entity) {
        return new TransferDTO(entity);
    }

}
