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
import pl.kamilkoszarny.iotmanager.security.AuthoritiesConstants;
import pl.kamilkoszarny.iotmanager.security.SecurityUtils;
import pl.kamilkoszarny.iotmanager.service.DeviceService;
import pl.kamilkoszarny.iotmanager.service.SiteService;
import pl.kamilkoszarny.iotmanager.service.UserService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceDTO;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFriendlyDTO;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFriendlyWithFaultsDTO;
import pl.kamilkoszarny.iotmanager.service.exceptions.NotYourEntityException;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        User user = userService.getCurrentUser();
        log.debug("Request to get all Devices of user: " + user.getLogin());
        List<Site> currentUserSites = siteService.findAllByCurrentUser();
        return deviceRepository.findAllBySiteIn(pageable, currentUserSites)
            .map(deviceMapper::toFriendlyDto);
    }

    public List<Device> findAllByCurrentUser() {
        List<Site> currentUserSites = siteService.findAllByCurrentUser();
        return deviceRepository.findAllBySiteIn(currentUserSites);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceFriendlyDTO> findAllBySiteId(Long siteId) {
        log.debug("Request to get Devices of by siteId: " + siteId);
        return deviceRepository.findBySiteId(siteId).stream()
            .map(deviceMapper::toFriendlyDto).collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceFriendlyWithFaultsDTO> findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        final Device device = deviceRepository.findById(id).orElse(null);
        User currentUser = userService.getCurrentUser();
        if (device != null && !device.getSite().getUser().getId().equals(currentUser.getId()) && !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            throw new NotYourEntityException(currentUser.getId(), "Device", device.getId());
        }
        return Optional.ofNullable(deviceMapper.toFriendlyWithFaultsDto(device));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.deleteById(id);
    }
}
