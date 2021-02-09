package pl.kamilkoszarny.iotmanager.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilkoszarny.iotmanager.domain.DeviceModel;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceModelDTO;

/**
 * Mapper for the entity {@link DeviceModel} and its DTO {@link DeviceModelDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeviceProducerMapper.class, DeviceTypeMapper.class})
public interface DeviceModelMapper extends EntityMapper<DeviceModelDTO, DeviceModel> {

    @Mapping(source = "producer.id", target = "producerId")
    @Mapping(source = "type.id", target = "typeId")
    DeviceModelDTO toDto(DeviceModel deviceModel);

    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "removeDevice", ignore = true)
    @Mapping(source = "producerId", target = "producer")
    @Mapping(source = "typeId", target = "type")
    DeviceModel toEntity(DeviceModelDTO deviceModelDTO);

    default DeviceModel fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setId(id);
        return deviceModel;
    }
}
