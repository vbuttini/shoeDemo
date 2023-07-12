package com.vbuttini.demo.shoe.controller;

import com.vbuttini.demo.shoe.entity.Shoe;
import com.vbuttini.demo.shoe.service.ShoeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ViewController {

    private final ShoeService shoeService;

    @GetMapping("/index")
    public ModelAndView hello() {
        final var modelAndView = new ModelAndView("home");
        List<Shoe> shoeList = shoeService.getAll();
        modelAndView.addObject("products",shoeList);
        return modelAndView;
    }

}
