package it.academy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Settlement {
    private Integer id;
    private Region region;
    private SettlementType settlementType;
    private String name;
}
