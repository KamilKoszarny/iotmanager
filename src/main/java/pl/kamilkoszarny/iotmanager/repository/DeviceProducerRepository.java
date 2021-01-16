package pl.kamilkoszarny.iotmanager.repository;

import pl.kamilkoszarny.iotmanager.domain.DeviceProducer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DeviceProducer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceProducerRepository extends JpaRepository<DeviceProducer, Long> {
}
