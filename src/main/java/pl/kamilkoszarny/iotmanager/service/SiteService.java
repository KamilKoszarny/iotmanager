package pl.kamilkoszarny.iotmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkoszarny.iotmanager.domain.Site;
import pl.kamilkoszarny.iotmanager.service.dto.SiteDTO;
import pl.kamilkoszarny.iotmanager.service.exceptions.NotYourEntityException;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link pl.kamilkoszarny.iotmanager.domain.Site}.
 */
public interface SiteService {

    /**
     * Save a site.
     *
     * @param siteDTO the entity to save.
     * @return the persisted entity.
     */
    SiteDTO save(SiteDTO siteDTO) throws NotYourEntityException;

    /**
     * Get all the sites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SiteDTO> findAll(Pageable pageable);


    @Transactional(readOnly = true)
    List<Site> findAllByCurrentUser();

    @Transactional(readOnly = true)
    Page<SiteDTO> findAllByCurrentUser(Pageable pageable);

    /**
     * Get the "id" site.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SiteDTO> findOne(Long id);

    /**
     * Delete the "id" site.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
