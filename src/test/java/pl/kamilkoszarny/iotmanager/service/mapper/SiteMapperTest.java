package pl.kamilkoszarny.iotmanager.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SiteMapperTest {

    private SiteMapper siteMapper;

    @BeforeEach
    public void setUp() {
        siteMapper = new SiteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(siteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(siteMapper.fromId(null)).isNull();
    }
}
