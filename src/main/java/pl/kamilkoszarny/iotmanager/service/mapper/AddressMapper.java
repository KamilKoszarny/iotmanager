package pl.kamilkoszarny.iotmanager.service.mapper;


import org.mapstruct.Mapper;
import pl.kamilkoszarny.iotmanager.domain.Address;
import pl.kamilkoszarny.iotmanager.service.dto.AddressDTO;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {



    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
