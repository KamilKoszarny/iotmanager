package pl.kamilkoszarny.iotmanager.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.Site} entity.
 */
@Getter
@Setter
@ToString
public class SiteWithDevicesDTO extends SiteDTO implements Serializable {

    private Set<DeviceFriendlyWithFaultsDTO> devices;

}
