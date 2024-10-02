package com.azakhiyah.demo_eshop.service.product;





import com.azakhiyah.demo_eshop.dto.ImageDto;
import com.azakhiyah.demo_eshop.dto.ProductDto;
import com.azakhiyah.demo_eshop.exceptions.ResourceNotFoundException;
import com.azakhiyah.demo_eshop.model.Category;
import com.azakhiyah.demo_eshop.model.Image;
import com.azakhiyah.demo_eshop.model.Product;
import com.azakhiyah.demo_eshop.repository.CategoryRepository;
import com.azakhiyah.demo_eshop.repository.ImageRepository;
import com.azakhiyah.demo_eshop.repository.ProductRepository;
import com.azakhiyah.demo_eshop.request.AddProductRequest;
import com.azakhiyah.demo_eshop.request.ProductUpdateRequest;
//import com.azakhiyah.demo_eshop.config.ShopConfig.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    //private ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        //check if the category is found in the db
        // if yes, set it as the new product categiry
        // if no, save is as a new category
        // the set as the new product category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
        .orElseGet(() -> {
            Category newCategory = new Category(request.getCategory().getName());
            return categoryRepository.save(newCategory);
        });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
            request.getName(),
            request.getBrand(),
            request.getPrice(),
            request.getInventory(),
            request.getDescription(),
            category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
            /*
             * fail : delete a row from the product table that is referenced by a foreign key (category_id) in the category table
             */
            // productRepository.findById(id).
            // ifPresentOrElse(productRepository::delete,()-> {throw new ResourceNotFoundException("Product not found!");});

            productRepository.findById(id).ifPresentOrElse(product -> {
            // Remove the association or delete related entries (if applicable)
            // Example: remove product from any related categories (depends on your design)
            product.setCategory(null);  // Unlink category (or handle relationships)
    
            productRepository.save(product);  // Update product with null category if needed
            productRepository.delete(product);  // Now delete the product
        }, () -> {
            throw new ResourceNotFoundException("Product not found!");
        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository :: save)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return  existingProduct;
    }




    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);        
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .collect(Collectors.toList());
        productDto.setImages(imageDtos);
        return productDto;

        // ProductDto productDto = modelMapper.map(product, ProductDto.class);
    
        //     // Fetch the images related to this product
        //     List<Image> images = imageRepository.findByProductId(product.getId());
            
        //     // Map each Image entity to an ImageDto and convert the image to Base64
        //     List<ImageDto> imageDtos = images.stream()
        //             .map(image -> {
        //                 ImageDto imageDto = new ImageDto();
        //                 imageDto.setId(image.getId());
        //                 imageDto.setDownloadUrl(image.getDownloadUrl());
        //                 imageDto.setFileName(image.getFileName());
        //                 imageDto.setFileType(image.getFileType());
        //                 imageDto.setImageBase64(image.getImageBase64());  // Convert Blob to Base64
        //                 return imageDto;
        //             })
        //             .collect(Collectors.toList());
            
        //     // Set the list of ImageDto in the ProductDto
        //             productDto.setImages(imageDtos);
            
        //             return productDto;
            }

}
