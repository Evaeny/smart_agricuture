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

import com.smart.agriculture.manage.entity.MessageInfoEntity;
import com.smart.agriculture.manage.service.MessageInfoService;
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
@RequestMapping("manage/messageinfo")
public class MessageInfoController {
    @Autowired
    private MessageInfoService messageInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
  //  @RequiresPermissions("manage:messageinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = messageInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
  //  @RequiresPermissions("manage:messageinfo:info")
    public R info(@PathVariable("id") Long id){
		MessageInfoEntity messageInfo = messageInfoService.getById(id);

        return R.ok().put("messageInfo", messageInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
 //   @RequiresPermissions("manage:messageinfo:save")
    public R save(@RequestBody MessageInfoEntity messageInfo){
		messageInfoService.save(messageInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
  //  @RequiresPermissions("manage:messageinfo:update")
    public R update(@RequestBody MessageInfoEntity messageInfo){
		messageInfoService.updateById(messageInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
  //  @RequiresPermissions("manage:messageinfo:delete")
    public R delete(@RequestBody Long[] ids){
		messageInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
