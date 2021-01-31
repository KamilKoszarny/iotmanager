package pl.kamilkoszarny.iotmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceModelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link pl.kamilkoszarny.iotmanager.domain.DeviceModel}.
 */
public interface DeviceModelService {

    /**
     * Save a deviceModel.
     *
     * @param deviceModelDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceModelDTO save(DeviceModelDTO deviceModelDTO);

    /**
     * Get all the deviceModels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceModelDTO> findAll(Pageable pageable);

    /**
     * Get all the deviceModels by Type and Producer.
     *
     * @param typeId device type id.
     * @param producerId device producer id.
     * @return the list of entities.
     */
    List<DeviceModelDTO> findAllByTypeAndProducer(Long typeId, Long producerId);


    /**
     * Get the "id" deviceModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceModelDTO> findOne(Long id);

    /**
     * Delete the "id" deviceModel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
