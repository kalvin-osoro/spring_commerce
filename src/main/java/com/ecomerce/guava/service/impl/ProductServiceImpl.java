package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.dto.ProductDto;
import com.ecomerce.guava.exceptions.productNotExistException;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.model.Product;
import com.ecomerce.guava.repository.ProductRepo;
import com.ecomerce.guava.service.FileStorageService;
import com.ecomerce.guava.service.ProductService;
import com.ecomerce.guava.utils.ImageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepository;

    private final FileStorageService fileStorageService;


    @Value("${spring.server.name}")
    private String serverName;



    @Override
    public Object addNewProduct(String productDetails,
                                MultipartFile img
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ProductDto productDto = mapper.readValue(
                    productDetails, ProductDto.class);
            if (productDto == null) throw new RuntimeException("Bad request");
            Product product =new Product();

//            productDto.setName(product.getName());
//            productDto.setPrice(product.getPrice());
//            productDto.setDescription(product.getDescription());
//            productDto .setCategoryId(product.getCategory().getId());




            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
//            product.setCategory(product.getCategory().getId());
//            product.setCategoryId(product.getCategory().getId());


//            productRepository.save(product);

            Product savedProduct = productRepository.save(product);

            if (img.isEmpty()) {
                throw new RuntimeException("Image file is empty");
            }
//
////          Product savedproduct = productRepository.save(product);

            String imagePath = fileStorageService.saveFileWithSpecificFileNameV(
                    "product_" + savedProduct.getId() + ".PNG", img, ImageUtils.getSubFolder());
//

            //save
            List<String> filePathList = new ArrayList<>();
            filePathList.add(imagePath);
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {
//                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()

//                        .path("/upload/" + ImageUtils.getSubFolder() + "/")
//                        .path(filePath)
//                        .toUriString();
//                downloadUrlList.add(downloadUrl);

                String fileDownLoadUri = UriComponentsBuilder.fromUriString(serverName)
                        .path("/product/view-product")
                        .queryParam("fileName", filePath)
                        .build()
                        .encode()
                        .toUriString();
                log.info("The composed path: " + fileDownLoadUri);

                //save to db
                product.setImagePath(fileDownLoadUri);
                productRepository.save(product);


                log.info("downloadUrl is {}", fileDownLoadUri);
                log.info("filePath is {}", filePath);


            }
//            return true;
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            log.error("Error occurred while adding product", e);
        }
        return false;
    }



    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findById(Long productId) throws productNotExistException {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new productNotExistException("Product id is invalid: " + productId);
        }
        return optionalProduct.get();
    }

}
