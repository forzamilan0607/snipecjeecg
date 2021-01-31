package org.jeecg.modules.demo3.device.service.impl;

import org.jeecg.modules.demo3.device.entity.Deviceinformation;
import org.jeecg.modules.demo3.device.mapper.DeviceinformationMapper;
import org.jeecg.modules.demo3.device.service.IDeviceinformationService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date:   2021-01-31
 * @Version: V1.0
 */
@Service
public class DeviceinformationServiceImpl extends ServiceImpl<DeviceinformationMapper, Deviceinformation> implements IDeviceinformationService {

}
