package com.vbuttini.demo.shoe.repository;

import com.vbuttini.demo.shoe.entity.Shoe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * This class is the repository of {@link Shoe}
 *
 * @author Vinicius Buttini
 */
@Repository
public interface ShoeRepository extends JpaRepository<Shoe, Long> {

    @Query("SELECT " +
            "shoe " +
            "FROM Shoe shoe " +
            "WHERE " +
            "( (:size IS NULL) OR (shoe.size = :size) ) " +
            "AND ( (:category IS NULL) OR (shoe.category = :category) ) " +
            "AND ( (:color IS NULL) OR (shoe.color = :color) ) " +
            "AND ( (:brand IS NULL) OR (shoe.brand = :brand) ) " +
            "AND ( (:dateCreated IS NULL) OR (shoe.dateCreated = :dateCreated) ) " +
            "AND ( (:description IS NULL) OR (UCASE(shoe.description) LIKE UCASE(:description)) )"
    )
    Page<Shoe> filterByAttributes(
                        Integer size,
                        String category,
                        String color,
                        String brand,
                        LocalDateTime dateCreated,
                        String description,
                        Pageable pageable
    );

}
