package ru.nsu.fit.sokolova.dis.converters;

import ru.nsu.fit.sokolova.dis.dto.NodeDTO;
import ru.nsu.fit.sokolova.dis.dto.TagDTO;
import ru.nsu.fit.sokolova.dis.dto.TagHttpSimpleEntity;
import ru.nsu.fit.sokolova.dis.entity.NodeEntity;
import ru.nsu.fit.sokolova.dis.entity.TagEntity;
import ru.nsu.fit.sokolova.dis.model.Node;
import ru.nsu.fit.sokolova.dis.model.Tag;

public class TagConverter {

    /*    node_id bigint not null references nodes(id),
    key varchar(50) not null,
    value varchar(256) not null,
    */
    public static TagEntity dtoToEntity(TagDTO tagDTO) {
        TagEntity tagEntity = new TagEntity();
        NodeEntity nodeEntity = NodeConverter.dtoToEntity(tagDTO.getNodeDTO());

        tagEntity.setNode(nodeEntity);
        tagEntity.setKey(tagDTO.getKey());
        tagEntity.setValue(tagDTO.getValue());

        return tagEntity;
    }

    public static TagDTO entityToDTO(TagEntity tagEntity) {
        TagDTO tagDTO = new TagDTO();

        tagDTO.setKey(tagEntity.getKey());
        tagDTO.setValue(tagEntity.getValue());
        tagDTO.setNodeDTO(NodeConverter.entityToDTO(tagEntity.getNode()));

        return tagDTO;
    }

    public static TagDTO modelToDto(Node node, Tag tag) {
        TagDTO tagDTO = new TagDTO();
        NodeDTO nodeDTO = NodeConverter.modelToDto(node);

        tagDTO.setKey(tag.getK());
        tagDTO.setValue(tag.getV());
        tagDTO.setNodeDTO(nodeDTO);

        return tagDTO;
    }

    public static TagHttpSimpleEntity entityToSimpleHttpEntity(TagEntity tagEntity) {
        TagHttpSimpleEntity tagHttpSimpleEntity = new TagHttpSimpleEntity();

        tagHttpSimpleEntity.setNodeId(tagEntity.getNode().getId());
        tagHttpSimpleEntity.setKey(tagEntity.getKey());
        tagHttpSimpleEntity.setValue(tagEntity.getValue());

        return tagHttpSimpleEntity;
    }

    public static TagEntity httpSimpleEntityToEntity(NodeEntity nodeEntity, TagHttpSimpleEntity tag) {
        TagEntity tagEntity = new TagEntity();

        tagEntity.setNode(nodeEntity);
        tagEntity.setKey(tag.getKey());
        tagEntity.setValue(tag.getValue());

        return tagEntity;
    }
}
