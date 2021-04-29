package com.smart.agriculture.manage.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smart.agriculture.manage.entity.MachineControllerEntity;
import com.smart.agriculture.manage.service.MachineControllerService;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.R;



/**
 * 
 *
 * @author Evan
 * @email 
 * @date 2021-04-29 14:22:58
 */
@RestController
@RequestMapping("manage/machinecontroller")
public class MachineControllerController {
    @Autowired
    private MachineControllerService machineControllerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
  //  @RequiresPermissions("manage:machinecontroller:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = machineControllerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
  //  @RequiresPermissions("manage:machinecontroller:info")
    public R info(@PathVariable("id") Integer id){
		MachineControllerEntity machineController = machineControllerService.getById(id);

        return R.ok().put("machineController", machineController);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
 //   @RequiresPermissions("manage:machinecontroller:save")
    public R save(@RequestBody MachineControllerEntity machineController){
		machineControllerService.save(machineController);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
  //  @RequiresPermissions("manage:machinecontroller:update")
    public R update(@RequestBody MachineControllerEntity machineController){
		machineControllerService.updateById(machineController);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
  //  @RequiresPermissions("manage:machinecontroller:delete")
    public R delete(@RequestBody Integer[] ids){
		machineControllerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
