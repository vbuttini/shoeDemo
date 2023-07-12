package com.vbuttini.demo.shoe.factory;

import com.vbuttini.demo.shoe.entity.Shoe;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

/**
 * This class is a factory of {@link Shoe}
 *
 * @author Vinicius Buttini
 */
@Component
public class ShoeFactory {

    Random random = new Random();

    public Shoe generateRandomShoe(){

        Shoe shoe = new Shoe();

        switch (random.nextInt(3)){
            case 0:
                shoe.setBrand("NIKE");
                break;
            case 1:
                shoe.setBrand("ADIDAS");
                break;
            default:
                shoe.setBrand("PUMA");
        }

        switch (random.nextInt(3)){
            case 0:
                shoe.setCategory("CORRIDA");
                break;
            case 1:
                shoe.setCategory("CHINELO");
                break;
            default:
                shoe.setCategory("CASUAL");
        }

        switch (random.nextInt(3)){
            case 0:
                shoe.setColor("PRETO");
                break;
            case 1:
                shoe.setColor("BRANCO");
                break;
            default:
                shoe.setColor("AZUL");
        }

        switch (random.nextInt(3)){
            case 0:
                shoe.setColor("PRETO");
                break;
            case 1:
                shoe.setColor("BRANCO");
                break;
            default:
                shoe.setColor("AZUL");
        }

        shoe.setSize(36 + random.nextInt(10));



        shoe.setDescription(
                shoe.getCategory() + " " +
                        shoe.getBrand() + " " +
                        shoe.getColor() + " " +
                        (char) (65 + random.nextInt(26)) +
                        (char) (65 + random.nextInt(26)) +
                        (char) (65 + random.nextInt(26)) +
                        1 + random.nextInt(1000)
        );

        shoe.setPrice(BigDecimal.valueOf(100 + random.nextInt(1500)));
        shoe.setStockQuantity(Long.valueOf(1 + random.nextInt(100000)));
        shoe.setDateCreated(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        return shoe;

    }

}
