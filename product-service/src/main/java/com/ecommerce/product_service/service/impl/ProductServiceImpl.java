package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.exception.ResourceNotFoundException;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // metodo para crear un producto
    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = productMapper.toProduct(requestDTO);
        Product savedProduct = productRepository.save(product);
        log.info("Product: {} guardado", savedProduct.getName());
        return productMapper.toProductResponseDTO(savedProduct);
    }

    // Metodo para listar todos los productos
    @Override
    public List<ProductResponseDTO> getAllsProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Product", "id" , id)
        );
        return productMapper.toProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Product", "id" , id)
        );
        productMapper.updateProductFromRequest(productRequestDTO, product);
        Product productUpdate = productRepository.save(product);
        log.info("Product: {} actualizado", productUpdate.getName());
        return productMapper.toProductResponseDTO(productUpdate);
    }

    @Override
    public void deleteProduct(String id) {
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Product", "id" , id);
        }
        productRepository.deleteById(id);
    }
}
