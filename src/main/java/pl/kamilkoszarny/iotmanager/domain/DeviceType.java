package pl.kamilkoszarny.iotmanager.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import pl.kamilkoszarny.iotmanager.domain.enumeration.DeviceCategory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeviceType.
 */
@Entity
@Table(name = "device_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private DeviceCategory category;

    @OneToMany(mappedBy = "deviceType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<DeviceModel> deviceModels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DeviceType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceCategory getCategory() {
        return category;
    }

    public DeviceType category(DeviceCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(DeviceCategory category) {
        this.category = category;
    }

    public Set<DeviceModel> getDeviceModels() {
        return deviceModels;
    }

    public DeviceType deviceModels(Set<DeviceModel> deviceModels) {
        this.deviceModels = deviceModels;
        return this;
    }

    public DeviceType addDeviceModel(DeviceModel deviceModel) {
        this.deviceModels.add(deviceModel);
        deviceModel.setDeviceType(this);
        return this;
    }

    public DeviceType removeDeviceModel(DeviceModel deviceModel) {
        this.deviceModels.remove(deviceModel);
        deviceModel.setDeviceType(null);
        return this;
    }

    public void setDeviceModels(Set<DeviceModel> deviceModels) {
        this.deviceModels = deviceModels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceType)) {
            return false;
        }
        return id != null && id.equals(((DeviceType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
