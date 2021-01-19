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
 * A Site.
 */
@Entity
@Table(name = "site")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street")
    private String street;

    @NotNull
    @Column(name = "street_no", nullable = false)
    private String streetNo;

    @OneToMany(mappedBy = "site")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Device> devices = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "sites", allowSetters = true)
    private User user;

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

    public Site name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public Site city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public Site street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public Site streetNo(String streetNo) {
        this.streetNo = streetNo;
        return this;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public Site devices(Set<Device> devices) {
        this.devices = devices;
        return this;
    }

    public Site addDevice(Device device) {
        this.devices.add(device);
        device.setSite(this);
        return this;
    }

    public Site removeDevice(Device device) {
        this.devices.remove(device);
        device.setSite(null);
        return this;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public User getUser() {
        return user;
    }

    public Site user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Site)) {
            return false;
        }
        return id != null && id.equals(((Site) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Site{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", streetNo='" + getStreetNo() + "'" +
            "}";
    }
}
