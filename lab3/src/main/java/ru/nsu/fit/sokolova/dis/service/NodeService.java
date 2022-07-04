package ru.nsu.fit.sokolova.dis.service;

import ru.nsu.fit.sokolova.dis.dto.NodeDTO;
import java.math.BigInteger;
import java.util.List;

public interface NodeService {
    NodeDTO create(NodeDTO nodeDTO);
    NodeDTO read(BigInteger nodeId);
    NodeDTO update(NodeDTO nodeDTO);
    void delete(BigInteger nodeId);
    List<NodeDTO> search(double lat, double lon, double radius);
}
