package pl.kamilkoszarny.iotmanager.service.dto;

import org.junit.jupiter.api.Test;
import pl.kamilkoszarny.iotmanager.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceFaultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceFaultDTO.class);
        DeviceFaultDTO deviceFaultDTO1 = new DeviceFaultDTO();
        deviceFaultDTO1.setId(1L);
        DeviceFaultDTO deviceFaultDTO2 = new DeviceFaultDTO();
        assertThat(deviceFaultDTO1).isNotEqualTo(deviceFaultDTO2);
        deviceFaultDTO2.setId(deviceFaultDTO1.getId());
        assertThat(deviceFaultDTO1).isEqualTo(deviceFaultDTO2);
        deviceFaultDTO2.setId(2L);
        assertThat(deviceFaultDTO1).isNotEqualTo(deviceFaultDTO2);
        deviceFaultDTO1.setId(null);
        assertThat(deviceFaultDTO1).isNotEqualTo(deviceFaultDTO2);
    }
}
