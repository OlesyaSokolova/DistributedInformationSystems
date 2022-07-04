package ru.nsu.fit.sokolova.dis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
@Entity
@Table(name = "nodes")
public class NodeEntity {

    @Id
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @Column(name = "version", nullable = false)
    private BigInteger version;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @Column(name = "uid", nullable = false)
    private BigInteger uid;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "changeset", nullable = false)
    private BigInteger changeset;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

}
