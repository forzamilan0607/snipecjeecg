package org.jeecg.modules.demo3.device.controller;


import io.swagger.annotations.ApiOperation;
import org.jeecg.common.util.QRCodeReturnDbpath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 二维码生成器，支持中文
 *
 */
@RestController
@RequestMapping("/QRcode")
public class QRcodeController {

    public  static  String hosturl="http://172.16.19.37:3000";

    public  static  String hosturlapi="http://172.16.19.37:8088/jeecg-boot/";

    @GetMapping(value = "/getImage", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ApiOperation("获取图片-返回BufferedImage")
    public void getImage(HttpServletResponse response,HttpServletRequest request, @RequestParam(name = "text",required = false) String text) throws IOException {
        String filename="myTestQrImg"+text+".png";
        System.out.println(text);
        System.out.println(request.getParameter("text"));

        String texts=hosturl+"/device?id="+text;
        String imgPath="D:\\";
        File file= QRCodeReturnDbpath.genQrCodeImg("utf-8",300,300,imgPath,filename,texts);
         FileInputStream fis;
        fis = new FileInputStream(file);

        long size = file.length();
        byte[] temp = new byte[(int) size];
        fis.read(temp, 0, (int) size);
        fis.close();
        byte[] data = temp;
        OutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        out.write(data);

        out.flush();
        out.close();
    }

}
