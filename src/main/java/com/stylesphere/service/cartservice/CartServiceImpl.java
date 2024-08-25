package com.stylesphere.service.cartservice;

import org.springframework.stereotype.Service;

import com.stylesphere.exception.product.ProductException;
import com.stylesphere.model.Cart;
import com.stylesphere.model.CartItem;
import com.stylesphere.model.Product;
import com.stylesphere.model.User;
import com.stylesphere.repository.cart.CartRepository;
import com.stylesphere.request.cart.AddItemRequest;
import com.stylesphere.service.productservice.ProductService;

@Service
public class CartServiceImpl implements CartService{
	
	private CartRepository cartRepository;
	private CartItemService cartItemService;
	private ProductService productService;
	
	public CartServiceImpl(CartRepository cartRepository,CartItemService cartItemService,ProductService productService) {
		this.cartRepository = cartRepository;
		this.cartItemService = cartItemService;
		this.productService = productService;
	}

	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest request) throws ProductException {
		
		Cart cart = cartRepository.findByUserId(userId);
		
		Product product = productService.findProductById(request.getProductId());
		
		
		CartItem isPresent = cartItemService.isCartItemExist(cart, product, request.getSize(), userId);
		
		if(isPresent==null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(request.getQuantity());
			cartItem.setUserId(userId);
			
			int price =request.getQuantity()*product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(request.getSize());
			
			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
			
		}
		
		
		return "Item Added To Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);
		
		int totalPrice = 0;
		int totalDiscountedPrice=0;
		int totalItem = 0;
		for(CartItem cartItem: cart.getCartItems()) {
			totalPrice=totalPrice+cartItem.getPrice();
			totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedPrice();
			totalItem=totalItem+cartItem.getQuantity();
		}
		cart.setTotalItem(totalItem);
		cart.setTotalDiscountedprice(totalDiscountedPrice);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice-totalDiscountedPrice);
		
		return cartRepository.save(cart);
	}
	
	

}
