package com.smart.agriculture.manage.converter;


import com.smart.agriculture.manage.entity.MachineSensorEntity;
import com.smart.agriculture.manage.entity.PolicyManagementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * ClassName: MachineSensorConverter
 * Description:
 * date: 2021/5/13 9:46
 *
 * @author 蒋一帆
 */

@Mapper(componentModel = "spring")
public interface MachineSensorConverter {


    @Mappings(
            @Mapping(target = "id", ignore = true)
    )
    PolicyManagementEntity sensorToPolicy(MachineSensorEntity machineSensorEntity);
}
