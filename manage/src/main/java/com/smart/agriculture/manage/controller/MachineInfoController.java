package com.smart.agriculture.manage.controller;

import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.R;
import com.smart.agriculture.manage.entity.MachineInfoEntity;
import com.smart.agriculture.manage.service.MachineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * @author Evan
 * @email
 * @date 2021-04-29 14:22:58
 */
@RestController
@RequestMapping("manage/machineinfo")
public class MachineInfoController {
    @Autowired
    private MachineInfoService machineInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //  @RequiresPermissions("manage:machineinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = machineInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //  @RequiresPermissions("manage:machineinfo:info")
    public R info(@PathVariable("id") Long id) {
        MachineInfoEntity machineInfo = machineInfoService.getById(id);
        return R.ok().put("machineInfo", machineInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MachineInfoEntity machineInfoEntity) throws Exception {
        Boolean iscuss = machineInfoService.saveMachineInfo(machineInfoEntity);
        return R.ok().put("成功",iscuss);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //  @RequiresPermissions("manage:machineinfo:update")
    public R update(@RequestBody MachineInfoEntity machineInfo) {
        machineInfoService.updateName(machineInfo);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //  @RequiresPermissions("manage:machineinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        machineInfoService.deleteMachineInfo(ids);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update/status")
    //  @RequiresPermissions("manage:machinecontroller:update")
    public R updateStatus(@RequestBody MachineInfoEntity machineInfoEntity) {
        machineInfoService.updateStatus(machineInfoEntity);
        return R.ok();
    }
}
