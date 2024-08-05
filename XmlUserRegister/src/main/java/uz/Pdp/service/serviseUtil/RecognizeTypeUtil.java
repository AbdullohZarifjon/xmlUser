package uz.Pdp.service.serviseUtil;

import lombok.experimental.UtilityClass;
import uz.Pdp.model.enums.CardType;


@UtilityClass
public class RecognizeTypeUtil {

    public static CardType recognize(String cardNumber) {
        if (cardNumber.startsWith("9860")) {
            return CardType.HUMO;
        }
        if (cardNumber.startsWith("8600")) {
            return CardType.UZ_CARD;
        }
        else {
            return CardType.VISA;
        }
    }

}
