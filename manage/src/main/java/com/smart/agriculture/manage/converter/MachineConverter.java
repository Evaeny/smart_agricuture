package com.smart.agriculture.manage.converter;


import com.smart.agriculture.manage.entity.MachineControllerEntity;
import com.smart.agriculture.manage.entity.MachineInfoEntity;
import com.smart.agriculture.manage.entity.MachineSensorEntity;
import com.smart.agriculture.manage.entity.PolicyManagementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * ClassName: MachineConverter
 * Description:
 * date: 2021/5/13 9:46
 *
 * @author 蒋一帆
 */

@Mapper(componentModel = "spring")
public interface MachineConverter {


    @Mappings(
            @Mapping(target = "id", ignore = true)
    )
    PolicyManagementEntity sensorToPolicy(MachineSensorEntity machineSensorEntity);

    @Mappings(
            @Mapping(target = "id", ignore = true)
    )
    MachineSensorEntity controllerToSensor(MachineControllerEntity machineControllerEntity);

    @Mappings({@Mapping(target = "id", ignore = true),
            @Mapping(target = "age", source = "age")}
    )
    MachineSensorEntity infoToSensor(MachineInfoEntity machineInfoEntity);

}
