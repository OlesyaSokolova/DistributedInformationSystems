package ru.nsu.fit.sokolova.dis.service;

import ru.nsu.fit.sokolova.dis.dto.TagDTO;
import ru.nsu.fit.sokolova.dis.dto.TagHttpSimpleEntity;

import java.math.BigInteger;

public interface TagService {
    TagHttpSimpleEntity create(TagHttpSimpleEntity tag);
    void create(TagDTO tag);
    TagDTO read(BigInteger nodeId, String key);
    TagDTO update(BigInteger nodeId, String key, TagHttpSimpleEntity tag);
    TagDTO delete(BigInteger nodeId, String key);
}
