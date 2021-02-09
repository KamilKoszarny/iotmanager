package pl.kamilkoszarny.iotmanager.web.rest;

import liquibase.util.csv.CSVReader;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkoszarny.iotmanager.IotmanagerApp;
import pl.kamilkoszarny.iotmanager.domain.Site;
import pl.kamilkoszarny.iotmanager.domain.User;
import pl.kamilkoszarny.iotmanager.repository.SiteRepository;
import pl.kamilkoszarny.iotmanager.repository.UserRepository;
import pl.kamilkoszarny.iotmanager.security.AuthoritiesConstants;
import pl.kamilkoszarny.iotmanager.service.SiteService;
import pl.kamilkoszarny.iotmanager.service.dto.SiteDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.SiteMapper;

import javax.persistence.EntityManager;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SiteResource} REST controller.
 */
@SpringBootTest(classes = IotmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SiteResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_NO = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NO = "BBBBBBBBBB";

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private SiteService siteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteMockMvc;

    private Site site;
    private Site currentUserSite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Site createEntity(EntityManager em) {
        Site site = new Site()
            .name(DEFAULT_NAME)
            .city(DEFAULT_CITY)
            .street(DEFAULT_STREET)
            .streetNo(DEFAULT_STREET_NO);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        site.setUser(user);
        return site;
    }
    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public Site createEntityForCurrentUser() {
        Site site = new Site()
            .name(DEFAULT_NAME)
            .city(DEFAULT_CITY)
            .street(DEFAULT_STREET)
            .streetNo(DEFAULT_STREET_NO);
        // Add required entity
        User currentUser = userRepository.getOne(UserResourceIT.CURRENT_USER_ID);
        site.setUser(currentUser);
        return site;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Site createUpdatedEntity(EntityManager em) {
        Site site = new Site()
            .name(UPDATED_NAME)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .streetNo(UPDATED_STREET_NO);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        site.setUser(user);
        return site;
    }

    @BeforeEach
    public void initTest() {
        site = createEntity(em);
        currentUserSite = createEntityForCurrentUser();
    }

    @Test
    @Transactional
    public void createSite() throws Exception {
        int databaseSizeBeforeCreate = siteRepository.findAll().size();
        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);
        siteDTO.setUserId(UserResourceIT.CURRENT_USER_ID);

        restSiteMockMvc.perform(post("/api/sites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isCreated());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeCreate + 1);
        Site testSite = siteList.get(siteList.size() - 1);
        assertThat(testSite.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSite.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testSite.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testSite.getStreetNo()).isEqualTo(DEFAULT_STREET_NO);
        assertThat(testSite.getUser().getId()).isEqualTo(UserResourceIT.CURRENT_USER_ID);
    }

    @Test
    @Transactional
    public void createSiteWithNoUserIdThenCurrentUserIdUsed() throws Exception {
        int databaseSizeBeforeCreate = siteRepository.findAll().size();
        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);
        siteDTO.setUserId(null);
        restSiteMockMvc.perform(post("/api/sites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isCreated());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeCreate + 1);
        Site testSite = siteList.get(siteList.size() - 1);
        assertThat(testSite.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSite.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testSite.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testSite.getStreetNo()).isEqualTo(DEFAULT_STREET_NO);
        assertThat(testSite.getUser().getId()).isEqualTo(UserResourceIT.CURRENT_USER_ID);
    }

    @Test
    @Transactional
    public void createSiteWithOtherUserIdThenNotYourEntityError() throws Exception {
        SiteDTO siteDTO = siteMapper.toDto(site);
        restSiteMockMvc.perform(post("/api/sites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("message").value("error.notYourEntity"));
    }


    @Test
    @Transactional
    public void createSiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siteRepository.findAll().size();

        // Create the Site with an existing ID
        site.setId(1L);
        SiteDTO siteDTO = siteMapper.toDto(site);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteMockMvc.perform(post("/api/sites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteRepository.findAll().size();
        // set the field null
        site.setName(null);

        // Create the Site, which fails.
        SiteDTO siteDTO = siteMapper.toDto(site);


        restSiteMockMvc.perform(post("/api/sites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteRepository.findAll().size();
        // set the field null
        site.setCity(null);

        // Create the Site, which fails.
        SiteDTO siteDTO = siteMapper.toDto(site);


        restSiteMockMvc.perform(post("/api/sites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStreetNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteRepository.findAll().size();
        // set the field null
        site.setStreetNo(null);

        // Create the Site, which fails.
        SiteDTO siteDTO = siteMapper.toDto(site);


        restSiteMockMvc.perform(post("/api/sites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSitesAsNoAdminThenForbidden() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get all the siteList
        restSiteMockMvc.perform(get("/api/sites?sort=id,desc"))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    @Sql("/config/liquibase/fake-data/sqlTestInserts/site.sql")
    public void getAllSites() throws Exception {
        // Database initialized by sql above

        // Reading data directly from csv - it will be the same as in database
        CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/config/liquibase/fake-data/site.csv"), ';');
        final List<String[]> csvData = csvReader.readAll();
        csvData.remove(0); //remove header

        // Get all the siteList
        final ResultActions result = restSiteMockMvc.perform(get("/api/sites?sort=id,asc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        TestUtil.compareWithRawData(result, csvData, "id", "name", "city", "street", "streetNo", "userId");
    }

    @Test
    @Transactional
    @Sql("/config/liquibase/fake-data/sqlTestInserts/site.sql")
    public void getAllUserSites() throws Exception {
        // Database initialized by sql above

        // Reading data directly from csv - it will be the same as in database
        final List<String[]> csvDataForCurrentUser = currentUserSitesCsv();

        // Get all the siteList
        final ResultActions result = restSiteMockMvc.perform(get("/api/sites/user?sort=id,asc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        TestUtil.compareWithRawData(result, csvDataForCurrentUser, "id", "name", "city", "street", "streetNo", "userId");
    }

    @NotNull
    public static List<String[]> currentUserSitesCsv() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/config/liquibase/fake-data/site.csv"), ';');
        final List<String[]> csvData = csvReader.readAll();
        csvData.remove(0); //remove header
        csvData.removeIf(strings -> !strings[5].equals(Long.toString(UserResourceIT.CURRENT_USER_ID))); //remove not current user sites
        return csvData;
    }

    @Test
    @Transactional
    public void getSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(currentUserSite);

        // Get the site
        restSiteMockMvc.perform(get("/api/sites/{id}", currentUserSite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currentUserSite.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.streetNo").value(DEFAULT_STREET_NO));
    }

    @Test
    @Transactional
    public void getNonExistingSite() throws Exception {
        // Get the site
        restSiteMockMvc.perform(get("/api/sites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void getSiteOfOtherUserThenNotYourEntityException() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        // Get the site
        restSiteMockMvc.perform(get("/api/sites/{id}", site.getId()))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("message").value("error.notYourEntity"));
    }

    @Test
    @Transactional
    public void updateSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(currentUserSite);

        int databaseSizeBeforeUpdate = siteRepository.findAll().size();

        // Update the site
        Site updatedSite = siteRepository.findById(currentUserSite.getId()).get();
        // Disconnect from session so that the updates on updatedSite are not directly saved in db
        em.detach(updatedSite);
        updatedSite
            .name(UPDATED_NAME)
            .city(UPDATED_CITY)
            .street(UPDATED_STREET)
            .streetNo(UPDATED_STREET_NO);
        SiteDTO siteDTO = siteMapper.toDto(updatedSite);

        restSiteMockMvc.perform(put("/api/sites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isOk());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
        Site testSite = siteList.get(siteList.size() - 1);
        assertThat(testSite.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSite.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSite.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testSite.getStreetNo()).isEqualTo(UPDATED_STREET_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingSite() throws Exception {
        int databaseSizeBeforeUpdate = siteRepository.findAll().size();

        // Create the Site
        SiteDTO siteDTO = siteMapper.toDto(site);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteMockMvc.perform(put("/api/sites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(siteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSite() throws Exception {
        // Initialize the database
        siteRepository.saveAndFlush(site);

        int databaseSizeBeforeDelete = siteRepository.findAll().size();

        // Delete the site
        restSiteMockMvc.perform(delete("/api/sites/{id}", site.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Site> siteList = siteRepository.findAll();
        assertThat(siteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
