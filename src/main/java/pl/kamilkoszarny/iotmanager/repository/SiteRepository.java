package pl.kamilkoszarny.iotmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kamilkoszarny.iotmanager.domain.Site;

import java.util.List;

/**
 * Spring Data  repository for the Site entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

    @Query("select site from Site site where site.user.login = ?#{principal.username}")
    List<Site> findByUserIsCurrentUser();
}
