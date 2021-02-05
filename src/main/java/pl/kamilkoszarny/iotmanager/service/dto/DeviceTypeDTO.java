package pl.kamilkoszarny.iotmanager.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.kamilkoszarny.iotmanager.domain.enumeration.DeviceCategory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.DeviceType} entity.
 */
@Getter
@Setter
@ToString
public class DeviceTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    private DeviceCategory category;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((DeviceTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
