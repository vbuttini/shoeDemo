package com.vbuttini.demo.shoe.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * This class represents a shoe entity.
 *
 * @author Vincius Buttini
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shoes")
public class Shoe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "dateCreated", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "stockQuantity", nullable = false)
    private Long stockQuantity;

    @Column(name = "description", nullable = false)
    private String description;

}
