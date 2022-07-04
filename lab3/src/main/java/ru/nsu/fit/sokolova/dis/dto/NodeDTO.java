package ru.nsu.fit.sokolova.dis.dto;


import lombok.*;

import java.math.BigInteger;
import java.sql.Date;

/*      id bigint not null primary key,
        version bigint not null,
        timestamp date not null,
        uid bigint not null,
        username varchar (50) not null,
        changeset bigint not null,
        lat double precision not null,
        lon double precision not null,
        visible boolean
        */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeDTO {
    private BigInteger id;
    private BigInteger version;
    private Date timestamp;
    private BigInteger uid;
    private String username;
    private BigInteger changeset;
    private double latitude;
    private double longitude;
}