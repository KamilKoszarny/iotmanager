package pl.kamilkoszarny.iotmanager.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import pl.kamilkoszarny.iotmanager.domain.enumeration.DeviceCategory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private DeviceCategory category;

    @OneToMany(mappedBy = "type")
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

    public Set<DeviceModel> getModels() {
        return models;
    }

    public DeviceType models(Set<DeviceModel> deviceModels) {
        this.models = deviceModels;
        return this;
    }

    public DeviceType addModel(DeviceModel deviceModel) {
        this.models.add(deviceModel);
        deviceModel.setType(this);
        return this;
    }

    public DeviceType removeModel(DeviceModel deviceModel) {
        this.models.remove(deviceModel);
        deviceModel.setType(null);
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
