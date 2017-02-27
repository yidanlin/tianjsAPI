package com.tianan;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@SpringBootApplication
@MapperScan("com.tianan.mapper")
@ComponentScan
@Configuration
public class App {
	private static Logger logger = Logger.getLogger(App.class);

	@Bean
	public DefaultKaptcha captchaProducer() {
		Properties properties = new Properties();
		properties.put("kaptcha.border", "yes");
		properties.put("kaptcha.border.color", "lightGray");
		properties.put("kaptcha.textproducer.font.color", "blue");
		properties.put("kaptcha.image.width", "160");
		properties.put("kaptcha.image.height", "50");
		properties.put("kaptcha.textproducer.font.size", "40");
		properties.put("kaptcha.session.key", "kaptcha");
		properties.put("kaptcha.textproducer.char.length", "4");
		properties.put("kaptcha.background.clear.to", "gray");
		properties.put("kaptcha.noise.color", "black");// 增加干扰线
		// properties.put("kaptcha.noise.impl",
		// "com.google.code.kaptcha.impl.NoNoise");//去掉干扰线
		properties.put("kaptcha.textproducer.char.space", "6");
		properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");

		Config config = new Config(properties);
		DefaultKaptcha kaptcha = new DefaultKaptcha();
		kaptcha.setConfig(config);

		return kaptcha;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		logger.info("SpringBoot Start Success");
	}
}
