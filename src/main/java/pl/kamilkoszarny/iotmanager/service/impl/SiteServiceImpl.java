package pl.kamilkoszarny.iotmanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkoszarny.iotmanager.domain.Site;
import pl.kamilkoszarny.iotmanager.domain.User;
import pl.kamilkoszarny.iotmanager.repository.SiteRepository;
import pl.kamilkoszarny.iotmanager.service.SiteService;
import pl.kamilkoszarny.iotmanager.service.UserService;
import pl.kamilkoszarny.iotmanager.service.dto.SiteDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.SiteMapper;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Site}.
 */
@Service
@Transactional
public class SiteServiceImpl implements SiteService {

    private final Logger log = LoggerFactory.getLogger(SiteServiceImpl.class);

    private final SiteRepository siteRepository;

    private final SiteMapper siteMapper;

    private final UserService userService;


    public SiteServiceImpl(SiteRepository siteRepository, SiteMapper siteMapper, UserService userService) {
        this.siteRepository = siteRepository;
        this.siteMapper = siteMapper;
        this.userService = userService;
    }

    @Override
    public SiteDTO save(SiteDTO siteDTO) {
        log.debug("Request to save Site : {}", siteDTO);
        User currentUser = userService.getCurrentUser();
        if (siteDTO.getUserId() == null) {
            siteDTO.setUserId(currentUser.getId());
        }

        Site site = siteMapper.toEntity(siteDTO);
        site = siteRepository.save(site);
        return siteMapper.toDto(site);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sites");
        return siteRepository.findAll(pageable)
            .map(siteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Site> findAllByCurrentUser() {
        log.debug("Request to get all current user Sites");
        return siteRepository.findByUserIsCurrentUser();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SiteDTO> findAllByCurrentUser(Pageable pageable) {
        log.debug("Request to get all current user Sites");
        return siteRepository.findByUserIsCurrentUser(pageable)
            .map(siteMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SiteDTO> findOne(Long id) {
        log.debug("Request to get Site : {}", id);
        return siteRepository.findById(id)
            .map(siteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Site : {}", id);
        siteRepository.deleteById(id);
    }
}
