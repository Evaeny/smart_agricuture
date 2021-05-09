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
@TableName("message_info")
public class MessageInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id",type = IdType.INPUT)
	private Long id;
	/**
	 * 消息类型
	 */
	private String messagetype;
	/**
	 * 设备id
	 */
	private String machineid;
	/**
	 * 设备名称
	 */
	private String machinename;
	/**
	 * 接收时间
	 */
	private Date creattime;
	/**
	 * 存在标识符
	 */
	private String deletyn;

}
