package pl.kamilkoszarny.iotmanager.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.Device} entity.
 */
@Getter
@Setter
@ToString
public class DeviceFriendlyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 80)
    private String serialNo;

    private Long modelId;

    private String modelName;

    private Long siteId;

    private String siteName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceFriendlyDTO)) {
            return false;
        }

        return id != null && id.equals(((DeviceFriendlyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
