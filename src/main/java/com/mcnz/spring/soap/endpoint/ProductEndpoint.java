package com.mcnz.spring.soap.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.mcnz.jee.soap.CreateProductRequest;
import com.mcnz.jee.soap.CreateProductResponse;
import com.mcnz.jee.soap.DeleteProductRequest;
import com.mcnz.jee.soap.DeleteProductResponse;
import com.mcnz.jee.soap.GetProductAllRequest;
import com.mcnz.jee.soap.GetProductAllResponse;
import com.mcnz.jee.soap.GetProductRequest;
import com.mcnz.jee.soap.GetProductResponse;
import com.mcnz.jee.soap.Product;
import com.mcnz.jee.soap.UpdateProductRequest;
import com.mcnz.jee.soap.UpdateProductResponse;
import com.mcnz.spring.soap.repository.ProductRepository;

@Endpoint
public class ProductEndpoint {
  
  @Autowired
  private ProductRepository productRepository;

    @PayloadRoot(namespace = "http://soap.jee.mcnz.com/", localPart = "createProductRequest")
    @ResponsePayload
    @CrossOrigin(origins = {
        "http://127.0.0.1:5500",  
        "*"  
    })
    public CreateProductResponse createProduct(@RequestPayload CreateProductRequest request) {
        Product product = request.getProduct();

        Product savedProduct = productRepository.save(product);

        CreateProductResponse response = new CreateProductResponse();
        response.setStatus("Product created successfully");
        response.setProductId(savedProduct.getId());

        return response;
    }

    @PayloadRoot(namespace = "http://soap.jee.mcnz.com/", localPart = "getProductRequest")
    @ResponsePayload
    @CrossOrigin(origins = {
        "http://127.0.0.1:5500",  
        "*"  
    })
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        int productId = request.getProductId();  
        Product product = productRepository.findById(productId).orElse(null);  

        GetProductResponse response = new GetProductResponse();
        if (product != null) {
            response.setProduct(product);  
        } else {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }

        return response; 
    }


    @PayloadRoot(namespace = "http://soap.jee.mcnz.com/", localPart = "updateProductRequest")
    @ResponsePayload
    @CrossOrigin(origins = {
        "http://127.0.0.1:5500",  
        "*"  
    })
    public UpdateProductResponse updateProduct(@RequestPayload UpdateProductRequest request) {
        Product updatedProduct = request.getProduct();
        int productId = updatedProduct.getId();  

        if (productRepository.existsById(productId)) {
            productRepository.save(updatedProduct);

            UpdateProductResponse response = new UpdateProductResponse();
            response.setStatus("Product updated successfully");
            return response;
        } else {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }
    }

    @PayloadRoot(namespace = "http://soap.jee.mcnz.com/", localPart = "deleteProductRequest")
    @ResponsePayload
    @CrossOrigin(origins = {
        "http://127.0.0.1:5500",  
        "*"  
    })
    public DeleteProductResponse deleteProduct(@RequestPayload DeleteProductRequest request) {
        int productId = request.getProductId();  
        Product product = productRepository.findById(productId).orElse(null);  
        
        if (product != null) {
            productRepository.deleteById(productId);
        }

        DeleteProductResponse response = new DeleteProductResponse();
        if (product != null) {
            response.setStatus("Product deleted successfully");
        } else {
            response.setStatus("Product with ID " + productId + " not found.");
        }

        return response;  
    }

    @PayloadRoot(namespace = "http://soap.jee.mcnz.com/", localPart = "getProductAllRequest")
    @ResponsePayload
    @CrossOrigin(origins = {
        "http://127.0.0.1:5500",  
        "*"  
    })
    public GetProductAllResponse getProductAll(@RequestPayload GetProductAllRequest request) {
        List<Product> allProducts = productRepository.findAll();
        
        GetProductAllResponse response = new GetProductAllResponse();
        response.getProducts().addAll(allProducts);
        return response;
    }
}
