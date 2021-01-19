package pl.kamilkoszarny.iotmanager.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.DeviceModel} entity.
 */
public class DeviceModelDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    private Long deviceProducerId;

    private Long deviceTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDeviceProducerId() {
        return deviceProducerId;
    }

    public void setDeviceProducerId(Long deviceProducerId) {
        this.deviceProducerId = deviceProducerId;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

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

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceModelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", deviceProducerId=" + getDeviceProducerId() +
            ", deviceTypeId=" + getDeviceTypeId() +
            "}";
    }
}
