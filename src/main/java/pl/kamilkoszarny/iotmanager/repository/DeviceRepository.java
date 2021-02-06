package pl.kamilkoszarny.iotmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilkoszarny.iotmanager.domain.Device;
import pl.kamilkoszarny.iotmanager.domain.Site;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data  repository for the Device entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findBySiteId(Long siteId);

    Page<Device> findAllBySiteIn(Pageable pageable, Collection<Site> sites);
}
