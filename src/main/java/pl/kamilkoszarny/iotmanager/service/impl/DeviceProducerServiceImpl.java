package pl.kamilkoszarny.iotmanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkoszarny.iotmanager.domain.DeviceProducer;
import pl.kamilkoszarny.iotmanager.repository.DeviceProducerRepository;
import pl.kamilkoszarny.iotmanager.service.DeviceProducerService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceProducerDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceProducerMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeviceProducer}.
 */
@Service
@Transactional
public class DeviceProducerServiceImpl implements DeviceProducerService {

    private final Logger log = LoggerFactory.getLogger(DeviceProducerServiceImpl.class);

    private final DeviceProducerRepository deviceProducerRepository;

    private final DeviceProducerMapper deviceProducerMapper;

    public DeviceProducerServiceImpl(DeviceProducerRepository deviceProducerRepository, DeviceProducerMapper deviceProducerMapper) {
        this.deviceProducerRepository = deviceProducerRepository;
        this.deviceProducerMapper = deviceProducerMapper;
    }

    @Override
    public DeviceProducerDTO save(DeviceProducerDTO deviceProducerDTO) {
        log.debug("Request to save DeviceProducer : {}", deviceProducerDTO);
        DeviceProducer deviceProducer = deviceProducerMapper.toEntity(deviceProducerDTO);
        deviceProducer = deviceProducerRepository.save(deviceProducer);
        return deviceProducerMapper.toDto(deviceProducer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceProducerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceProducers");
        return deviceProducerRepository.findAll(pageable)
            .map(deviceProducerMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceProducerDTO> findOne(Long id) {
        log.debug("Request to get DeviceProducer : {}", id);
        return deviceProducerRepository.findById(id)
            .map(deviceProducerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceProducer : {}", id);
        deviceProducerRepository.deleteById(id);
    }
}
