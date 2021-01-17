package pl.kamilkoszarny.iotmanager.service.dto;

import org.junit.jupiter.api.Test;
import pl.kamilkoszarny.iotmanager.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressDTO.class);
        AddressDTO addressDTO1 = new AddressDTO();
        addressDTO1.setId(1L);
        AddressDTO addressDTO2 = new AddressDTO();
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
        addressDTO2.setId(addressDTO1.getId());
        assertThat(addressDTO1).isEqualTo(addressDTO2);
        addressDTO2.setId(2L);
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
        addressDTO1.setId(null);
        assertThat(addressDTO1).isNotEqualTo(addressDTO2);
    }
}
