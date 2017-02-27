package com.tianan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.tianan.model.User;
import com.tianan.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/users")
@Api("用户信息查询")
public class UserController {
	@Autowired
	private UserService userService;	

	@RequestMapping(value = "/name/{name}/{sex}", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "姓名模糊查询用户")
	public List<User> likeName(@PathVariable String name,@PathVariable String sex) {
		PageHelper.startPage(1, 10);
		return userService.likeName(name);
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "根据id查询用户",produces = "application/json")
	@ResponseBody
	public User getById(@PathVariable Long id) {
		PageHelper.startPage(1, 10);
		return userService.getById(id);
	}

	@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "分页查询用户")
	public List<User> getUsers(@RequestParam(defaultValue = "10") int offset, @RequestParam(defaultValue = "1") int limit) {
		PageHelper.startPage(limit, offset);
		return userService.getUsers();
	}
	
//	@ResponseBody  
//    @RequestMapping(value = "/jsonTest5", method = RequestMethod.POST)  
//    public ModelMap jsonTest5(@RequestBody JSONObject jsonObject) {  
//        String name = jsonObject.getString("name");  
//        ModelMap map = new ModelMap();  
//        map.addAttribute("demoName",name);  
//        return map;  
//    } 
	
	@ResponseBody  
    @RequestMapping(value = "/jsonTest6", method = RequestMethod.POST)  
	@ApiOperation(value = "测试接口6", code = 200, produces = "application/json")
	public JSONObject testFormJson(@RequestParam ("filename" ) String filename,@RequestParam ("filename1" ) String filename1){
		JSONObject jsonObject =  new JSONObject();
		jsonObject.put("test", filename);
		jsonObject.put("test1", filename1);
		return jsonObject;
	}
}
