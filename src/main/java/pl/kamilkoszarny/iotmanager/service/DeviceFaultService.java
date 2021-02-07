package pl.kamilkoszarny.iotmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFaultDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link pl.kamilkoszarny.iotmanager.domain.DeviceFault}.
 */
public interface DeviceFaultService {

    /**
     * Save a deviceFault.
     *
     * @param deviceFaultDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceFaultDTO save(DeviceFaultDTO deviceFaultDTO);

    /**
     * Get all the deviceFaults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceFaultDTO> findAll(Pageable pageable);


    /**
     * Get the "id" deviceFault.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceFaultDTO> findOne(Long id);

    /**
     * Delete the "id" deviceFault.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
