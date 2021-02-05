package pl.kamilkoszarny.iotmanager.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.DeviceProducer} entity.
 */
@Getter
@Setter
@ToString
public class DeviceProducerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 80)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceProducerDTO)) {
            return false;
        }

        return id != null && id.equals(((DeviceProducerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
