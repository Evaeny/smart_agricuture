package com.smart.agriculture.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.manage.entity.MessageInfoEntity;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author Evan
 * @email
 * @date 2021-04-29 14:22:58
 */
public interface MessageInfoService extends IService<MessageInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    /**
     * 列表查询
     */
    PageUtils selectListInfo(Map<String, Object> params);

    /**
     * 删除信息
     */
    Boolean deleteMessageInfo (Long[] ids);
}

