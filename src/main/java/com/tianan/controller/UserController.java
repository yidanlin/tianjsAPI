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
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianan.model.User;
import com.tianan.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/users")
@Api("用户信息查询")
public class UserController {
	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/name/{name}/{sex}", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "姓名模糊查询用户")
	public List<User> likeName(@PathVariable String name,@PathVariable String sex) {
		return userService.selectList(new EntityWrapper<User>().eq("name", name).ne("role", sex));
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "根据id查询用户",produces = "application/json")
	@ResponseBody
	public User getById(@PathVariable Long id) {
		System.err.println("删除一条数据：" + userService.deleteById(1L));
		System.err.println("deleteAll：" + userService.deleteAll());
		System.err.println("插入一条数据：" + userService.insert(new User(1L, "张三", 17, 1)));
		User user = new User("张三", 17, 1);
		boolean result = userService.insert(user);
		System.out.println("插入或更新一条数据："+userService.insertOrUpdate(new User(1L, "王五", 19, 3)));
		// 自动回写的ID
		Long idl = user.getId();
		System.err.println("插入一条数据：" + result + ", 插入信息：" + user.toString());
		System.err.println("查询：" + userService.selectById(idl).toString());
		System.err.println("更新一条数据：" + userService.updateById(new User(1L, "三毛", 18, 2)));
//		return userService.selectById(1L);
		return userService.selectById(id);
	}

	@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "分页查询用户")
	public Page<User> getUsers(@RequestParam(defaultValue = "10") int offset, @RequestParam(defaultValue = "1") int limit) {
		return userService.selectPage(new Page<User>(offset, limit));
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
	@ApiOperation(value = "测试返回json格式", code = 200, produces = "application/json")
	public JSONObject testFormJson(@RequestParam ("filename" ) String filename,@RequestParam ("filename1" ) String filename1){
		JSONObject jsonObject =  new JSONObject();
		jsonObject.put("test", filename);
		jsonObject.put("test1", filename1);
		return jsonObject;
	}
	
	/**
	 * AR 部分测试
	 */
	@RequestMapping(value = "/test",method=RequestMethod.POST)
	@ResponseBody  
	@ApiOperation(value = "测试实体类调用增删改查")
	public Page<User> test() {
		User user = new User("testAr", 0, 1);
		System.err.println("删除所有：" + user.delete(null));
		user.insert();
		System.err.println("查询插入结果：" + user.selectById().toString());
		user.setName("mybatis-plus-ar");
		System.err.println("更新：" + user.updateById());
		return user.selectPage(new Page<User>(0, 12), null);
	}
}
