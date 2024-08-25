package com.stylesphere.service.productservice;

import java.util.List;

import org.springframework.data.domain.Page;

import com.stylesphere.exception.product.ProductException;
import com.stylesphere.model.Product;
import com.stylesphere.request.product.CreateProductRequest;

public interface ProductService {
	
	public Product createProduct(CreateProductRequest request);
	
	public String deleteProduct(Long productId) throws ProductException;
	
	public Product updateProduct(Long productId,Product request) throws ProductException;
	
	public Product findProductById(Long id) throws ProductException;
	
	public List<Product> findProductByCategory(String category);
	
	public Page<Product> getAllProducts(String category,List<String> colors,Integer minPrice,Integer maxPrice,
			Integer minDiscount , String sort,String stock,Integer pageNumber,Integer pageSize);

}
