package pl.kamilkoszarny.iotmanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkoszarny.iotmanager.domain.Device;
import pl.kamilkoszarny.iotmanager.domain.DeviceFault;
import pl.kamilkoszarny.iotmanager.domain.User;
import pl.kamilkoszarny.iotmanager.repository.DeviceFaultRepository;
import pl.kamilkoszarny.iotmanager.security.AuthoritiesConstants;
import pl.kamilkoszarny.iotmanager.security.SecurityUtils;
import pl.kamilkoszarny.iotmanager.service.DeviceFaultService;
import pl.kamilkoszarny.iotmanager.service.DeviceService;
import pl.kamilkoszarny.iotmanager.service.UserService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFaultDTO;
import pl.kamilkoszarny.iotmanager.service.exceptions.NotYourEntityException;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceFaultMapper;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DeviceFault}.
 */
@Service
@Transactional
public class DeviceFaultServiceImpl implements DeviceFaultService {

    private final Logger log = LoggerFactory.getLogger(DeviceFaultServiceImpl.class);

    private final DeviceFaultRepository deviceFaultRepository;

    private final DeviceFaultMapper deviceFaultMapper;

    private final UserService userService;

    private final DeviceService deviceService;

    public DeviceFaultServiceImpl(DeviceFaultRepository deviceFaultRepository, DeviceFaultMapper deviceFaultMapper, UserService userService, DeviceService deviceService) {
        this.deviceFaultRepository = deviceFaultRepository;
        this.deviceFaultMapper = deviceFaultMapper;
        this.userService = userService;
        this.deviceService = deviceService;
    }

    @Override
    public DeviceFaultDTO save(DeviceFaultDTO deviceFaultDTO) {
        log.debug("Request to save DeviceFault : {}", deviceFaultDTO);
        DeviceFault deviceFault = deviceFaultMapper.toEntity(deviceFaultDTO);
        deviceFault = deviceFaultRepository.save(deviceFault);
        return deviceFaultMapper.toDto(deviceFault);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceFaultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceFaults");
        return deviceFaultRepository.findAll(pageable)
            .map(deviceFaultMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceFaultDTO> findAllByCurrentUser(Pageable pageable) {
        User user = userService.getCurrentUser();
        log.debug("Request to get all DeviceFaults of user: " + user.getLogin());
        List<Device> userDevices = deviceService.findAllByCurrentUser();
        return deviceFaultRepository.findAllByDeviceIn(pageable, userDevices)
            .map(deviceFaultMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceFaultDTO> findOne(Long id) throws NotYourEntityException {
        log.debug("Request to get DeviceFault : {}", id);
        final DeviceFault deviceFault = deviceFaultRepository.findById(id).orElse(null);
        User currentUser = userService.getCurrentUser();
        if (deviceFault != null && isNotDeviceFaultOfUser(deviceFault, currentUser) && !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            throw new NotYourEntityException(currentUser.getId(), "DeviceFault", deviceFault.getId());
        }
        return deviceFaultRepository.findById(id)
            .map(deviceFaultMapper::toDto);
    }

    private boolean isNotDeviceFaultOfUser(DeviceFault deviceFault, User currentUser) {
        return deviceService.isNotDeviceOfUser(deviceFault.getDevice(), currentUser);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceFault : {}", id);
        deviceFaultRepository.deleteById(id);
    }
}
