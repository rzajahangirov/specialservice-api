package az.techvibeds.specialservice.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "Welcome to SpecialService API";
    }
}
