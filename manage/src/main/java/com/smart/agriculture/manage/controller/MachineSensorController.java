package com.smart.agriculture.manage.controller;

import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.R;
import com.smart.agriculture.manage.entity.MachineSensorEntity;
import com.smart.agriculture.manage.service.MachineSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * @author Evan
 * @email
 * @date 2021-04-29 14:22:58
 */
@RestController
@RequestMapping("manage/machinesensor")
public class MachineSensorController {
    @Autowired
    private MachineSensorService machineSensorService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //  @RequiresPermissions("manage:machinesensor:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = machineSensorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //  @RequiresPermissions("manage:machinesensor:info")
    public R info(@PathVariable("id") Long id) {

        MachineSensorEntity machineSensor = machineSensorService.getById(id);

        return R.ok().put("machineSensor", machineSensor);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //   @RequiresPermissions("manage:machinesensor:save")
    public R save(@RequestBody MachineSensorEntity machineSensor) {
        machineSensorService.saveMachineSensor(machineSensor);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //  @RequiresPermissions("manage:machinesensor:update")
    public R update(@RequestBody MachineSensorEntity machineSensor) {
        machineSensorService.updateById(machineSensor);

        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/update/status")
    //  @RequiresPermissions("manage:machinesensor:update")
    public R updateStatus(@RequestBody MachineSensorEntity machineSensor) {
        machineSensorService.updateById(machineSensor);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //  @RequiresPermissions("manage:machinesensor:delete")
    public R delete(@RequestBody Long[] ids) {
        machineSensorService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/query/{id}")
    public R query(@PathVariable("id") String id) throws Exception {
        MachineSensorEntity sensorEntity = machineSensorService.saveMachineSensor(id);
        return R.ok().put("machineSensor", sensorEntity);
    }


}
