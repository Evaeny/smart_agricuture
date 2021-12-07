package com.smart.agriculture.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;
import com.smart.agriculture.manage.dao.*;
import com.smart.agriculture.manage.entity.*;
import com.smart.agriculture.manage.service.MachineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("machineInfoService")
public class MachineInfoServiceImpl extends ServiceImpl<MachineInfoDao, MachineInfoEntity> implements MachineInfoService {

    @Autowired
    private MachineControllerDao machineControllerDao;

    @Autowired
    private PolicyManagementDao policyManagementDao;

    @Autowired
    private MachineSensorDao machineSensorDao;

    @Autowired
    private MessageInfoDao messageInfoDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String machineSensor = (String) params.get("machineSensor");
        QueryWrapper<MachineInfoEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(machineSensor) && "0".equals(machineSensor)) {
            queryWrapper.in("machine_type", "a", "b", "c", "d", "e", "f");
        }
        else {
            queryWrapper.eq(!StringUtils.isEmpty(params.get("machineType")), "machine_type", params.get("machineType"));
        }

        queryWrapper.eq(!StringUtils.isEmpty(params.get("channel")), "channel", params.get("channel"));

        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineId")), "machine_id", params.get("machineId"));


        queryWrapper.like(!StringUtils.isEmpty(params.get("machineName")), "machine_name", params.get("machineName"));
        queryWrapper.ge(!StringUtils.isEmpty(params.get("startTime")), "create_time", params.get("startTime"));
        queryWrapper.le(!StringUtils.isEmpty(params.get("endTime")), "create_time", params.get("endTime"));
        queryWrapper.orderByDesc("create_time");
        IPage<MachineInfoEntity> page = this.page(
                new Query<MachineInfoEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<MachineInfoEntity> queryAll(Map<String, Object> params) {
        QueryWrapper<MachineInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("machine_type", "a", "b", "c", "d", "e", "f");
        queryWrapper.in("machine_status", true);
        return this.baseMapper.selectList(queryWrapper);
    }


    @Override
    public Boolean saveMachineInfo(MachineInfoEntity machineInfoEntity) throws Exception {
        QueryWrapper<MachineInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("machine_id", machineInfoEntity.getMachineId());
        queryWrapper.last("limit 1");
        MachineInfoEntity machineInfoEntity1 = this.baseMapper.selectOne(queryWrapper);
        if (!StringUtils.isEmpty(machineInfoEntity1)) {
            throw new Exception("该设备信息已存在");
        }
        if (("a").equals(machineInfoEntity.getMachineType()) || ("g").equals(machineInfoEntity.getMachineType())) {
            machineInfoEntity.setUnit("℃");
        } else if (("b").equals(machineInfoEntity.getMachineType()) || ("f").equals(machineInfoEntity.getMachineType()) || ("c").equals(machineInfoEntity.getMachineType()) ||
                ("h").equals(machineInfoEntity.getMachineType()) || ("i").equals(machineInfoEntity.getMachineType()) || ("l").equals(machineInfoEntity.getMachineType())) {
            machineInfoEntity.setUnit("%");
        } else if (("d").equals(machineInfoEntity.getMachineType()) || ("j").equals(machineInfoEntity.getMachineType())) {
            machineInfoEntity.setUnit("m/s");
        } else if (("e").equals(machineInfoEntity.getMachineType()) || ("k").equals(machineInfoEntity.getMachineType())) {
            machineInfoEntity.setUnit("dev");
        }
        int insert = this.baseMapper.insert(machineInfoEntity);
        Boolean iscuss = insert > 0;
        if (iscuss) {
            MessageInfoEntity messageInfoEntity = new MessageInfoEntity();
            messageInfoEntity.setDeleteYn("1");
            messageInfoEntity.setMachineId(machineInfoEntity.getMachineId());
            messageInfoEntity.setMachineName(machineInfoEntity.getMachineName());
            messageInfoEntity.setMessageType("设备添加");
            messageInfoEntity.setCreatTime(new Date());
            messageInfoDao.insert(messageInfoEntity);
            if (("g").equals(machineInfoEntity.getMachineType()) || ("h").equals(machineInfoEntity.getMachineType()) ||
                    ("i").equals(machineInfoEntity.getMachineType()) || ("j").equals(machineInfoEntity.getMachineType()) ||
                    ("k").equals(machineInfoEntity.getMachineType()) || ("l").equals(machineInfoEntity.getMachineType())) {
                MachineControllerEntity machineControllerEntity = new MachineControllerEntity();
                machineControllerEntity.setChannel(machineInfoEntity.getChannel());
                machineControllerEntity.setMachineId(machineInfoEntity.getMachineId());
                machineControllerEntity.setMachineName(machineInfoEntity.getMachineName());
                machineControllerEntity.setMachineType(machineInfoEntity.getMachineType());
                machineControllerEntity.setPresetStatus(machineInfoEntity.getMachineStatus());
                machineControllerEntity.setUnit(machineInfoEntity.getUnit());
                machineControllerDao.insert(machineControllerEntity);
            }
        }
        return iscuss;
    }

    @Override
    public void deleteMachineInfo(Long[] ids) {
        LambdaQueryWrapper<MachineInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MachineInfoEntity::getId, ids);
        List<MachineInfoEntity> entityList = this.baseMapper.selectList(queryWrapper);
        for (MachineInfoEntity machineInfoEntity : entityList) {
            MessageInfoEntity messageInfoEntity = new MessageInfoEntity();
            messageInfoEntity.setDeleteYn("1");
            messageInfoEntity.setMachineId(machineInfoEntity.getMachineId());
            messageInfoEntity.setMachineName(machineInfoEntity.getMachineName());
            messageInfoEntity.setMessageType("设备删除");
            messageInfoEntity.setCreatTime(new Date());
            messageInfoDao.insert(messageInfoEntity);
        }
        List<String> machineIdList = entityList.stream().map(MachineInfoEntity::getMachineId).collect(Collectors.toList());
        LambdaQueryWrapper<MachineControllerEntity> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.in(MachineControllerEntity::getMachineId, machineIdList);
        machineControllerDao.delete(deleteWrapper);
        LambdaQueryWrapper<PolicyManagementEntity> deleteWrapper2 = new LambdaQueryWrapper<>();
        deleteWrapper2.in(PolicyManagementEntity::getMachineId, machineIdList);
        policyManagementDao.delete(deleteWrapper2);
        LambdaQueryWrapper<PolicyManagementEntity> deleteWrapper3 = new LambdaQueryWrapper<>();
        deleteWrapper3.in(PolicyManagementEntity::getBetweenSensorId, machineIdList);
        policyManagementDao.delete(deleteWrapper3);
        this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public Boolean updateStatus(MachineInfoEntity machineInfoEntity) {
        LambdaQueryWrapper<MachineInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MachineInfoEntity::getId, machineInfoEntity.getId())
                    .last("limit 1");
        MachineInfoEntity machineInfoEntity1 = this.baseMapper.selectOne(queryWrapper);
        Boolean iscuss = this.baseMapper.updateById(machineInfoEntity) > 0;
//        if (("a").equals(machineInfoEntity1.getMachineType()) || ("b").equals(machineInfoEntity1.getMachineType()) || ("c").equals(machineInfoEntity1.getMachineType()) || ("d").equals(machineInfoEntity1.getMachineType()) || ("e").equals(machineInfoEntity1.getMachineType()) || ("f").equals(machineInfoEntity1.getMachineType())) {
//            LambdaQueryWrapper<MachineSensorEntity> queryWrapper1 = new LambdaQueryWrapper<>();
//            queryWrapper1.eq(MachineSensorEntity::getMachineId, machineInfoEntity1.getMachineId())
//                    .eq(MachineSensorEntity::getMachineType, machineInfoEntity1.getMachineType());
//            MachineSensorEntity machineSensorEntity = machineSensorDao.selectOne(queryWrapper1);
//            if (!StringUtils.isEmpty(machineSensorEntity)) {
//                machineSensorEntity.setEnableStatus(machineInfoEntity.getMachineStatus());
//                machineSensorDao.updateById(machineSensorEntity);
//            }
//        }else
        if (iscuss){
            if (("g").equals(machineInfoEntity1.getMachineType()) || ("h").equals(machineInfoEntity1.getMachineType()) || ("i").equals(machineInfoEntity1.getMachineType()) || ("j").equals(machineInfoEntity1.getMachineType()) || ("k").equals(machineInfoEntity1.getMachineType()) || ("l").equals(machineInfoEntity1.getMachineType())) {
            LambdaQueryWrapper<MachineControllerEntity> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(MachineControllerEntity::getMachineId, machineInfoEntity1.getMachineId())
                    .eq(MachineControllerEntity::getMachineType, machineInfoEntity1.getMachineType());
            MachineControllerEntity machineControllerEntity = machineControllerDao.selectOne(queryWrapper2);
            machineControllerEntity.setPresetStatus(machineInfoEntity.getMachineStatus());
            machineControllerDao.updateById(machineControllerEntity);
            }
        }
//        Boolean iscuss = this.baseMapper.updateById(machineInfoEntity) > 0;
        return iscuss;
    }

    @Override
    public Boolean updateName(MachineInfoEntity machineInfoEntity) {
        Boolean iscuss = this.baseMapper.updateById(machineInfoEntity) > 0;
        if (iscuss) {
            LambdaQueryWrapper<MachineControllerEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MachineControllerEntity::getMachineId, machineInfoEntity.getMachineId())
                    .eq(MachineControllerEntity::getMachineType, machineInfoEntity.getMachineType());
            MachineControllerEntity machineControllerEntity = machineControllerDao.selectOne(queryWrapper);
            if (!StringUtils.isEmpty(machineControllerEntity)) {
                machineControllerEntity.setMachineName(machineInfoEntity.getMachineName());
                machineControllerDao.updateById(machineControllerEntity);
            }
            LambdaQueryWrapper<PolicyManagementEntity> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(PolicyManagementEntity::getMachineId, machineInfoEntity.getMachineId())
                    .eq(PolicyManagementEntity::getMachineType, machineInfoEntity.getMachineType());
            PolicyManagementEntity policyManagementEntity = policyManagementDao.selectOne(queryWrapper2);
            if (!StringUtils.isEmpty(policyManagementEntity)) {
                policyManagementEntity.setMachineName(machineInfoEntity.getMachineName());
                policyManagementDao.updateById(policyManagementEntity);
            }
            MessageInfoEntity messageInfoEntity = new MessageInfoEntity();
            messageInfoEntity.setDeleteYn("1");
            messageInfoEntity.setMachineId(machineInfoEntity.getMachineId());
            messageInfoEntity.setMachineName(machineInfoEntity.getMachineName());
            messageInfoEntity.setMessageType("设备名称更改");
            messageInfoEntity.setCreatTime(new Date());
            messageInfoDao.insert(messageInfoEntity);
        }
        return iscuss;
    }
}
