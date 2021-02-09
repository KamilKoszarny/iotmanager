package pl.kamilkoszarny.iotmanager.service.mapper;


import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilkoszarny.iotmanager.domain.Site;
import pl.kamilkoszarny.iotmanager.service.dto.SiteDTO;
import pl.kamilkoszarny.iotmanager.service.dto.SiteWithDevicesDTO;

/**
 * Mapper for the entity {@link Site} and its DTO {@link SiteDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DeviceMapper.class})
public interface SiteMapper {

    @Mapping(source = "user.id", target = "userId")
    @IterableMapping(elementTargetType = SiteDTO.class)
    SiteDTO toDto(Site site);

    @Mapping(source = "user.id", target = "userId")
    SiteWithDevicesDTO toWithDevicesDto(Site site);

    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "removeDevice", ignore = true)
    @Mapping(source = "userId", target = "user")
    Site toEntity(SiteDTO siteDTO);

    default Site fromId(Long id) {
        if (id == null) {
            return null;
        }
        Site site = new Site();
        site.setId(id);
        return site;
    }
}
