package pl.kamilkoszarny.iotmanager.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.DeviceModel} entity.
 */
@Getter
@Setter
@ToString
public class DeviceModelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;


    private Long producerId;

    private Long typeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceModelDTO)) {
            return false;
        }

        return id != null && id.equals(((DeviceModelDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
