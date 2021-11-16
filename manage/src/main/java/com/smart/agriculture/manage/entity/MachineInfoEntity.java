package com.smart.agriculture.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Evan
 * @email
 * @date 2021-04-29 14:22:58
 */
@Data
@TableName("machine_info")
public class MachineInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 设备通道
     */
    private String channel;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;
    /**
     * 设备名称
     */
    private String machineName;
    /**
     * 设备类型
     */
    private String machineType;
    /**
     * 设备启用状态（默认为不启用）
     */
    private String machineStatus;
    /**
     * 设备编号
     */
    private String machineId;

    /**
     * 单位
     */
    private String unit;

    private long age;

}
