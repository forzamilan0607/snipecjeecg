package org.jeecg.modules.demo3.device.controller;


import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.QRCodeReturnDbpath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 二维码生成器，支持中文
 */
@RestController
@RequestMapping("/QRcode")
@Slf4j
public class QRcodeController {

    public static String hostUrl;

    public static String hostUrlApi;

    @Value("${jeecg.hostUrl:http://101.37.81.229}")
    public void setHostUrl(String hostUrl) {
        QRcodeController.hostUrl = hostUrl;
    }

    @Value("${jeecg.hostUrlApi:http://101.37.81.229}")
    public void setHostUrlApi(String hostUrlApi) {
        QRcodeController.hostUrlApi = hostUrlApi;
    }

/*static {
            try {
                urlPrefix = "http://" + InetAddress.getLocalHost().getHostAddress();
                hostUrl = "http://101.37.81.229";
                hostUrlApi = hostUrl;// urlPrefix + ":8088/jeecg-boot/";
                log.info("主机地址 = {} API 地址 = {}", hostUrl, hostUrlApi);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }*/

    @GetMapping(value = "/getImage", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ApiOperation("获取图片-返回BufferedImage")
    public void getImage(HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "text", required = false) String text) throws IOException {
        String filename = "myTestQrImg" + text + ".png";
        System.out.println(text);
        System.out.println(request.getParameter("text"));

        String texts = hostUrl + "/device?id=" + text;
        String imgPath = "D:\\";
        File file = QRCodeReturnDbpath.genQrCodeImg("utf-8", 300, 300, imgPath, filename, texts);
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

    public static void main(String[] args) {
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
