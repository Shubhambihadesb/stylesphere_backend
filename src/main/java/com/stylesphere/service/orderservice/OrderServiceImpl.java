package com.stylesphere.service.orderservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stylesphere.exception.order.OrderException;
import com.stylesphere.model.Address;
import com.stylesphere.model.Order;
import com.stylesphere.model.User;
import com.stylesphere.repository.cart.CartRepository;
import com.stylesphere.service.cartservice.CartService;
import com.stylesphere.service.productservice.ProductService;

@Service
public class OrderServiceImpl implements OrderService {
	
	private CartRepository cartRepository;
	private CartService cartService;
	private ProductService productService;
	
	public OrderServiceImpl(CartRepository cartRepository,CartService cartService,ProductService productService) {
		this.cartRepository = cartRepository;
		this.cartService =cartService;
		this.productService = productService;
	}

	@Override
	public Order createOrder(User user, Address shippingAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> userOrderHistory(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order cancelledOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		
	}

}
