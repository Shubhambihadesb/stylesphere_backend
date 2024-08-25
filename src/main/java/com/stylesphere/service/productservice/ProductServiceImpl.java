package com.stylesphere.service.productservice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stylesphere.exception.product.ProductException;
import com.stylesphere.model.Category;
import com.stylesphere.model.Product;
import com.stylesphere.repository.category.CategoryRepository;
import com.stylesphere.repository.product.ProductRepository;
import com.stylesphere.request.product.CreateProductRequest;
import com.stylesphere.service.userservice.UserService;

@Service
public class ProductServiceImpl implements ProductService {
	
	
	private ProductRepository productRepository;
	private UserService userService;
	private CategoryRepository categoryRepository;
	
	public ProductServiceImpl(ProductRepository productRepository,UserService userService,CategoryRepository categoryRepository) {
		
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.userService = userService;

	}
	
	

	@Override
	public Product createProduct(CreateProductRequest request) {
		
		Category topLevel = categoryRepository.findByName(request.getTopLevelCategory());
		
		if(topLevel==null) {
			Category topLevelCategory = new Category();
			topLevelCategory.setName(request.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			
			
			topLevel = categoryRepository.save(topLevelCategory);
		}
		
		Category secondLevel = categoryRepository.findByNameAndParent(request.getSecondLevelCategory(),topLevel.getName());
		if(secondLevel==null) {
			Category secondLevelCategory = new Category();
			secondLevelCategory.setName(request.getSecondLevelCategory());
			secondLevelCategory.setParentCategory(topLevel);
			secondLevelCategory.setLevel(2);
			
			topLevel = categoryRepository.save(secondLevelCategory);
		}
		
		Category thirdLevel = categoryRepository.findByNameAndParent(request.getThirdLevelCategory(),secondLevel.getName());
		if(thirdLevel==null) {
			Category thirdLevelCategory = new Category();
			thirdLevelCategory.setName(request.getThirdLevelCategory());
			thirdLevelCategory.setParentCategory(secondLevel);
			thirdLevelCategory.setLevel(3);
			
			
			topLevel = categoryRepository.save(thirdLevelCategory);
		}
		
		Product product = new Product();
		product.setTitle(request.getTitle());
		product.setColor(request.getColor());
		product.setDescription(request.getDescription());
		product.setDiscountedPrice(request.getDiscountedPrice());
		product.setDiscountPercent(request.getDiscountPercent());
		product.setImageUrl(request.getImageUrl());
		product.setBrand(request.getBrand());
		product.setPrice(request.getPrice());
		product.setSizes(request.getSize());
		product.setQuantity(request.getQuantity());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());
		
		Product savedProduct=  productRepository.save(product);
		
		return savedProduct;
		
		
		
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		
		
		Product product = findProductById(productId);
		product.getSizes().clear();
		productRepository.delete(product);
		
		return "Product Deleted Successfully...";
	}

	@Override
	public Product updateProduct(Long productId, Product request) throws ProductException {
		Product product = findProductById(productId);
		
		if(request.getQuantity()!=0) {
			product.setQuantity(request.getQuantity());
		}
		
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {

		Optional<Product> opt = productRepository.findById(id);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new ProductException("Product Not Found With Id : "+id);
		
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProducts(String category, List<String> colors, Integer minPrice, Integer maxPrice,
			Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

		Pageable pageble = PageRequest.of(pageNumber, pageSize);
		
		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		
		if(!colors.isEmpty()) {
			products = products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
		}
		
		
		if(stock!=null) {
			if(stock.equals("in_stock")) {
				products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
				
			}
			else if(stock.equals("out_of_stock")) {
				products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
				
			}
		}
		
		int startIndex =(int) pageble.getOffset();
		int endIndex = Math.min(startIndex+pageble.getPageSize(),products.size());
		
		List<Product> pageContent = products.subList(startIndex, endIndex);
		
		Page<Product> filteredProducts = new PageImpl<>(pageContent,pageble, products.size());
		
		
		return filteredProducts;
	}

}
