package org.jeecg.common.util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @Description: 二维码生成
 * @Author: SuJiaChen
 * @Date: 2019/11/21
 * @Version: V1.0
 */
public class QRCodeReturnDbpath {


    /*
         *焊机二维码生成返回存储数据库地址
         * @author ZhangYin
         * @date 2019/11/21 19:19
         * @return
        */
    public static String welderQRCodeUtil(String uploadpath,String text ) throws Exception {
        //调用生成二维码的方法生成二维码，
        String ctxPath = uploadpath;
        String bizPath = "files";
        String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String destpath = ctxPath + File.separator + bizPath + File.separator + nowday + "二维码";
        File file = new File(destpath);
        if (!file.exists()) {
            file.mkdirs();// 创建文件根目录
        }
        String userDir=System.getProperty("user.dir");
        String imgPath=userDir+"Logo.png"; //logo图片
        String fileName = QRCodeUtilencode(text,imgPath,destpath, true);
        String dbpath = bizPath + File.separator + nowday + "二维码" + File.separator + fileName;
        //路径转换
        //dbpath = FilePathUtil.getRealFilePath(dbpath);
        return dbpath;
    }

    private static String QRCodeUtilencode(String text, String imgPath, String destpath, boolean b) {
        String filename="myTestQrImg"+System.currentTimeMillis()+".png";
        genQrCodeImg("utf-8",300,300,imgPath,filename,text);
   return  filename;
    }


    /**
     * 根据参数生成二维码图片。
     *
     * @param imgCharactCode
     *            字符编码, 默认为:UTF-8.
     * @param imgWidth
     *            图片宽度, 默认为: 300px
     * @param imgHeight
     *            图片高度, 默认为: 300px
     * @param strImgFileFoler
     *      图片存储目录
     * @param imgFileName
     *            图片名称(如：myTestQrImg.png)
     * @param qrContent
     *            二维码内容
     * @return 二维码图片的文件对象
     */
    public static File genQrCodeImg(String imgCharactCode, int imgWidth, int imgHeight, String strImgFileFoler, String imgFileName, String qrContent) {
        File imgFullFile = null;

        if (strImgFileFoler == null || "".equals(strImgFileFoler) || imgFileName == null || "".equals(imgFileName)
                || qrContent == null || "".equals(qrContent)) {
            return imgFullFile;
        }

        BitMatrix bitMatrix = null;
        try {
            // 定义二维码参数的哈希映射表
            HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            // 编码方式，支持中文
            imgCharactCode = (imgCharactCode == null || "".equals(imgCharactCode) ? "UTF-8" : imgCharactCode);
            hints.put(EncodeHintType.CHARACTER_SET, imgCharactCode);
            // 容错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            // 二维码边距
            hints.put(EncodeHintType.MARGIN, 1);

            // 生成点阵
            imgWidth = (imgWidth <= 0 ? 300 : imgWidth);  // 默认为300px
            imgHeight = (imgHeight <= 0 ? 300 : imgHeight);  // 默认为300px

            bitMatrix = new MultiFormatWriter().encode(qrContent, BarcodeFormat.QR_CODE, imgWidth, imgHeight, hints);

            // 创建目录
            File fileImgFoler = new File(strImgFileFoler);
            if (!fileImgFoler.exists()) {
                fileImgFoler.mkdir();
            }

            // 图片的文件对象
            String strImgFullName = fileImgFoler.getPath() + "/" + imgFileName;
            imgFullFile = new File(strImgFullName);

            // 图片扩展名(即：图片格式)
            Path filePath = imgFullFile.toPath();
            String imgFormat = imgFileName.substring(imgFileName.lastIndexOf(".") + 1);

            // 输出文件
            writeToFile(bitMatrix, imgFormat, imgFullFile);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            imgFullFile = null;
        }

        return imgFullFile;
    }

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;


    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void writeToFiles(BitMatrix matrix, String format, String file)
            throws IOException {
        writeToFile(matrix,format,new File(file));

    }
        public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format "
                    + format + " to " + file);
        }
    }
    public static void writeToStream(BitMatrix matrix, String format,
                                     OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }



    public static byte[] File2byte(File file){
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }

    /*
     *用户二维码生成返回存储数据库地址
     * @author SuJiaChen
     * @date 2019/11/21 19:19
     * @return
     */
    public static String userQRCodeUtil(String uploadpath,String text ) throws Exception {
        //调用生成二维码的方法生成二维码，
        String ctxPath = uploadpath;
        String bizPath = "files";
        String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String destpath = ctxPath + File.separator + bizPath + File.separator + nowday + "二维码";
        File file = new File(destpath);
        if (!file.exists()) {
            file.mkdirs();// 创建文件根目录
        }
        String userDir=System.getProperty("user.dir");
        String imgPath=userDir+"Logo.png"; //logo图片
        String fileName = QRCodeUtilencode(text,imgPath,destpath, true);
        String dbpath = bizPath + File.separator + nowday + "二维码" + File.separator + fileName;
        //路径转换
      //  dbpath = FilePathUtil.getRealFilePath(dbpath);
        return dbpath;
    }
}
