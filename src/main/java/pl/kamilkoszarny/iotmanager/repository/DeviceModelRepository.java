package pl.kamilkoszarny.iotmanager.repository;

import pl.kamilkoszarny.iotmanager.domain.DeviceModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DeviceModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {
}
