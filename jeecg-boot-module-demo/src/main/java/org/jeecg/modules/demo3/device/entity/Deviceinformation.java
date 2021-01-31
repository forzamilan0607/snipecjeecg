package org.jeecg.modules.demo3.device.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date:   2021-01-31
 * @Version: V1.0
 */
@Data
@TableName("deviceinformation")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="deviceinformation对象", description="设备信息")
public class Deviceinformation implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
    private java.lang.String name;
	/**规格型号*/
	@Excel(name = "规格型号", width = 15)
    @ApiModelProperty(value = "规格型号")
    private java.lang.String model;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String states;
	/**设备编号*/
	@Excel(name = "设备编号", width = 15)
    @ApiModelProperty(value = "设备编号")
    private java.lang.String code;
	/**生产厂家*/
	@Excel(name = "生产厂家", width = 15)
    @ApiModelProperty(value = "生产厂家")
    private java.lang.String manufacturer;
	/**二维码*/
	@Excel(name = "二维码", width = 15)
    private transient java.lang.String qrcodeString;

    private byte[] qrcode;

    public byte[] getQrcode(){
        if(qrcodeString==null){
            return null;
        }
        try {
            return qrcodeString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getQrcodeString(){
        if(qrcode==null || qrcode.length==0){
            return "";
        }
        try {
            return new String(qrcode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
	/**二维码Url*/
	@Excel(name = "二维码Url", width = 15)
    @ApiModelProperty(value = "二维码Url")
    private java.lang.String qrcodeStringUrl;
	/**出产编号*/
	@Excel(name = "出产编号", width = 15)
    @ApiModelProperty(value = "出产编号")
    private java.lang.String manufacturercode;
	/**出产日期*/
	@Excel(name = "出产日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "出产日期")
    private java.util.Date manufacturerdate;
	/**投产日期*/
	@Excel(name = "投产日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "投产日期")
    private java.util.Date commissiondate;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private java.lang.Integer quantity;
	/**单价*/
	@Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private java.lang.Double unitprice;
	/**仪器负责人*/
	@Excel(name = "仪器负责人", width = 15)
    @ApiModelProperty(value = "仪器负责人")
    private java.lang.String maintainer;
	/**仪器使用部门*/
	@Excel(name = "仪器使用部门", width = 15)
    @ApiModelProperty(value = "仪器使用部门")
    private java.lang.String instrunentdept;
	/**放置地点*/
	@Excel(name = "放置地点", width = 15)
    @ApiModelProperty(value = "放置地点")
    private java.lang.String placementlocation;
	/**说明书*/
	@Excel(name = "说明书", width = 15)
    private transient java.lang.String descriptionString;

    private byte[] description;

    public byte[] getDescription(){
        if(descriptionString==null){
            return null;
        }
        try {
            return descriptionString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDescriptionString(){
        if(description==null || description.length==0){
            return "";
        }
        try {
            return new String(description,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
	/**校验类型（自校）*/
	@Excel(name = "校验类型（自校）", width = 15)
    @ApiModelProperty(value = "校验类型（自校）")
    private java.lang.String selfcalibration;
	/**自校.校验周期*/
	@Excel(name = "自校.校验周期", width = 15)
    @ApiModelProperty(value = "自校.校验周期")
    private java.lang.String selfcalibrationcycle;
	/**自校.校验日期*/
	@Excel(name = "自校.校验日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "自校.校验日期")
    private java.util.Date instrumenttestdate;
	/**校验证书（自校）*/
	@Excel(name = "校验证书（自校）", width = 15)
    @ApiModelProperty(value = "校验证书（自校）")
    private java.lang.String selfcalibrationimgs;
	/**校验类型（外校）*/
	@Excel(name = "校验类型（外校）", width = 15)
    @ApiModelProperty(value = "校验类型（外校）")
    private java.lang.String othercalibration;
	/**校验周期（外校）*/
	@Excel(name = "校验周期（外校）", width = 15)
    @ApiModelProperty(value = "校验周期（外校）")
    private java.lang.String othercalibrationcycle;
	/**外校.校验日期*/
	@Excel(name = "外校.校验日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "外校.校验日期")
    private java.util.Date nexttestdate;
	/**校验证书（外校）*/
	@Excel(name = "校验证书（外校）", width = 15)
    @ApiModelProperty(value = "校验证书（外校）")
    private java.lang.String othercalibrationimgs;
	/**维护保养周期*/
	@Excel(name = "维护保养周期", width = 15)
    @ApiModelProperty(value = "维护保养周期")
    private java.lang.String maintenancecycle;
	/**维护保养日期*/
	@Excel(name = "维护保养日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "维护保养日期")
    private java.util.Date maintenancedate;
	/**维护保养记录（照片/视频）*/
	@Excel(name = "维护保养记录（照片/视频）", width = 15)
    @ApiModelProperty(value = "维护保养记录（照片/视频）")
    private java.lang.String maintenanceimg;

    private java.lang.String maintenanceimgh5;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remarks;
	/**仪器，设备*/
	@Excel(name = "仪器，设备", width = 15)
    @ApiModelProperty(value = "仪器，设备")
    private java.lang.String instrumentandequipment;
	/**性质类别*/
	@Excel(name = "性质类别", width = 15)
    @ApiModelProperty(value = "性质类别")
    private java.lang.String naturecategory;
	/**重要性类别*/
	@Excel(name = "重要性类别", width = 15)
    @ApiModelProperty(value = "重要性类别")
    private java.lang.String importancecategory;
}
