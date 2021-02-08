package pl.kamilkoszarny.iotmanager.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.DeviceFault} entity.
 */
@Getter
@Setter
@ToString
public class DeviceFaultDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 1000)
    private String description;

    @Min(value = 1)
    @Max(value = 10)
    private Integer urgency;


    private Long deviceId;

    private String deviceName;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceFaultDTO)) {
            return false;
        }

        return id != null && id.equals(((DeviceFaultDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
