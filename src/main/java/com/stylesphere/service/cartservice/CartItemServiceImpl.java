package com.stylesphere.service.cartservice;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stylesphere.exception.UserException;
import com.stylesphere.exception.cart.CartItemException;
import com.stylesphere.model.Cart;
import com.stylesphere.model.CartItem;
import com.stylesphere.model.Product;
import com.stylesphere.model.User;
import com.stylesphere.repository.cart.CartItemRepository;
import com.stylesphere.repository.cart.CartRepository;
import com.stylesphere.service.userservice.UserService;

@Service
public class CartItemServiceImpl implements CartItemService {
	
	private CartItemRepository cartItemRepository;
	private UserService userService;
	private CartRepository cartRepository;
	
	public CartItemServiceImpl(CartItemRepository cartItemRepository,UserService userService,CartRepository cartRepository) {
		this.cartItemRepository = cartItemRepository;
		this.userService = userService;
		this.cartRepository = cartRepository;
	}
	
	

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		
		CartItem createdCartItem = cartItemRepository.save(cartItem);
		
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		CartItem item = findCartItemById(id);
		
		User user = userService.findUserById(item.getUserId());
		
		if(user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
		}
		
		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		CartItem cartItem = findCartItemById(cartItemId);
		
		User user = userService.findUserById(cartItem.getUserId());
		User reqUser = userService.findUserById(userId);
		
		if(user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItemId);
		}
		
		else {
			throw new UserException("You can't remove another user's item");
		}
		
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> option = cartItemRepository.findById(cartItemId);
		
		if(option.isPresent()) {
			return option.get();
		}
		throw new CartItemException("Cart Item not found with id : "+cartItemId);
	
	}

}
