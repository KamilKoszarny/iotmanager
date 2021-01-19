package pl.kamilkoszarny.iotmanager.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.DeviceModel} entity.
 */
public class DeviceModelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;


    private Long producerId;

    private Long typeId;

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

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long deviceProducerId) {
        this.producerId = deviceProducerId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long deviceTypeId) {
        this.typeId = deviceTypeId;
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
            ", producerId=" + getProducerId() +
            ", typeId=" + getTypeId() +
            "}";
    }
}
