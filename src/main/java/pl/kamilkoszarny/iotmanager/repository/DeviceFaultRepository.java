package pl.kamilkoszarny.iotmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilkoszarny.iotmanager.domain.Device;
import pl.kamilkoszarny.iotmanager.domain.DeviceFault;

import java.util.List;

/**
 * Spring Data  repository for the DeviceFault entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceFaultRepository extends JpaRepository<DeviceFault, Long> {

    Page<DeviceFault> findAllByDeviceIn(Pageable pageable, List<Device> devices);
}
