package com.smart.agriculture.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;
import com.smart.agriculture.manage.dao.MachineControllerDao;
import com.smart.agriculture.manage.dao.MachineInfoDao;
import com.smart.agriculture.manage.dao.PolicyManagementDao;
import com.smart.agriculture.manage.entity.MachineControllerEntity;
import com.smart.agriculture.manage.entity.MachineInfoEntity;
import com.smart.agriculture.manage.entity.PolicyManagementEntity;
import com.smart.agriculture.manage.entity.MachineInfoEntity;
import com.smart.agriculture.manage.service.MachineControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import com.smart.agriculture.manage.service.MachineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("machineControllerService")
public class MachineControllerServiceImpl extends ServiceImpl<MachineControllerDao, MachineControllerEntity> implements MachineControllerService {
    @Autowired
    private MachineInfoService machineInfoService;

    @Autowired
    private MachineInfoDao machineInfoDao;

    @Autowired
    private PolicyManagementDao policyManagementDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MachineControllerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineType")), "machine_type", params.get("machineType"));
        queryWrapper.eq(!StringUtils.isEmpty(params.get("channel")), "channel", params.get("channel"));
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineId")), "machine_id", params.get("machineId"));
        queryWrapper.like(!StringUtils.isEmpty(params.get("machineName")), "machine_name", params.get("machineName"));
        IPage<MachineControllerEntity> page = this.page(
                new Query<MachineControllerEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public Boolean updateStatus(MachineControllerEntity machineControllerEntity) {
        LambdaQueryWrapper<MachineControllerEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MachineControllerEntity::getId, machineControllerEntity.getId());
        MachineControllerEntity machineControllerEntity1 = this.baseMapper.selectOne(queryWrapper);
        Boolean iscuss = this.baseMapper.updateById(machineControllerEntity) > 0;
        if (iscuss) {
            LambdaQueryWrapper<MachineInfoEntity> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(MachineInfoEntity::getMachineId, machineControllerEntity1.getMachineId())
                    .eq(MachineInfoEntity::getMachineType, machineControllerEntity1.getMachineType());
            MachineInfoEntity machineInfoEntity = machineInfoDao.selectOne(queryWrapper2);
            machineInfoEntity.setMachineStatus(machineControllerEntity.getPresetStatus());
            machineInfoDao.updateById(machineInfoEntity);
        }
        return iscuss;
    }

    @Override
    public void deleteMachineController(Long[] ids) {
        LambdaQueryWrapper<MachineControllerEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MachineControllerEntity::getId, ids);
        List<MachineControllerEntity> machineControllerEntityList = this.baseMapper.selectList(queryWrapper);
        List<String> machineIdList = machineControllerEntityList.stream().map(MachineControllerEntity::getMachineId).collect(Collectors.toList());
        LambdaQueryWrapper<MachineInfoEntity> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.in(MachineInfoEntity::getMachineId, machineIdList);
        machineInfoDao.delete(deleteWrapper);
        LambdaQueryWrapper<PolicyManagementEntity> deleteWrapper2 = new LambdaQueryWrapper<>();
        deleteWrapper2.in(PolicyManagementEntity::getMachineId, machineIdList);
        policyManagementDao.delete(deleteWrapper2);
        this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public List<MachineInfoEntity> queryAll(Map<String, Object> params) {
        QueryWrapper<MachineInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("machine_type", "g", "h", "i", "j", "k", "l");
        queryWrapper.in("machine_status", true);
        return machineInfoService.getBaseMapper().selectList(queryWrapper);
    }

}
