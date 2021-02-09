package pl.kamilkoszarny.iotmanager.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.Device} entity.
 */
@Getter
@Setter
@ToString
public class DeviceFriendlyDTO extends DeviceDTO implements Serializable {

    private String modelName;

    private String siteName;
}
