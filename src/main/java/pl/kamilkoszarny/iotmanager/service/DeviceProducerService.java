package pl.kamilkoszarny.iotmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceProducerDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link pl.kamilkoszarny.iotmanager.domain.DeviceProducer}.
 */
public interface DeviceProducerService {

    /**
     * Save a deviceProducer.
     *
     * @param deviceProducerDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceProducerDTO save(DeviceProducerDTO deviceProducerDTO);

    /**
     * Get all the deviceProducers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceProducerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" deviceProducer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceProducerDTO> findOne(Long id);

    /**
     * Delete the "id" deviceProducer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
