package ru.nsu.fit.sokolova.dis.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/*    node_id bigint not null references nodes(id),
    key varchar(50) not null,
    value varchar(256) not null,
    */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags")
public class TagEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "node_id", referencedColumnName = "id")
    private NodeEntity node;
}
