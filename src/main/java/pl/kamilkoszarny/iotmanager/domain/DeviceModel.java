package pl.kamilkoszarny.iotmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeviceModel.
 */
@Entity
@Table(name = "device_model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "model")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Device> models = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "models", allowSetters = true)
    private DeviceProducer producer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "models", allowSetters = true)
    private DeviceType type;

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

    public DeviceModel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Device> getModels() {
        return models;
    }

    public DeviceModel models(Set<Device> devices) {
        this.models = devices;
        return this;
    }

    public DeviceModel addModel(Device device) {
        this.models.add(device);
        device.setModel(this);
        return this;
    }

    public DeviceModel removeModel(Device device) {
        this.models.remove(device);
        device.setModel(null);
        return this;
    }

    public void setModels(Set<Device> devices) {
        this.models = devices;
    }

    public DeviceProducer getProducer() {
        return producer;
    }

    public DeviceModel producer(DeviceProducer deviceProducer) {
        this.producer = deviceProducer;
        return this;
    }

    public void setProducer(DeviceProducer deviceProducer) {
        this.producer = deviceProducer;
    }

    public DeviceType getType() {
        return type;
    }

    public DeviceModel type(DeviceType deviceType) {
        this.type = deviceType;
        return this;
    }

    public void setType(DeviceType deviceType) {
        this.type = deviceType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceModel)) {
            return false;
        }
        return id != null && id.equals(((DeviceModel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceModel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
