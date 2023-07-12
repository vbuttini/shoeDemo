package com.vbuttini.demo.shoe.service;

import com.vbuttini.demo.shoe.entity.Shoe;
import com.vbuttini.demo.shoe.factory.ShoeFactory;
import com.vbuttini.demo.shoe.repository.ShoeRepository;
import com.vbuttini.demo.shoe.service.exceptions.ShoeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing {@link Shoe} entities.
 *
 * @author Vinicius Buttini
 */
@Service
@RequiredArgsConstructor
public class ShoeService {

    private final ShoeRepository repository;
    private final ShoeFactory factory;

    /**
     * Saves a shoe in the database
     *
     * @param shoe The shoe object to be saved.
     * @return The saved shoe.
     */
    public Shoe save(Shoe shoe) {
        shoe.setDateCreated(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return repository.save(shoe);
    }

    /**
     * Updates a shoe by ID.
     * Retrieves the existing shoe from the database, copies the updated properties,
     * and saves the changes in the database.
     *
     * @param shoe The updated shoe object.
     * @param id   The ID of the shoe to be updated.
     * @return The updated shoe.
     * @throws ShoeNotFoundException if the shoe does not exist.
     */
    public Shoe updateById(Shoe shoe, Long id) {
        Shoe shoeSaved = repository.findById(id).orElseThrow(()-> new ShoeNotFoundException("Shoe not exists"));
        BeanUtils.copyProperties(shoe, shoeSaved, "id", "dateCreated");
        return repository.save(shoeSaved);
    }

    /**
     * Deletes a shoe by its ID.
     *
     * @param id The ID of the shoe to be deleted.
     * @throws ShoeNotFoundException if the shoe does not exist.
     */
    public void deleteById(Long id){
        repository.delete(
                repository.findById(id)
                        .orElseThrow(()-> new ShoeNotFoundException("Shoe not exists"))
        );
    }

    /**
     * Filters shoes based on the specified attributes.
     *
     * @param size          The shoe size to filter by.
     * @param category      The shoe category to filter by.
     * @param color         The shoe color to filter by.
     * @param brand         The shoe brand to filter by.
     * @param dateCreated   The creation date of the shoes to filter by.
     * @param description   The shoe description to filter by.
     * @param pageable      The pagination information for the result set.
     * @return A Page of Shoe objects that match the specified filters.
     */
    public Page<Shoe> filterByAttributes(Integer size,
                                         String category,
                                         String color,
                                         String brand,
                                         BigDecimal price,
                                         LocalDateTime dateCreated,
                                         String description,
                                         Pageable pageable
    ){
        return repository.filterByAttributes(size, category, color, brand, price, dateCreated, description, pageable);
    }

    /**
     * Populates the shoe database with sample data.
     * Generates a list of random Shoe objects using the ShoeFactory,
     * and saves them in the database.
     *
     * @return A list of Shoe objects that were populated in the database.
     */
    public List<Shoe> populateDatabase() {
        List<Shoe> shoeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            shoeList.add(factory.generateRandomShoe());
        }
        return repository.saveAll(shoeList);
    }

    public List<Shoe> getAll() {
        return repository.findAll();
    }
}
