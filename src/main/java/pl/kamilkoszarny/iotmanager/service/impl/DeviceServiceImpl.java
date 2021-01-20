package pl.kamilkoszarny.iotmanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkoszarny.iotmanager.domain.Device;
import pl.kamilkoszarny.iotmanager.domain.Site;
import pl.kamilkoszarny.iotmanager.domain.User;
import pl.kamilkoszarny.iotmanager.repository.DeviceRepository;
import pl.kamilkoszarny.iotmanager.service.DeviceService;
import pl.kamilkoszarny.iotmanager.service.SiteService;
import pl.kamilkoszarny.iotmanager.service.UserService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceDTO;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFriendlyDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceMapper;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Device}.
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    private final UserService userService;

    private final SiteService siteService;

    public DeviceServiceImpl(DeviceRepository deviceRepository, DeviceMapper deviceMapper, UserService userService, SiteService siteService) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
        this.userService = userService;
        this.siteService = siteService;
    }

    @Override
    public DeviceDTO save(DeviceDTO deviceDTO) {
        log.debug("Request to save Device : {}", deviceDTO);
        Device device = deviceMapper.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        return deviceMapper.toDto(device);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        return deviceRepository.findAll(pageable)
            .map(deviceMapper::toDto);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceFriendlyDTO> findAllByCurrentUser(Pageable pageable) {
        // current user will be found as method is accessible only for legged users
        // noinspection OptionalGetWithoutIsPresent
        User user = userService.getCurrentUser().get();
        log.debug("Request to get all Devices of user: " + user.getLogin());
        List<Site> currentUserSites = siteService.findAllByCurrentUser();
        return deviceRepository.findAllBySiteIn(pageable, currentUserSites)
            .map(deviceMapper::toFriendlyDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceDTO> findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        return deviceRepository.findById(id)
            .map(deviceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.deleteById(id);
    }
}
