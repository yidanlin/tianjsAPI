package com.tianan.controller;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 生成图片数字
 * @author yidanlin
 *
 */
@RestController
@RequestMapping(value = "/verification")
@Api("验证码生成")
public class KaptchaController {

    @Resource
    private Producer captchaProducer;

    @RequestMapping(value = "/kaptcha", method = { RequestMethod.GET})
    @ApiOperation(value = "验证码")
    public void getKaptchaImage(@RequestParam ("timestamp" ) String timestamp,HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");	
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(captchaProducer.createImage(capText), "jpg", out);
            out.flush();
        }

    }
}