package com.vbuttini.demo.shoe.service;

import com.vbuttini.demo.shoe.entity.Shoe;
import com.vbuttini.demo.shoe.factory.ShoeFactory;
import com.vbuttini.demo.shoe.repository.ShoeRepository;
import com.vbuttini.demo.shoe.service.exceptions.ShoeNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Related tests for {@link ShoeService} class
 *
 * @author Vinicius Buttini
 */
class ShoeServiceTest {

    @InjectMocks
    private ShoeService shoeService;

    @Mock
    private ShoeRepository shoeRepository;
    @Mock
    private ShoeFactory shoeFactory;


    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }

    private Shoe buildShoe(){
        return Shoe.builder()
                .id(1L)
                .brand("BRAND_TEST")
                .category("CATEGORY_TEST")
                .color("COLOR_TEST")
                .dateCreated(LocalDateTime.MAX)
                .description("DESCRIPTION_TEST")
                .stockQuantity(1000L)
                .build();
    }

    @Test
    @DisplayName("save - if shoe object is valid then save shoe")
    void save_whenShoeIsValid_thenSaveShoe() {
        Shoe shoe = buildShoe();
        when(shoeRepository.save(shoe)).thenReturn(shoe);
        Assertions.assertDoesNotThrow(()-> shoeService.save(shoe));
    }

    @Test
    @DisplayName("updateById - when id is valid then update attributes and save shoe")
    void updateById_whenIdIsValid_thenUpdateAndSaveShoe() {
        Shoe shoe = buildShoe();
        when(shoeRepository.findById(anyLong())).thenReturn(Optional.of(shoe));
        Assertions.assertDoesNotThrow(()-> shoeService.updateById(shoe, anyLong()));
    }

    @Test
    @DisplayName("updateById - when id is not valid then throws ShoeNotFoundException")
    void updateById_whenIdIsNotValid_thenThrowsShoeNotFoundException() {
        when(shoeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ShoeNotFoundException.class, ()-> shoeService.updateById(buildShoe(),anyLong()));
    }

    @Test
    @DisplayName("deleteById - if id is not valid then trows ShoeNotFoundException")
    void deleteById_whenIdIsNotValid_thenThrowsShoeNotFoundException() {
        when(shoeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ShoeNotFoundException.class, ()-> shoeService.deleteById(anyLong()));
    }

    @Test
    @DisplayName("filterByAttributes - if shoe with defined attributes exists, then return pages of shoes")
    void filterByAttributes_whenFilteredShoeExists_thenReturnPageOfShoes() {

        Integer size = 42;
        String category = "TENIS";
        String color = "AZUL";
        String brand = "NIKE";
        LocalDateTime dateCreated = LocalDateTime.now();
        String description = "TENIS NIKE PRETO ADF200";
        Pageable pageable = mock(Pageable.class);

        when(shoeRepository.filterByAttributes(size, category, color, brand, dateCreated, description, pageable))
                .thenReturn(new PageImpl<>(List.of(buildShoe())));

        Assertions.assertDoesNotThrow(() -> shoeService.filterByAttributes(size, category, color, brand, dateCreated, description, pageable));
    }

    @Test
    @DisplayName("populateDatabase - if called, insert a list of 100 shoes with random attributes on database")
    void populateDatabase_whenCalled_thenInsertListOfShoesOnDatabase() {

        Shoe shoe = buildShoe();

        when(shoeService.populateDatabase()).thenReturn(List.of(shoe));
        when(shoeRepository.saveAll(anyList())).thenReturn(List.of(shoe));

        Assertions.assertDoesNotThrow(()-> shoeService.populateDatabase());

    }

}
