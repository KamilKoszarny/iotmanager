package pl.kamilkoszarny.iotmanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkoszarny.iotmanager.domain.DeviceFault;
import pl.kamilkoszarny.iotmanager.repository.DeviceFaultRepository;
import pl.kamilkoszarny.iotmanager.service.DeviceFaultService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFaultDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceFaultMapper;

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

    public DeviceFaultServiceImpl(DeviceFaultRepository deviceFaultRepository, DeviceFaultMapper deviceFaultMapper) {
        this.deviceFaultRepository = deviceFaultRepository;
        this.deviceFaultMapper = deviceFaultMapper;
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
    public Optional<DeviceFaultDTO> findOne(Long id) {
        log.debug("Request to get DeviceFault : {}", id);
        return deviceFaultRepository.findById(id)
            .map(deviceFaultMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceFault : {}", id);
        deviceFaultRepository.deleteById(id);
    }
}
