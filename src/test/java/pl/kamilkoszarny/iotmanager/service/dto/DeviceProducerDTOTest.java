package pl.kamilkoszarny.iotmanager.service.dto;

import org.junit.jupiter.api.Test;
import pl.kamilkoszarny.iotmanager.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceProducerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceProducerDTO.class);
        DeviceProducerDTO deviceProducerDTO1 = new DeviceProducerDTO();
        deviceProducerDTO1.setId(1L);
        DeviceProducerDTO deviceProducerDTO2 = new DeviceProducerDTO();
        assertThat(deviceProducerDTO1).isNotEqualTo(deviceProducerDTO2);
        deviceProducerDTO2.setId(deviceProducerDTO1.getId());
        assertThat(deviceProducerDTO1).isEqualTo(deviceProducerDTO2);
        deviceProducerDTO2.setId(2L);
        assertThat(deviceProducerDTO1).isNotEqualTo(deviceProducerDTO2);
        deviceProducerDTO1.setId(null);
        assertThat(deviceProducerDTO1).isNotEqualTo(deviceProducerDTO2);
    }
}
