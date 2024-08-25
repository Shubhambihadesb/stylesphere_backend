package com.stylesphere.service.cartservice;

import com.stylesphere.exception.UserException;
import com.stylesphere.exception.cart.CartItemException;
import com.stylesphere.model.Cart;
import com.stylesphere.model.CartItem;
import com.stylesphere.model.Product;

public interface CartItemService {
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException,UserException;
	
	public CartItem isCartItemExist(Cart cart, Product product,String size, Long userId);
	
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartItemById(Long cartItem) throws CartItemException;
}
