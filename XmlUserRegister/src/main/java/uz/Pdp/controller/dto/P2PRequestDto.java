package uz.Pdp.controller.dto;

import lombok.Data;

@Data
public class P2PRequestDto {
    private String fromCard;
    private String toCard;
    private double amount;
}

