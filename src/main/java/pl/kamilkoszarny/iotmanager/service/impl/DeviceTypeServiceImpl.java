package pl.kamilkoszarny.iotmanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkoszarny.iotmanager.domain.DeviceType;
import pl.kamilkoszarny.iotmanager.repository.DeviceTypeRepository;
import pl.kamilkoszarny.iotmanager.service.DeviceTypeService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceTypeDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceTypeMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeviceType}.
 */
@Service
@Transactional
public class DeviceTypeServiceImpl implements DeviceTypeService {

    private final Logger log = LoggerFactory.getLogger(DeviceTypeServiceImpl.class);

    private final DeviceTypeRepository deviceTypeRepository;

    private final DeviceTypeMapper deviceTypeMapper;

    public DeviceTypeServiceImpl(DeviceTypeRepository deviceTypeRepository, DeviceTypeMapper deviceTypeMapper) {
        this.deviceTypeRepository = deviceTypeRepository;
        this.deviceTypeMapper = deviceTypeMapper;
    }

    @Override
    public DeviceTypeDTO save(DeviceTypeDTO deviceTypeDTO) {
        log.debug("Request to save DeviceType : {}", deviceTypeDTO);
        DeviceType deviceType = deviceTypeMapper.toEntity(deviceTypeDTO);
        deviceType = deviceTypeRepository.save(deviceType);
        return deviceTypeMapper.toDto(deviceType);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceTypes");
        return deviceTypeRepository.findAll(pageable)
            .map(deviceTypeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceTypeDTO> findOne(Long id) {
        log.debug("Request to get DeviceType : {}", id);
        return deviceTypeRepository.findById(id)
            .map(deviceTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceType : {}", id);
        deviceTypeRepository.deleteById(id);
    }
}
