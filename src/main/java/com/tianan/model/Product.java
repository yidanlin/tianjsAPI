package com.tianan.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;

public class Product extends Model<Product> {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 9218699627823815614L;

	private Integer id; 

	 private String pro_name; 

	 private String pro_code; 


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getPro_code() {
		return pro_code;
	}

	public void setPro_code(String pro_code) {
		this.pro_code = pro_code;
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return id;
	}

	
}
