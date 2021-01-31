package pl.kamilkoszarny.iotmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilkoszarny.iotmanager.domain.DeviceModel;

import java.util.List;

/**
 * Spring Data  repository for the DeviceModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {

    List<DeviceModel> findAllByTypeIdAndProducerId(Long typeId, Long producerId);

    List<DeviceModel> findAllByTypeId(Long typeId);

    List<DeviceModel> findAllByProducerId(Long producerId);
}
