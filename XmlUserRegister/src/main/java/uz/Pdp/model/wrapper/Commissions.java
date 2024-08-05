package uz.Pdp.model.wrapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.Pdp.model.Commission;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "commissions")
public class Commissions {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "commission")
    List<Commission> commissions = new ArrayList<>();
}
