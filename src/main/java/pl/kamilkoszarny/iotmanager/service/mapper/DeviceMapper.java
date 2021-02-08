package pl.kamilkoszarny.iotmanager.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilkoszarny.iotmanager.domain.Device;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceDTO;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFriendlyDTO;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFriendlyWithFaultsDTO;

/**
 * Mapper for the entity {@link Device} and its DTO {@link DeviceDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeviceModelMapper.class, SiteMapper.class, DeviceFaultMapper.class})
public interface DeviceMapper extends EntityMapper<DeviceDTO, Device> {

    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "site.id", target = "siteId")
    DeviceDTO toDto(Device device);

    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "model.name", target = "modelName")
    @Mapping(source = "site.id", target = "siteId")
    @Mapping(source = "site.name", target = "siteName")
    DeviceFriendlyDTO toFriendlyDto(Device device);

    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "model.name", target = "modelName")
    @Mapping(source = "site.id", target = "siteId")
    @Mapping(source = "site.name", target = "siteName")
    DeviceFriendlyWithFaultsDTO toFriendlyWithFaultsDto(Device device);

    @Mapping(target = "faults", ignore = true)
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
