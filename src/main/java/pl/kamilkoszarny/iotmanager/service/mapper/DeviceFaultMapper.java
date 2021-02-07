package pl.kamilkoszarny.iotmanager.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilkoszarny.iotmanager.domain.DeviceFault;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFaultDTO;

/**
 * Mapper for the entity {@link DeviceFault} and its DTO {@link DeviceFaultDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeviceMapper.class})
public interface DeviceFaultMapper extends EntityMapper<DeviceFaultDTO, DeviceFault> {

    @Mapping(source = "device.id", target = "deviceId")
    DeviceFaultDTO toDto(DeviceFault deviceFault);

    @Mapping(source = "deviceId", target = "device")
    DeviceFault toEntity(DeviceFaultDTO deviceFaultDTO);

    default DeviceFault fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeviceFault deviceFault = new DeviceFault();
        deviceFault.setId(id);
        return deviceFault;
    }
}
