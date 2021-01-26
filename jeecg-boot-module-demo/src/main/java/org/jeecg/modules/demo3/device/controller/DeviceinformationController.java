package org.jeecg.modules.demo3.device.controller;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.QRCodeReturnDbpath;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo3.device.entity.Deviceinformation;
import org.jeecg.modules.demo3.device.service.IDeviceinformationService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date: 2021-01-24
 * @Version: V1.0
 */
@Api(tags = "设备信息")
@RestController
@RequestMapping("/device/deviceinformation")
@Slf4j
public class DeviceinformationController extends JeecgController<Deviceinformation, IDeviceinformationService> {
    @Autowired
    private IDeviceinformationService deviceinformationService;

    /**
     * 分页列表查询
     *
     * @param deviceinformation
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "设备信息-分页列表查询")
    @ApiOperation(value = "设备信息-分页列表查询", notes = "设备信息-分页列表查询")
    @GetMapping(value = "/list")

    public Result<?> queryPageList(Deviceinformation deviceinformation,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<Deviceinformation> queryWrapper = QueryGenerator.initQueryWrapper(deviceinformation, req.getParameterMap());
        Page<Deviceinformation> page = new Page<Deviceinformation>(pageNo, pageSize);
        IPage<Deviceinformation> pageList = deviceinformationService.page(page, queryWrapper);

        for (Deviceinformation dev : pageList.getRecords()) {
            String filename = dev.getId() + ".png";
            String text = QRcodeController.hosturl + "/device?id=" + dev.getId();
            dev.setQrcodeStringUrl(QRcodeController.hosturlapi + "/jeecg-boot/QRcode/getImage?text=" + dev.getId() + "&t=" + System.currentTimeMillis());
            String imgPath = "D:\\";
            File file = QRCodeReturnDbpath.genQrCodeImg("utf-8", 300, 300, imgPath, filename, text);
            FileInputStream fis;
            try {
                fis = new FileInputStream(file);
                long size = file.length();
                byte[] temp = new byte[(int) size];
                fis.read(temp, 0, (int) size);
                fis.close();
                dev.setQrcode(temp);
                deviceinformationService.updateById(dev);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param deviceinformation
     * @return
     */
    @AutoLog(value = "设备信息-添加")
    @ApiOperation(value = "设备信息-添加", notes = "设备信息-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody Deviceinformation deviceinformation) {
        deviceinformationService.save(deviceinformation);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param deviceinformation
     * @return
     */
    @AutoLog(value = "设备信息-编辑")
    @ApiOperation(value = "设备信息-编辑", notes = "设备信息-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody Deviceinformation deviceinformation) {
        deviceinformationService.updateById(deviceinformation);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设备信息-通过id删除")
    @ApiOperation(value = "设备信息-通过id删除", notes = "设备信息-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        deviceinformationService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "设备信息-批量删除")
    @ApiOperation(value = "设备信息-批量删除", notes = "设备信息-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.deviceinformationService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设备信息-通过id查询")
    @ApiOperation(value = "设备信息-通过id查询", notes = "设备信息-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        Deviceinformation deviceinformation = deviceinformationService.getById(id);
        if (deviceinformation == null) {
            return Result.error("未找到对应数据");
        }

        String filename = deviceinformation.getId() + ".png";
        String text = QRcodeController.hosturl + "/device?id=" + deviceinformation.getId();

        deviceinformation.setQrcodeStringUrl(QRcodeController.hosturlapi + "/jeecg-boot/QRcode/getImage?text=" + deviceinformation.getId() + "&t=" + System.currentTimeMillis());
        String imgPath = "D:\\";
        File file = QRCodeReturnDbpath.genQrCodeImg("utf-8", 300, 300, imgPath, filename, text);

        FileInputStream fis;
        try {
            fis = new FileInputStream(file);


            long size = file.length();
            byte[] temp = new byte[(int) size];
            fis.read(temp, 0, (int) size);
            fis.close();
            deviceinformation.setQrcode(temp);
            deviceinformationService.updateById(deviceinformation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.OK(deviceinformation);
    }


    /**
     * 导出excel
     *
     * @param request
     * @param deviceinformation
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Deviceinformation deviceinformation) {
        return super.exportXls(request, deviceinformation, Deviceinformation.class, "设备信息");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Deviceinformation.class);
    }

}
