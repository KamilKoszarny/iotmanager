package pl.kamilkoszarny.iotmanager.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilkoszarny.iotmanager.domain.DeviceType;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceTypeDTO;

/**
 * Mapper for the entity {@link DeviceType} and its DTO {@link DeviceTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeviceTypeMapper extends EntityMapper<DeviceTypeDTO, DeviceType> {


    @Mapping(target = "models", ignore = true)
    @Mapping(target = "removeModel", ignore = true)
    DeviceType toEntity(DeviceTypeDTO deviceTypeDTO);

    default DeviceType fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeviceType deviceType = new DeviceType();
        deviceType.setId(id);
        return deviceType;
    }
}
