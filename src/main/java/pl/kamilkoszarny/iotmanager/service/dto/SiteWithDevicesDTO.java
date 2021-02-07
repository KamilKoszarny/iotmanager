package pl.kamilkoszarny.iotmanager.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.Site} entity.
 */
@Getter
@Setter
@ToString
public class SiteWithDevicesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String city;

    @Size(max = 50)
    private String street;

    @NotNull
    @Size(max = 10)
    private String streetNo;

    private Set<DeviceFriendlyDTO> devices;


    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteWithDevicesDTO)) {
            return false;
        }

        return id != null && id.equals(((SiteWithDevicesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
