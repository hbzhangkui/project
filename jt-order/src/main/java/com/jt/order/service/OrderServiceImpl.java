package com.jt.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import com.jt.order.pojo.Order;
import com.jt.order.pojo.OrderItem;
import com.jt.order.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderMapper orderMapper;
	
	/**
	 * 要求实现三张同时入库
	 * 1.先入库order表
	 * 2.入库orderShipping
	 * 3.入库OrderItem
	 */
	@Override
	public String saveOrder(Order order) {
		Date date = new Date();
		String orderId = 
		order.getUserId() + "" + System.currentTimeMillis();
		//状态：1、未付款2、已付款3、未发货4、已发货5、交易成功6、交易关闭
		order.setStatus(1);
		order.setCreated(date);
		order.setUpdated(date);
		order.setOrderId(orderId);
		orderMapper.insert(order);	//实现订单入库
		System.out.println("订单入库成功！！！！！");
		
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流入库成功！！！！");
		
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单入库操作成功！！！！");
		
		return orderId;
	}

	/**
	 * 1.要求实现三张表关联查询
	 */
	@Override
	public Order findOrderById(String id) {
		
		return orderMapper.findOrderById(id);
	}

}
