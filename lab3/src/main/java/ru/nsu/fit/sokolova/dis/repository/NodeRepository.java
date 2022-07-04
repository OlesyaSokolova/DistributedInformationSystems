package ru.nsu.fit.sokolova.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.sokolova.dis.entity.NodeEntity;

import java.math.BigInteger;
import java.util.List;

//add index to increase speed of search

//https://habr.com/ru/company/postgrespro/blog/333878/
@Repository
public interface NodeRepository extends JpaRepository<NodeEntity, BigInteger> {
    @Query(
            value = "SELECT * FROM nodes " +
                    "WHERE (earth_box(ll_to_earth(?1, ?2), ?3) @> ll_to_earth(nodes.latitude, nodes.longitude)) " +
                    "AND (earth_distance(ll_to_earth(?1, ?2), ll_to_earth(nodes.latitude, nodes.longitude)) < ?3) " +
                    "ORDER BY earth_distance(ll_to_earth(?1, ?2), ll_to_earth(nodes.latitude, nodes.longitude)) ASC",
            nativeQuery = true
    )
    List<NodeEntity> searchByPoint(double lat, double lon, double radius);
}
