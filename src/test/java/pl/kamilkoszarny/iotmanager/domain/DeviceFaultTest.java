package pl.kamilkoszarny.iotmanager.domain;

import org.junit.jupiter.api.Test;
import pl.kamilkoszarny.iotmanager.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceFaultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceFault.class);
        DeviceFault deviceFault1 = new DeviceFault();
        deviceFault1.setId(1L);
        DeviceFault deviceFault2 = new DeviceFault();
        deviceFault2.setId(deviceFault1.getId());
        assertThat(deviceFault1).isEqualTo(deviceFault2);
        deviceFault2.setId(2L);
        assertThat(deviceFault1).isNotEqualTo(deviceFault2);
        deviceFault1.setId(null);
        assertThat(deviceFault1).isNotEqualTo(deviceFault2);
    }
}
