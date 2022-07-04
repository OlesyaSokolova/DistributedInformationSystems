package ru.nsu.fit.sokolova.dis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagHttpSimpleEntity {
    private String key;
    private String value;
    private BigInteger nodeId;
}
