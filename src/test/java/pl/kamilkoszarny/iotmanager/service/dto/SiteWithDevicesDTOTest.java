package pl.kamilkoszarny.iotmanager.service.dto;

import org.junit.jupiter.api.Test;
import pl.kamilkoszarny.iotmanager.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class SiteWithDevicesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteWithDevicesDTO.class);
        SiteWithDevicesDTO siteWithDevicesDTO1 = new SiteWithDevicesDTO();
        siteWithDevicesDTO1.setId(1L);
        SiteWithDevicesDTO siteWithDevicesDTO2 = new SiteWithDevicesDTO();
        assertThat(siteWithDevicesDTO1).isNotEqualTo(siteWithDevicesDTO2);
        siteWithDevicesDTO2.setId(siteWithDevicesDTO1.getId());
        assertThat(siteWithDevicesDTO1).isEqualTo(siteWithDevicesDTO2);
        siteWithDevicesDTO2.setId(2L);
        assertThat(siteWithDevicesDTO1).isNotEqualTo(siteWithDevicesDTO2);
        siteWithDevicesDTO1.setId(null);
        assertThat(siteWithDevicesDTO1).isNotEqualTo(siteWithDevicesDTO2);
    }
}
