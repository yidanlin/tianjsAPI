package com.tianan.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.tianan.model.User;
import com.tianan.restful.HttpSendUtil;
import com.tianan.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/register")
@Api("用户注册")
public class RegisterController {
	private Logger logger = Logger.getLogger(RegisterController.class);

	@Value("${ManagementUser_URL}")
	private String ManagementUser_URL;
	
	@Autowired
	private UserService userService;
	
	//注册用户，使用POST，传输数据
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册接口", code = 200, produces = "application/json")
    @ResponseBody
    public JSONObject registerPost(HttpServletRequest request,
    		                   @RequestParam(value="email") String email,@RequestParam(value="password") String password,@RequestParam(value="telephone") String telephone,@RequestParam(value="imageVerificationCode") String imageVerificationCode,@RequestParam(value="roleType") String roleType) throws Exception {
    	JSONObject jsonObject =  new JSONObject();
    	//    	String kaptcha = request.getParameter("kaptcha");
        String captcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
	    System.out.println("用户输入的验证码是："+imageVerificationCode+";系统生成的验证码是："+captcha);
		
		if("".equals(imageVerificationCode)){
			jsonObject.put("code", "500");
			jsonObject.put("info", "验证码为空");
        	return jsonObject;
		}else if(!captcha.equals(imageVerificationCode)){
			jsonObject.put("code", "500");
			jsonObject.put("info", "验证码错误");
        	return jsonObject;
		}else{
			User user = new User();
			user.setId(3);
			user.setName("test");
			user.setAge(23);
			user.setAddress("test123");
			//使用userService处理业务
			String result = userService.insert(user);
			if(result == null){
				String res = "email="+"137763911%40qq.com"+"&password="+"123456"+"&telephone="+"13636597537"+"&imageVerificationCode="+"w2r4"+"&roleType="+"1";
				String resultRep = null;
				try {
					resultRep = HttpSendUtil.sendHttp(ManagementUser_URL,res);
				} catch (Exception e) {
					throw new Exception("调用管理用户注册接口异常" + e.getMessage());
				}
				JSONObject resultObj = JSONObject.parseObject(resultRep);
				System.out.println(resultObj.get("code"));
				if("200".equals(resultObj.get("code"))){
					jsonObject.put("code", "200");
					jsonObject.put("info", "注册成功");
					logger.info("注册成功");
					return jsonObject;
				}else{
					jsonObject.put("code", resultObj.get("code"));
					jsonObject.put("info", "注册失败");
					logger.info("注册失败");
					return jsonObject;
				}
			}else{
				jsonObject.put("code", "500");
				jsonObject.put("info", result);
				return jsonObject;
			}
		}
    }
}
