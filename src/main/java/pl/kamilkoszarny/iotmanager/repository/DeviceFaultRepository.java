package pl.kamilkoszarny.iotmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilkoszarny.iotmanager.domain.DeviceFault;

/**
 * Spring Data  repository for the DeviceFault entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceFaultRepository extends JpaRepository<DeviceFault, Long> {
}
