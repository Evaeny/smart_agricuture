package com.smart.agriculture.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Evan
 * @email
 * @date 2021-04-29 14:22:58
 */
@Data
@TableName("machine_sensor")
public class MachineSensorEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 条件数值
     */
    private Double conditionNumber;
    /**
     * 记录时间
     */
    private Date createTime;
    /**
     * 来源设备编号
     */
    private String machineId;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;
    /**
     * 单位
     */
    private String unit;
    /**
     * 来源通道
     */
    private String channel;
    /**
     * 备注
     */
    private String remark;
    /**
     * 来源设备名称
     */
    private String machineName;
    /**
     * 设备类型
     */
    private String machineType;

    /**
     * 启用状态
     */
    private String enableStatus;

}
