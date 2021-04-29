package com.smart.agriculture.manage.entity;

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
	private String enablestatus;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 策略设定最小值
	 */
	private String numbermin;
	/**
	 * 策略设定最大值
	 */
	private String numbermax;
	/**
	 * 设备通道
	 */
	private String channel;
	/**
	 * 设备类型
	 */
	private String machinetype;
	/**
	 * 设备名称
	 */
	private String machinename;
	/**
	 * 设备编号
	 */
	private String machineid;
	/**
	 * 主键id
	 */
	@TableId
	private Long id;

}
