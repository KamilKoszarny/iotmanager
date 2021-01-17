package pl.kamilkoszarny.iotmanager.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilkoszarny.iotmanager.domain.DeviceProducer;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceProducerDTO;

/**
 * Mapper for the entity {@link DeviceProducer} and its DTO {@link DeviceProducerDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeviceProducerMapper extends EntityMapper<DeviceProducerDTO, DeviceProducer> {


    @Mapping(target = "deviceModels", ignore = true)
    @Mapping(target = "removeDeviceModel", ignore = true)
    DeviceProducer toEntity(DeviceProducerDTO deviceProducerDTO);

    default DeviceProducer fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeviceProducer deviceProducer = new DeviceProducer();
        deviceProducer.setId(id);
        return deviceProducer;
    }
}
