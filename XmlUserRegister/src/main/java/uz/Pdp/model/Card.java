package uz.Pdp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.Pdp.model.enums.CardType;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Card {
    private UUID id;
    private UUID userId;
    private String name;
    private String cardNumber;
    private double balance;
    private String password;
    private CardType cardType;
}
