package ru.nsu.fit.sokolova.dis.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.sokolova.dis.dto.NodeDTO;
import ru.nsu.fit.sokolova.dis.service.NodeService;

import java.math.BigInteger;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/node")
@RequiredArgsConstructor
public class NodeController {
    private final NodeService nodeService;

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createNode(@RequestBody NodeDTO nodeDTO) {
        log.info("Called method: /node/create");
        NodeDTO savedNode = nodeService.create(nodeDTO);
        if (savedNode == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to create node, maybe you missed some required fields?");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Node was successfully created!");

    }

    @GetMapping(value = "/read/{nodeId}")
    public ResponseEntity<Object> readNode(@PathVariable BigInteger nodeId) {
        log.info("Called method: /node/read");
        NodeDTO foundNode = nodeService.read(nodeId);
        if (foundNode == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Node with id \"" + nodeId + "\" not found in database!");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foundNode);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateNode(@RequestBody NodeDTO nodeDTO) {
        log.info("Called method: /node/update");
        NodeDTO updatedEntity = nodeService.update(nodeDTO);
        if (updatedEntity == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Node with id \"" + nodeDTO.getId() + "\" not found in database!");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedEntity);
    }

    @DeleteMapping("/delete/{nodeId}")
    public ResponseEntity<Object> deleteNode(@PathVariable BigInteger nodeId) {
        log.info("Called method: /node/delete");
        NodeDTO foundNode = nodeService.read(nodeId);
        if (foundNode == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Node with id \"" + nodeId + "\" not found in database!");
        }
        else {
            nodeService.delete(nodeId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(foundNode);
        }
    }

    @GetMapping("/search/{lat}/{lon}/{radius}")
    public ResponseEntity<Object> searchNode(@PathVariable double lat, @PathVariable double lon,  @PathVariable double radius) {
        log.info("Called method: /node/search");
        List<NodeDTO> foundNodes = nodeService.search(lat, lon, radius);
        if (foundNodes.size() == 0) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Nodes for point with" +
                            " latitude = " + lat
                            + ", longitude = " + lon
                            + " and radius = " + radius
                            + " not found in database!");
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(foundNodes);
        }
    }
}