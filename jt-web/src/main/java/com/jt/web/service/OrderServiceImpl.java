package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Order;
import com.sun.corba.se.impl.ior.ObjectAdapterIdArray;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private HttpClientService httpClient;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
			

	//httpClient方式
	@Override
	public String saveOrder(Order order) {
		String url = "http://order.jt.com/order/create";
		String orderJSON = null; 
		try {
			orderJSON = objectMapper.writeValueAsString(order);
			Map<String,String> params = new HashMap<>();
			params.put("orderJSON", orderJSON);
			String sysResultJSON = 
					httpClient.post(url, params);
			SysResult sysResult = 
			objectMapper.readValue(sysResultJSON, SysResult.class);
			if(sysResult.getStatus() == 200){
				
				return (String) sysResult.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}


	@Override
	public Order findOrderById(String id) {
		String url = "http://order.jt.com/order/query/"+id;
		String orderJSON = httpClient.get(url);
		Order order = null;
		try {
			order = objectMapper.readValue(orderJSON, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}
	
	
	
}
