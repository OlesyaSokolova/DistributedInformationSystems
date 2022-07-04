package ru.nsu.fit.sokolova.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.sokolova.dis.entity.TagEntity;

import java.math.BigInteger;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, BigInteger> {

    @Query(
            value = "SELECT * FROM tags t WHERE t.node_id = ?1 AND t.key = ?2",
            nativeQuery = true)
    TagEntity findByNodeIdAndKey(BigInteger nodeId, String key);

    @Query(
            value = "DELETE FROM tags t WHERE t.node_id = ?1 AND t.key = ?2 RETURNING *",
            nativeQuery = true)
    TagEntity deleteByNodeIdAndKey(BigInteger nodeId, String key);
}
