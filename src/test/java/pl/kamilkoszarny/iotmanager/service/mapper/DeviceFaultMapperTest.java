package pl.kamilkoszarny.iotmanager.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceFaultMapperTest {

    private DeviceFaultMapper deviceFaultMapper;

    @BeforeEach
    public void setUp() {
        deviceFaultMapper = new DeviceFaultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(deviceFaultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(deviceFaultMapper.fromId(null)).isNull();
    }
}
