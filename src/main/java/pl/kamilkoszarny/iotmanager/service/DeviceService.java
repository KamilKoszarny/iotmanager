package pl.kamilkoszarny.iotmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkoszarny.iotmanager.domain.Device;
import pl.kamilkoszarny.iotmanager.domain.User;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceDTO;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFriendlyDTO;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFriendlyWithFaultsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link pl.kamilkoszarny.iotmanager.domain.Device}.
 */
public interface DeviceService {

    /**
     * Save a device.
     *
     * @param deviceDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceDTO save(DeviceDTO deviceDTO);

    /**
     * Get all the devices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceDTO> findAll(Pageable pageable);


    @Transactional(readOnly = true)
    Page<DeviceFriendlyDTO> findAllByCurrentUser(Pageable pageable);
    @Transactional(readOnly = true)
    List<Device> findAllByCurrentUser();


    @Transactional(readOnly = true)
    List<DeviceFriendlyDTO> findAllBySiteId(Long siteId);

    /**
     * Get the "id" device.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceFriendlyWithFaultsDTO> findOne(Long id);

    boolean isNotDeviceOfUser(Device device, User currentUser);

    /**
     * Delete the "id" device.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
