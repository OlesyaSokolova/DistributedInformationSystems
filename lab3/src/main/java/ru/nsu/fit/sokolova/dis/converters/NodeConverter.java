package ru.nsu.fit.sokolova.dis.converters;

import ru.nsu.fit.sokolova.dis.dto.NodeDTO;
import ru.nsu.fit.sokolova.dis.entity.NodeEntity;
import ru.nsu.fit.sokolova.dis.model.Node;

import java.sql.Date;

public class NodeConverter {
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
    public static NodeEntity dtoToEntity(NodeDTO nodeDTO) {
        NodeEntity nodeEntity = new NodeEntity();

        nodeEntity.setId(nodeDTO.getId());
        nodeEntity.setVersion(nodeDTO.getVersion());
        nodeEntity.setTimestamp(nodeDTO.getTimestamp());
        nodeEntity.setUid(nodeDTO.getUid());
        nodeEntity.setUsername(nodeDTO.getUsername());
        nodeEntity.setChangeset(nodeDTO.getChangeset());
        nodeEntity.setLatitude(nodeDTO.getLatitude());
        nodeEntity.setLongitude(nodeDTO.getLongitude());

        return nodeEntity;
    }

    public static NodeDTO entityToDTO(NodeEntity nodeEntity) {
        NodeDTO nodeDTO = new NodeDTO();

        nodeDTO.setId(nodeEntity.getId());
        nodeDTO.setVersion(nodeEntity.getVersion());
        nodeDTO.setTimestamp(nodeEntity.getTimestamp());
        nodeDTO.setUid(nodeEntity.getUid());
        nodeDTO.setUsername(nodeEntity.getUsername());
        nodeDTO.setChangeset(nodeEntity.getChangeset());
        nodeDTO.setLatitude(nodeEntity.getLatitude());
        nodeDTO.setLongitude(nodeEntity.getLongitude());
        return nodeDTO;
    }

    public static NodeDTO modelToDto(Node node) {
        NodeDTO nodeDTO = new NodeDTO();

        nodeDTO.setId(node.getId());
        nodeDTO.setVersion(node.getVersion());
        nodeDTO.setTimestamp(new Date(node.getTimestamp().toGregorianCalendar().getTime().getTime()));
        nodeDTO.setUid(node.getUid());
        nodeDTO.setUsername(node.getUser());
        nodeDTO.setChangeset(node.getChangeset());
        nodeDTO.setLatitude(node.getLat());
        nodeDTO.setLongitude(node.getLon());

        return nodeDTO;
    }
}
