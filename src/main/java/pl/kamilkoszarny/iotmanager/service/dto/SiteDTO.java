package pl.kamilkoszarny.iotmanager.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link pl.kamilkoszarny.iotmanager.domain.Site} entity.
 */
public class SiteDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String city;

    private String street;

    @NotNull
    private String streetNo;


    private Long userId;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteDTO)) {
            return false;
        }

        return id != null && id.equals(((SiteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", streetNo='" + getStreetNo() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
