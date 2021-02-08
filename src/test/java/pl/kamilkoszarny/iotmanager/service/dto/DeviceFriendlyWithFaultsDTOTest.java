package pl.kamilkoszarny.iotmanager.service.dto;

import org.junit.jupiter.api.Test;
import pl.kamilkoszarny.iotmanager.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceFriendlyWithFaultsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceFriendlyWithFaultsDTO.class);
        DeviceDTO deviceFriendlyWithFaultsDTO1 = new DeviceDTO();
        deviceFriendlyWithFaultsDTO1.setId(1L);
        DeviceDTO deviceFriendlyWithFaultsDTO2 = new DeviceDTO();
        assertThat(deviceFriendlyWithFaultsDTO1).isNotEqualTo(deviceFriendlyWithFaultsDTO2);
        deviceFriendlyWithFaultsDTO2.setId(deviceFriendlyWithFaultsDTO1.getId());
        assertThat(deviceFriendlyWithFaultsDTO1).isEqualTo(deviceFriendlyWithFaultsDTO2);
        deviceFriendlyWithFaultsDTO2.setId(2L);
        assertThat(deviceFriendlyWithFaultsDTO1).isNotEqualTo(deviceFriendlyWithFaultsDTO2);
        deviceFriendlyWithFaultsDTO1.setId(null);
        assertThat(deviceFriendlyWithFaultsDTO1).isNotEqualTo(deviceFriendlyWithFaultsDTO2);
    }
}
