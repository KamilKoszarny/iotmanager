package pl.kamilkoszarny.iotmanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.kamilkoszarny.iotmanager.web.rest.TestUtil;

public class DeviceProducerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceProducer.class);
        DeviceProducer deviceProducer1 = new DeviceProducer();
        deviceProducer1.setId(1L);
        DeviceProducer deviceProducer2 = new DeviceProducer();
        deviceProducer2.setId(deviceProducer1.getId());
        assertThat(deviceProducer1).isEqualTo(deviceProducer2);
        deviceProducer2.setId(2L);
        assertThat(deviceProducer1).isNotEqualTo(deviceProducer2);
        deviceProducer1.setId(null);
        assertThat(deviceProducer1).isNotEqualTo(deviceProducer2);
    }
}
