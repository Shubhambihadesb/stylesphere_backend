package com.stylesphere.service.cartservice;

import com.stylesphere.exception.product.ProductException;
import com.stylesphere.model.Cart;
import com.stylesphere.model.User;
import com.stylesphere.request.cart.AddItemRequest;

public interface CartService {
	
		
		public Cart createCart(User user);
		
		public String addCartItem(Long userId,AddItemRequest request)throws ProductException;
		
		public Cart findUserCart(Long userId);

}
