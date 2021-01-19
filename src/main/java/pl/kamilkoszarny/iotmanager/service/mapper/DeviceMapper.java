package pl.kamilkoszarny.iotmanager.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilkoszarny.iotmanager.domain.Device;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceDTO;

/**
 * Mapper for the entity {@link Device} and its DTO {@link DeviceDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeviceModelMapper.class, SiteMapper.class})
public interface DeviceMapper extends EntityMapper<DeviceDTO, Device> {

    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "site.id", target = "siteId")
    DeviceDTO toDto(Device device);

    @Mapping(source = "modelId", target = "model")
    @Mapping(source = "siteId", target = "site")
    Device toEntity(DeviceDTO deviceDTO);

    default Device fromId(Long id) {
        if (id == null) {
            return null;
        }
        Device device = new Device();
        device.setId(id);
        return device;
    }
}
