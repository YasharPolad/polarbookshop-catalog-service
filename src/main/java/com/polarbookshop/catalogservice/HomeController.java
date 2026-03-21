package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.configuration.PolarConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  private PolarConfiguration polarConfiguration;

  public HomeController(PolarConfiguration polarConfiguration) {
    this.polarConfiguration = polarConfiguration;
  }

  @GetMapping("/")
    public String getGreeting(){
        return polarConfiguration.getGreeting();
    }
}
