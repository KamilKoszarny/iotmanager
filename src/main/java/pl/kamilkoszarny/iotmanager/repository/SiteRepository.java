package pl.kamilkoszarny.iotmanager.repository;

import pl.kamilkoszarny.iotmanager.domain.Site;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Site entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
}
