package uz.Pdp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CardType {
    UZ_CARD("Uzcard"),
    HUMO("Humo"),
    VISA("Visa");

    private final String val;
}
