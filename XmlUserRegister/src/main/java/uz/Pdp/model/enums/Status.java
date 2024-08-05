package uz.Pdp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    SUCCESS("success", "transaction is success"),
    ERROR_CARD_NOT_FOUND("error","card not found"),
    ERROR_CARD_MONEY_IS_NOT_ENOUGH("error","money is not enough"),
    ERROR_CARD_SAME("error","same card");

    private final String state;
    private final String message;
}
