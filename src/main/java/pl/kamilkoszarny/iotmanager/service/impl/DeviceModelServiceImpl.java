package pl.kamilkoszarny.iotmanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkoszarny.iotmanager.domain.DeviceModel;
import pl.kamilkoszarny.iotmanager.repository.DeviceModelRepository;
import pl.kamilkoszarny.iotmanager.service.DeviceModelService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceModelDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DeviceModel}.
 */
@Service
@Transactional
public class DeviceModelServiceImpl implements DeviceModelService {

    private final Logger log = LoggerFactory.getLogger(DeviceModelServiceImpl.class);

    private final DeviceModelRepository deviceModelRepository;

    private final DeviceModelMapper deviceModelMapper;

    public DeviceModelServiceImpl(DeviceModelRepository deviceModelRepository, DeviceModelMapper deviceModelMapper) {
        this.deviceModelRepository = deviceModelRepository;
        this.deviceModelMapper = deviceModelMapper;
    }

    @Override
    public DeviceModelDTO save(DeviceModelDTO deviceModelDTO) {
        log.debug("Request to save DeviceModel : {}", deviceModelDTO);
        DeviceModel deviceModel = deviceModelMapper.toEntity(deviceModelDTO);
        deviceModel = deviceModelRepository.save(deviceModel);
        return deviceModelMapper.toDto(deviceModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceModels");
        return deviceModelRepository.findAll(pageable)
            .map(deviceModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceModelDTO> findAllByTypeAndProducer(Long typeId, Long producerId) {
        log.debug("Request to get all DeviceModels by typeId: {} and producerId: {}", typeId, producerId);
        if (typeId != null && producerId != null) {
            return deviceModelRepository.findAllByTypeIdAndProducerId(typeId, producerId).stream().map(deviceModelMapper::toDto).collect(Collectors.toList());
        } else if (typeId != null) {
            return deviceModelRepository.findAllByTypeId(typeId).stream().map(deviceModelMapper::toDto).collect(Collectors.toList());
        } else if (producerId != null) {
            return deviceModelRepository.findAllByProducerId(producerId).stream().map(deviceModelMapper::toDto).collect(Collectors.toList());
        } else {
            return deviceModelRepository.findAll().stream().map(deviceModelMapper::toDto).collect(Collectors.toList());
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceModelDTO> findOne(Long id) {
        log.debug("Request to get DeviceModel : {}", id);
        return deviceModelRepository.findById(id)
            .map(deviceModelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceModel : {}", id);
        deviceModelRepository.deleteById(id);
    }
}
