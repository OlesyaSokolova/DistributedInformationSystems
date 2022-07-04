package ru.nsu.fit.sokolova.dis.dto;

import lombok.*;


/*    node_id bigint not null references nodes(id),
    key varchar(50) not null,
    value varchar(256) not null,
    */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private String key;
    private String value;
    private NodeDTO nodeDTO;
}