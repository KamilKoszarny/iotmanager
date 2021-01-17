package pl.kamilkoszarny.iotmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceTypeDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link pl.kamilkoszarny.iotmanager.domain.DeviceType}.
 */
public interface DeviceTypeService {

    /**
     * Save a deviceType.
     *
     * @param deviceTypeDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceTypeDTO save(DeviceTypeDTO deviceTypeDTO);

    /**
     * Get all the deviceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" deviceType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceTypeDTO> findOne(Long id);

    /**
     * Delete the "id" deviceType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
