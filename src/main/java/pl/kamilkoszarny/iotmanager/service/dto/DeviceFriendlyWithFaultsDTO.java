package pl.kamilkoszarny.iotmanager.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.Device} entity.
 */
@Getter
@Setter
@ToString
public class DeviceFriendlyWithFaultsDTO extends DeviceFriendlyDTO implements Serializable {

    private List<DeviceFaultDTO> faults;
}
