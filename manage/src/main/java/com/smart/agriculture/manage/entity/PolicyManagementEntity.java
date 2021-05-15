package com.smart.agriculture.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Evan
 * @email 
 * @date 2021-04-29 14:22:58
 */
@Data
@TableName("policy_management")
public class PolicyManagementEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 启用状态（默认为不启用）
	 */
	private String enableStatus;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 策略设定最小值
	 */
	private String numberMin;
	/**
	 * 策略设定最大值
	 */
	private String numberMax;
	/**
	 * 设备通道
	 */
	private String channel;
	/**
	 * 设备类型
	 */
	private String machineType;
	/**
	 * 设备名称
	 */
	private String machineName;
	/**
	 * 设备编号
	 */
	private String machineId;
	/**
	 * 主键id
	 */
	@TableId(value = "id",type = IdType.INPUT)
	private Long id;

}
