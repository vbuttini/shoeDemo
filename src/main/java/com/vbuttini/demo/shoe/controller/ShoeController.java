package com.vbuttini.demo.shoe.controller;

import com.vbuttini.demo.shoe.entity.Shoe;
import com.vbuttini.demo.shoe.service.ShoeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class is the controller for handling HTTP requests related to {@link Shoe} resources.
 * It provides endpoints for creating, updating, deleting, and retrieving shoe information.
 *
 * @author Vinicius Buttini
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/shoes")
public class ShoeController {

    private final ShoeService service;

    /**
     * Endpoint for create a new shoe.
     *
     * @param shoe The {@link Shoe} object to be created.
     * @return A ResponseEntity containing the created shoe and the URI of the created resource.
     */
    @PostMapping(produces = "application/json")
    public ResponseEntity<Shoe> save(@RequestBody Shoe shoe){
        shoe = service.save(shoe);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(shoe.getId()).toUri();
        return ResponseEntity.created(uri).body(shoe);
    }

    /**
     * Endpoint for updating an existing shoe by ID.
     *
     * @param shoe The updated shoe object.
     * @param id   The ID of the shoe to be updated.
     * @return A ResponseEntity containing the updated shoe.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Shoe> updateById(@RequestBody Shoe shoe, @PathVariable Long id){
        return ResponseEntity.ok().body(service.updateById(shoe, id));
    }

    /**
     * Endpoint for deleting a shoe by ID.
     *
     * @param id The ID of the shoe to be deleted.
     * @return A ResponseEntity indicating the success of the deletion operation.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for filtering shoes based on various attributes.
     *
     * @param size          The shoe size to filter by.
     * @param category      The shoe category to filter by.
     * @param color         The shoe color to filter by.
     * @param brand         The shoe brand to filter by.
     * @param dateCreated   The creation date of the shoes to filter by.
     * @param description   The shoe description to filter by.
     * @param pageable      The pagination information for the result set.
     * @return A ResponseEntity containing a Page of Shoe objects that match the specified filters.
     */
    @GetMapping("/getFiltered")
    public ResponseEntity<Page<Shoe>> filterByAttributes(
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) LocalDateTime dateCreated,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Pageable pageable
    ){
        return ResponseEntity.ok(service.filterByAttributes(size, category, color, brand, dateCreated, description, pageable));
    }

    /**
     * Endpoint for populating the shoe database with sample data.
     *
     * @return A ResponseEntity containing a list of Shoe objects that were populated in the database.
     */
    @PostMapping("/populateDatabase")
    public ResponseEntity<List<Shoe>> populateDatabase(){
        List<Shoe> shoeList = service.populateDatabase();
        return ResponseEntity.status(201).body(shoeList);
    }

}
