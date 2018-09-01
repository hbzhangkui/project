package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	//根据userId查询购物车信息
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult findCartListByUserId(@PathVariable Long userId){
		try {
			List<Cart> cartList = 
			cartService.findCartListByUserId(userId);
			return SysResult.oK(cartList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车查询失败");
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(String cartJSON){
		try {
			Cart cart = 
			objectMapper.readValue(cartJSON,Cart.class);
			cartService.saveCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"购物车新增失败");
	}
	
	
	//实现购物车数量的修改
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(
			@PathVariable Long userId,
			@PathVariable Long itemId,
			@PathVariable Integer num){
		try {
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);
			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"购物车数量修改失败");
	}
	
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(
			@PathVariable Long userId,
			@PathVariable Long itemId){
		try {
			
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cartService.deleteCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车删除失败");
	}
	
	
	
	
	
	
}
