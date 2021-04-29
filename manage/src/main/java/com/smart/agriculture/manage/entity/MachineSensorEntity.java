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
@TableName("machine_sensor")
public class MachineSensorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 条件数值
	 */
	private String conditionNumber;
	/**
	 * 记录时间
	 */
	private Date createtime;
	/**
	 * 来源设备编号
	 */
	private String machineid;
	/**
	 * 主键id
	 */
	@TableId
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
	private String machinename;
	/**
	 * 设备类型
	 */
	private String machineType;

}
