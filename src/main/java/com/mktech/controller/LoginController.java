/**
 * @author Chnyge Lin
 */
package com.mktech.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mktech.entity.DbLimo;
import com.mktech.service.impl.DbLimoServiceImpl;
import com.mktech.service.impl.SnUserServiceImpl;

/**
 * controller 4 index
 * 
 * @author Chnyge Lin
 * 
 */
@Controller
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private SnUserServiceImpl snUserService;
	
	@Resource
	private DbLimoServiceImpl dbLimoService;
	
	@RequestMapping(path = {"/reg"}, method = {RequestMethod.POST})
	public String reg(Model model,
					@RequestParam("username") String username,
					@RequestParam("password") String password){
		
		try {
			Map<String, String> map = snUserService.register(username, password);
			if(map.containsKey("msg")){
				model.addAttribute("msg", map.get("msg"));
				return "WEB-INF/login/index";
			}
			
			return "redirect:/";
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("注册异常"+e.getMessage());
			model.addAttribute("msg", "服务器异常");
			return "WEB-INF/login/index";
		}
	}
	
	/**
	 * 重定向
	 * @return
	 */
	@RequestMapping(value = { "/index" }, method = {RequestMethod.GET})
	public String index() {
		return "WEB-INF/login/index";
	}
	/**
	 * 登录
	 * @param model
	 * @param username
	 * @param password
	 * @param next
	 * @param response
	 * @return
	 */
	@RequestMapping(path = {"/login"},method = {RequestMethod.POST})
	public String login(Model model,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value = "next",required = false) String next,
			HttpServletResponse response){
		System.out.println("收到登录请求 username:"+username);
		try {
			Map<String, String> map = snUserService.login(username, password);
			if(map.containsKey("msg")){
				model.addAttribute("msg", map.get("msg"));
				model.addAttribute("username", username);
				return "WEB-INF/login/index";
			}else if(map.containsKey("ticket")){
				Cookie cookie = new Cookie("ticket", map.get("ticket"));
				cookie.setPath("/");
				long tmptime = Long.parseLong("1522701557277");
				response.addCookie(cookie);
				model.addAttribute("tmpname",username);
				model.addAttribute("tmptime",tmptime);
				return "WEB-INF/controller_main/new_index";
			}
			return "redirect:/";
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("登录异常"+e.getMessage());
			model.addAttribute("msg", "服务器异常");
			return "WEB-INF/login/index";
		}
	}
	
	/**
	 * 登出
	 * @param ticket
	 * @return
	 */
	@RequestMapping(path = {"/logout"}, method = {RequestMethod.GET})
	public String logout(@CookieValue("ticket") String ticket){
		snUserService.logout(ticket);
		return "redirect:/";
	}
	
	/**
	 * 控制器tab入口
	 * @param model
	 * @param response
	 * @param ticket
	 * @return
	 */
	@RequestMapping(path = {"/ControllerIndex"},method = {RequestMethod.GET})
	public String ControllerIndex(Model model,
									HttpServletResponse response,
									@CookieValue("ticket") String ticket){
		long tmptime = Long.parseLong("1522701557277");
		System.out.println(snUserService.getUserByTicket(ticket).getUsername());
		model.addAttribute("tmpname",snUserService.getUserByTicket(ticket).getUsername());
		model.addAttribute("tmptime",tmptime);
		return "WEB-INF/controller_main/new_index";
	}
	/**
	 * 测试方法入口
	 * testLin.do?
	 * pathvariable
	 * @throws IOException 
	 */
	@RequestMapping(path = {"/testLin"},method = {RequestMethod.GET})
	@ResponseBody
	public String testLin(Model model,
			@RequestParam("min") Integer min,
			@RequestParam("max") Integer max,
			HttpServletResponse response) throws IOException{

		if(min>max){
			return "wrong!";
		}else if (max-min>=100000){
			return "Range is unsupported!Supported number of data should not be more than 100,000. ";
		}
		byte[] bytes = dbLimoService.export2ExcelNotPernament(min, max);
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename=" + UUID.randomUUID() + ".xlsx");
		response.setContentLength(bytes.length);
		response.getOutputStream().write(bytes);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		//测试是否能释放内存
		bytes = null;
		return "success!";
		
	}
	
	
}
