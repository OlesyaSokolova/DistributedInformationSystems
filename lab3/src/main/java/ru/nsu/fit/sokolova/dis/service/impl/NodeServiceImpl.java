package ru.nsu.fit.sokolova.dis.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.sokolova.dis.converters.NodeConverter;
import ru.nsu.fit.sokolova.dis.dto.NodeDTO;
import ru.nsu.fit.sokolova.dis.entity.NodeEntity;
import ru.nsu.fit.sokolova.dis.repository.NodeRepository;
import ru.nsu.fit.sokolova.dis.service.NodeService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NodeServiceImpl implements NodeService {

    private final NodeRepository nodeRepository;

    @Override
    public NodeDTO create(NodeDTO nodeDTO) {
        NodeEntity nodeEntity = NodeConverter.dtoToEntity(nodeDTO);
        NodeEntity savedEntity = nodeRepository.save(nodeEntity);
        return NodeConverter.entityToDTO(savedEntity);
    }

    @Override
    public NodeDTO read(BigInteger nodeId) {
        Optional<NodeEntity> foundEntity = nodeRepository.findById(nodeId);
        return foundEntity.map(NodeConverter::entityToDTO).orElse(null);
    }

    @Override
    public NodeDTO update(NodeDTO nodeDTO) {
        Optional<NodeEntity> foundEntity = nodeRepository.findById(nodeDTO.getId());
        if(foundEntity.isPresent()) {
            NodeEntity updatedEntity = NodeConverter.dtoToEntity(nodeDTO);
            nodeRepository.save(updatedEntity);
            return NodeConverter.entityToDTO(updatedEntity);
        }
        return null;
    }

    @Override
    public void delete(BigInteger nodeId) {
        nodeRepository.deleteById(nodeId);
    }

    @Override
    public List<NodeDTO> search(double lat, double lon, double radius) {
        List<NodeEntity> nodeEntities = nodeRepository.searchByPoint(lat, lon, radius);
        List<NodeDTO> nodeDTOS = new ArrayList<>();
        for (NodeEntity nodeEntity: nodeEntities) {
            nodeDTOS.add(NodeConverter.entityToDTO(nodeEntity));
        }
        return nodeDTOS;
    }
}
