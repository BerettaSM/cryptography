package com.ramon.crypt.domain.dto;

import com.ramon.crypt.domain.entities.Transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TransferDTO {

    private Long id;
    private String userDocument;
    private String creditCardToken;
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
