package com.tianan.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianan.model.Product;
import com.tianan.model.User;
import com.tianan.service.IUserService;
import com.tianan.util.RedisUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/users")
@Api("用户信息查询")
public class UserController {
	@Autowired
	private IUserService userService;
	
	@Autowired
	private RedisUtil redisUtil;

	@RequestMapping(value = "/name/{name}/{role}", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation(value = "姓名模糊查询用户")
	public List<User> likeName(@PathVariable String name,@PathVariable String role) {
		System.out.println(new EntityWrapper<User>().where("name={0}", "'zhangsan'").and("id=1")//{0}是为了取得后面的值不加{0}后面拼接的zhangsan无效
	            .orNew("status={0}", "0").or("status=1").notLike("nlike", "notvalue") //带New的这一行属于一个整体条件见下面的sql
	            .andNew("new=xx").like("hhh", "ddd").like("left", "haa", SqlLike.LEFT)
	            .andNew("pwd=11").isNotNull("n1,n2").isNull("n3")
	            .groupBy("x1").groupBy("x2,x3")
	            .having("x1=11").having("x3=433")
	            .orderBy("dd").orderBy("d1,d2"));
/*WHERE (name='zhangsan' AND id=1) 
OR (status='0' OR status=1 AND nlike NOT LIKE '%notvalue%') 
AND (new=xx AND hhh LIKE '%ddd%' AND left LIKE '%haa') 
AND (pwd=11 AND n1 IS NOT NULL AND n2 IS NOT NULL AND n3 IS NULL)
GROUP BY x1, x2,x3
HAVING (x1=11 AND x3=433)
ORDER BY dd, d1,d2*/
		return userService.selectList(new EntityWrapper<User>().where("name={0}",name).and("role={0}",role));
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
	public Page<User> getUsers(@RequestParam(defaultValue = "1") int offset, @RequestParam(defaultValue = "10") int limit) {
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
		Product pro = new Product();
//		pro.setId(22);
		pro.setPro_name("AR_test");
		pro.setPro_code("AR_Test");
		pro.insert();
		pro.selectAll();
		pro.selectPage(new Page<Product>(1,10), new EntityWrapper<Product>().eq("pro_name", "test")).getRecords();
		return user.selectPage(new Page<User>(0, 12), null);
	}
	
	
	@RequestMapping(value="/testRedis",method=RequestMethod.POST)
	@Cacheable(value="test") //指明缓存将被存到什么地方。
	@ApiOperation(value = "测试Redis")
	public String getSessionId(@RequestParam ("key" ) String key){
	redisUtil.set("123", key);
	System.out.println("进入了方法");
	String string= redisUtil.get("123").toString();
	return string;
	}
}
