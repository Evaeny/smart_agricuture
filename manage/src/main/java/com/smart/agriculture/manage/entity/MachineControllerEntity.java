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
@TableName("machine_controller")
public class MachineControllerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */

    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;
    /**
     * 设备名称
     */
    private String machineName;
    /**
     * 设定数值
     */
    private Double presetNumber;
    /**
     * 单位
     */
    private String unit;
    /**
     * 设备通道（a、b、c......)
     */
    private String channel;
    /**
     * 设定启用状态（默认为不启用）
     */
    private String presetStatus;
    /**
     * 设备类型
     */
    private String machineType;
    /**
     * 设备编号
     */
    private String machineId;

}
