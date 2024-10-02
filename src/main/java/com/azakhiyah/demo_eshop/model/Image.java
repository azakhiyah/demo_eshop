package com.azakhiyah.demo_eshop.model;

import javax.persistence.*;
import java.util.Base64;
//import java.io.IOException;
import java.sql.SQLException;


//import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;

  
    @Lob
    //@JsonIgnore
    private Blob image;

    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public String getImageBase64() {
        try {
            if (image != null) {
                // Retrieve the binary data from Blob and convert to byte array
                byte[] bytes = image.getBytes(1, (int) image.length());
                // Convert byte array to Base64 string
                return Base64.getEncoder().encodeToString(bytes);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle SQL exception
        }
        return null;
    }
}