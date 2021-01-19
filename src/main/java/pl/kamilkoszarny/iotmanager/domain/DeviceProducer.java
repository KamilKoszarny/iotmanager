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

    @OneToMany(mappedBy = "producer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<DeviceModel> models = new HashSet<>();

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

    public Set<DeviceModel> getModels() {
        return models;
    }

    public DeviceProducer models(Set<DeviceModel> deviceModels) {
        this.models = deviceModels;
        return this;
    }

    public DeviceProducer addModel(DeviceModel deviceModel) {
        this.models.add(deviceModel);
        deviceModel.setProducer(this);
        return this;
    }

    public DeviceProducer removeModel(DeviceModel deviceModel) {
        this.models.remove(deviceModel);
        deviceModel.setProducer(null);
        return this;
    }

    public void setModels(Set<DeviceModel> deviceModels) {
        this.models = deviceModels;
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
