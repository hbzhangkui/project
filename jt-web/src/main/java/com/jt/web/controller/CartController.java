package com.jt.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.User;
import com.jt.web.service.CartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;

	//跳转到购物车展现页面
	@RequestMapping("/show")
	public String findCartListByUserId(Model model){
		Long userId = UserThreadLocal.get().getId();
		//根据userId查询购物车列表信息
		List<Cart> cartList = 
		cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	//实现购物车新增
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart,@PathVariable Long itemId){
		Long userId = UserThreadLocal.get().getId();
		cart.setItemId(itemId);
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long itemId,@PathVariable Integer num){
		try {
			Long userId = UserThreadLocal.get().getId();
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);
			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车修改失败");
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId){
		/*User user = 
	(User) request.getSession().getAttribute("JT_USER");*/
		Long userId = UserThreadLocal.get().getId();
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
	
	
	
}
