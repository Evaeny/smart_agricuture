package com.smart.agriculture.manage.controller;

import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.R;
import com.smart.agriculture.manage.entity.PolicyManagementEntity;
import com.smart.agriculture.manage.service.PolicyManagementService;
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
@RequestMapping("manage/policymanagement")
public class PolicyManagementController {
    @Autowired
    private PolicyManagementService policyManagementService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //  @RequiresPermissions("manage:policymanagement:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = policyManagementService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //  @RequiresPermissions("manage:policymanagement:info")
    public R info(@PathVariable("id") Long id) {
        PolicyManagementEntity policyManagement = policyManagementService.getById(id);

        return R.ok().put("policyManagement", policyManagement);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //   @RequiresPermissions("manage:policymanagement:save")
    public R save(@RequestBody PolicyManagementEntity policyManagement) {
        policyManagementService.save(policyManagement);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //  @RequiresPermissions("manage:policymanagement:update")
    public R update(@RequestBody PolicyManagementEntity policyManagement) {
        policyManagementService.updateById(policyManagement);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //  @RequiresPermissions("manage:policymanagement:delete")
    public R delete(@RequestBody Long[] ids) {
        policyManagementService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update/status")
    //  @RequiresPermissions("manage:machinecontroller:update")
    public R updateStatus(@RequestBody PolicyManagementEntity policyManagement) {
        policyManagementService.updateById(policyManagement);
        return R.ok();
    }

}
