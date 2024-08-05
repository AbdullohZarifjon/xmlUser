package uz.Pdp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;
import uz.Pdp.model.enums.Status;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    @Getter
    @Setter
    @JacksonXmlProperty(isAttribute = true)
    private int id;

    @Setter
    @Getter
    @JacksonXmlProperty(isAttribute = true)
    private double amount;

    @Setter
    @Getter
    private String fromCard;
    @Setter
    @Getter
    private String toCard;
    @Setter
    @Getter
    private Date date;
    @Setter
    @Getter
    private double commission;

    @Setter
    @Getter
    private double commission_amount;

    @Setter
    @Getter
    private Status status;

    @JacksonXmlProperty(localName = "state")
    public String getState() {
        return getStatus().getState();
    }

    @JacksonXmlProperty(localName = "message")
    public String getMessage() {
        return getStatus().getMessage();
    }
}


