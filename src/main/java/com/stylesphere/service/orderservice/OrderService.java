package com.stylesphere.service.orderservice;

import java.util.List;

import com.stylesphere.exception.order.OrderException;
import com.stylesphere.model.Address;
import com.stylesphere.model.Order;
import com.stylesphere.model.User;

public interface OrderService {
	public Order createOrder(User user, Address shippingAddress);
	
	public Order findOrderById(Long orderId) throws OrderException;
	
	public List<Order> userOrderHistory(Long userId);
	
	public Order placedOrder(Long orderId) throws OrderException;
	
	public Order confirmedOrder(Long orderId) throws OrderException;
	
	public Order shippedOrder(Long orderId) throws OrderException;
	public Order deliveredOrder(Long orderId) throws OrderException;
	
	public Order cancelledOrder(Long orderId) throws OrderException;
	
	public List<Order> getAllOrders();
	
	public void deleteOrder(Long orderId) throws OrderException;
}
