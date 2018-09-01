package com.jt.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.SysResult;
import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/create")
	@ResponseBody
	public SysResult saveOrder(String orderJSON){
		//将orderJSON转化为order对象
		try {
			Order order = objectMapper.readValue(orderJSON,Order.class);
			String orderId = orderService.saveOrder(order);
			return SysResult.oK(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201,"新增订单失败");
	}
	
	
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Order findOrderById(@PathVariable String orderId){
		
		return orderService.findOrderById(orderId);
	}
	
	
	
	
	
}
