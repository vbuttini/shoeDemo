package com.vbuttini.demo.shoe.service.exceptions;

/**
 * This class is a custom Exception dedicated for when shoe entity is not found on database
 *
 * @author Vinícius Buttini
 */
public class ShoeNotFoundException extends NullPointerException{
    public ShoeNotFoundException(String message){
        super(message);
    }
}