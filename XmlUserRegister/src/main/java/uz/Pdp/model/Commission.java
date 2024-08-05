package uz.Pdp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commission {
    @JacksonXmlProperty(localName = "from_type")
    private String fromType;
    @JacksonXmlProperty(localName = "to_type")
    private String toType;
    private double percent;
}
