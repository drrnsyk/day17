package vttp2022.ssf.day17dice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2022.ssf.day17dice.services.DiceService;

@Controller
@RequestMapping(path="/roll")
public class DiceController {

    @Autowired
    private DiceService diceSvc;

    @GetMapping
    public String getRoll(Model model) {

        List<Integer> rolls = diceSvc.roll(3);

        model.addAttribute("dices", rolls);
        return "roll.html";
    }

}
