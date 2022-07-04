package ru.nsu.fit.sokolova.dis.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.sokolova.dis.dto.TagDTO;
import ru.nsu.fit.sokolova.dis.dto.TagHttpSimpleEntity;
import ru.nsu.fit.sokolova.dis.entity.NodeEntity;
import ru.nsu.fit.sokolova.dis.entity.TagEntity;
import ru.nsu.fit.sokolova.dis.repository.NodeRepository;
import ru.nsu.fit.sokolova.dis.repository.TagRepository;
import ru.nsu.fit.sokolova.dis.service.TagService;
import ru.nsu.fit.sokolova.dis.converters.TagConverter;

import java.math.BigInteger;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final NodeRepository nodeRepository;

    @Override
    public TagHttpSimpleEntity create(TagHttpSimpleEntity tag) {
        Optional<NodeEntity> nodeEntity = nodeRepository.findById(tag.getNodeId());
        if (nodeEntity.isPresent()) {
            TagEntity foundEntity = tagRepository.findByNodeIdAndKey(tag.getNodeId(), tag.getKey());
            TagEntity savedEntity = null;
            if(foundEntity == null) {
                TagEntity tagEntity = TagConverter.httpSimpleEntityToEntity(nodeEntity.get(), tag);
                savedEntity = tagRepository.save(tagEntity);
            }
            else {
                savedEntity = foundEntity;
            }
            return TagConverter.entityToSimpleHttpEntity(savedEntity);
        }
        return null;
    }

    @Override
    public void create(TagDTO tagDTO) {
        TagEntity tagEntity = TagConverter.dtoToEntity(tagDTO);
        tagRepository.save(tagEntity);
    }

    @Override
    public TagDTO read(BigInteger nodeId, String key) {
        TagEntity foundEntity = tagRepository.findByNodeIdAndKey(nodeId, key);
        if(foundEntity != null) {
            return TagConverter.entityToDTO(foundEntity);
        }
        return null;
    }

    @Override
    public TagDTO update(BigInteger nodeId, String key, TagHttpSimpleEntity tag) {
        TagEntity foundEntity = tagRepository.findByNodeIdAndKey(nodeId, key);
        if(foundEntity != null) {
            tagRepository.deleteByNodeIdAndKey(nodeId, key);
            create(tag);
            return TagConverter.entityToDTO(foundEntity);
        }
        return null;
    }

    @Override
    public TagDTO delete(BigInteger nodeId, String key) {
        TagEntity deletedTag = tagRepository.deleteByNodeIdAndKey(nodeId, key);
        if(deletedTag != null) {
            return TagConverter.entityToDTO(deletedTag);
        }
        return null;
    }
}
