package pl.kamilkoszarny.iotmanager.service.dto;

import org.junit.jupiter.api.Test;
import pl.kamilkoszarny.iotmanager.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceFriendyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceFriendlyDTO.class);
        DeviceDTO deviceFriendlyDTO1 = new DeviceDTO();
        deviceFriendlyDTO1.setId(1L);
        DeviceDTO deviceFriendlyDTO2 = new DeviceDTO();
        assertThat(deviceFriendlyDTO1).isNotEqualTo(deviceFriendlyDTO2);
        deviceFriendlyDTO2.setId(deviceFriendlyDTO1.getId());
        assertThat(deviceFriendlyDTO1).isEqualTo(deviceFriendlyDTO2);
        deviceFriendlyDTO2.setId(2L);
        assertThat(deviceFriendlyDTO1).isNotEqualTo(deviceFriendlyDTO2);
        deviceFriendlyDTO1.setId(null);
        assertThat(deviceFriendlyDTO1).isNotEqualTo(deviceFriendlyDTO2);
    }
}
