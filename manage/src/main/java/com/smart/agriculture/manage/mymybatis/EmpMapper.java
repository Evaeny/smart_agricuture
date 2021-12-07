package com.smart.agriculture.manage.mymybatis;

import com.smart.agriculture.manage.entity.MachineControllerEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmpMapper {

    @Select("select * from machine_controller where  #{machineName} and #{id} ")
    List<MachineControllerEntity> selectMachineController(String machineName, String id);
}
