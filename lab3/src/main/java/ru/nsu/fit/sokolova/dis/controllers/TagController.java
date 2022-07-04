package ru.nsu.fit.sokolova.dis.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.sokolova.dis.dto.TagDTO;
import ru.nsu.fit.sokolova.dis.dto.TagHttpSimpleEntity;
import ru.nsu.fit.sokolova.dis.service.TagService;

import java.math.BigInteger;

@Log4j2
@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createTag(@RequestBody TagHttpSimpleEntity tag) {
        log.info("Called method: /tag/create");
        TagHttpSimpleEntity savedTag = tagService.create(tag);
        if(savedTag == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Node with id " + tag.getNodeId() + " not found in database!");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Tag was successfully created!");

    }

    @GetMapping(value = "/read/{nodeId}/{key}")
    public ResponseEntity<Object> readTag(@PathVariable BigInteger nodeId, @PathVariable String key) {
        log.info("Called method: /tag/read");
        TagDTO foundTag = tagService.read(nodeId, key);
        if(foundTag == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Tag with node id \"" + nodeId + "\" and key \"" + key + "\" not found in database!");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(foundTag);
    }

    @PutMapping(value = "/update/{nodeId}/{key}")
    public ResponseEntity<Object> updateTag(@PathVariable BigInteger nodeId, @PathVariable String key, @RequestBody TagHttpSimpleEntity tag) {
        log.info("Called method: /tag/update");
        TagDTO tagToUpdate = tagService.update(nodeId, key, tag);
        if(tagToUpdate == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Tag with node id \"" + nodeId + "\" and key \"" + key + "\" not found in database!");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagToUpdate);
    }

    @DeleteMapping("/delete/{nodeId}/{key}")
    public ResponseEntity<Object> deleteTag(@PathVariable BigInteger nodeId, @PathVariable String key) {
        log.info("Called method: /tag/delete");
        TagDTO deletedTag = tagService.delete(nodeId, key);
        if(deletedTag == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Tag with node id \"" + nodeId + "\" and key \"" + key + "\" not found in database!");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deletedTag);
    }
}
