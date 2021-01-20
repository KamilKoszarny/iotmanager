package pl.kamilkoszarny.iotmanager.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.Device} entity.
 */
@Data
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

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", serialNo='" + getSerialNo() + "'" +
            ", modelId=" + getModelId() +
            ", siteId=" + getSiteId() +
            "}";
    }
}
