package pl.kamilkoszarny.iotmanager.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeviceProducer.
 */
@Entity
@Table(name = "device_producer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceProducer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "deviceProducer")
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

    public DeviceProducer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DeviceModel> getDeviceModels() {
        return deviceModels;
    }

    public DeviceProducer deviceModels(Set<DeviceModel> deviceModels) {
        this.deviceModels = deviceModels;
        return this;
    }

    public DeviceProducer addDeviceModel(DeviceModel deviceModel) {
        this.deviceModels.add(deviceModel);
        deviceModel.setDeviceProducer(this);
        return this;
    }

    public DeviceProducer removeDeviceModel(DeviceModel deviceModel) {
        this.deviceModels.remove(deviceModel);
        deviceModel.setDeviceProducer(null);
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
        if (!(o instanceof DeviceProducer)) {
            return false;
        }
        return id != null && id.equals(((DeviceProducer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceProducer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
