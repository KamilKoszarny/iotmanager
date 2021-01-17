package pl.kamilkoszarny.iotmanager.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceProducerMapperTest {

    private DeviceProducerMapper deviceProducerMapper;

    @BeforeEach
    public void setUp() {
        deviceProducerMapper = new DeviceProducerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(deviceProducerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(deviceProducerMapper.fromId(null)).isNull();
    }
}
