package uz.Pdp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum XmlJson {
    Xml("Xml"),
    Json("Json");

    private final String val;
}

