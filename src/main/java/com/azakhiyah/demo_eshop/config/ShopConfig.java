package com.azakhiyah.demo_eshop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
}
