package pl.kamilkoszarny.iotmanager.web.rest;

import liquibase.util.csv.CSVReader;
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
import pl.kamilkoszarny.iotmanager.domain.Device;
import pl.kamilkoszarny.iotmanager.domain.DeviceModel;
import pl.kamilkoszarny.iotmanager.domain.Site;
import pl.kamilkoszarny.iotmanager.domain.User;
import pl.kamilkoszarny.iotmanager.repository.DeviceRepository;
import pl.kamilkoszarny.iotmanager.repository.UserRepository;
import pl.kamilkoszarny.iotmanager.security.AuthoritiesConstants;
import pl.kamilkoszarny.iotmanager.service.DeviceService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceMapper;

import javax.persistence.EntityManager;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DeviceResource} REST controller.
 */
@SpringBootTest(classes = IotmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeviceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NO = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NO = "BBBBBBBBBB";

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceMockMvc;

    private Device device;
    private Device currentUserDevice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Device createEntity(EntityManager em) {
        Device device = new Device()
            .name(DEFAULT_NAME)
            .serialNo(DEFAULT_SERIAL_NO);
        // Add required entity
        DeviceModel deviceModel;
        if (TestUtil.findAll(em, DeviceModel.class).isEmpty()) {
            deviceModel = DeviceModelResourceIT.createEntity(em);
            em.persist(deviceModel);
            em.flush();
        } else {
            deviceModel = TestUtil.findAll(em, DeviceModel.class).get(0);
        }
        device.setModel(deviceModel);
        // Add required entity
        Site site;
        if (TestUtil.findAll(em, Site.class).isEmpty()) {
            site = SiteResourceIT.createEntity(em);
            em.persist(site);
            em.flush();
        } else {
            site = TestUtil.findAll(em, Site.class).get(0);
        }
        device.setSite(site);
        return device;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Device createUpdatedEntity(EntityManager em) {
        Device device = new Device()
            .name(UPDATED_NAME)
            .serialNo(UPDATED_SERIAL_NO);
        // Add required entity
        DeviceModel deviceModel;
        if (TestUtil.findAll(em, DeviceModel.class).isEmpty()) {
            deviceModel = DeviceModelResourceIT.createUpdatedEntity(em);
            em.persist(deviceModel);
            em.flush();
        } else {
            deviceModel = TestUtil.findAll(em, DeviceModel.class).get(0);
        }
        device.setModel(deviceModel);
        // Add required entity
        Site site;
        if (TestUtil.findAll(em, Site.class).isEmpty()) {
            site = SiteResourceIT.createUpdatedEntity(em);
            em.persist(site);
            em.flush();
        } else {
            site = TestUtil.findAll(em, Site.class).get(0);
        }
        device.setSite(site);
        return device;
    }
    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public Device createEntityForCurrentUser(EntityManager em) {
        Device device = new Device()
            .name(DEFAULT_NAME)
            .serialNo(DEFAULT_SERIAL_NO);
        // Add required entity
        DeviceModel deviceModel;
        if (TestUtil.findAll(em, DeviceModel.class).isEmpty()) {
            deviceModel = DeviceModelResourceIT.createEntity(em);
            em.persist(deviceModel);
            em.flush();
        } else {
            deviceModel = TestUtil.findAll(em, DeviceModel.class).get(0);
        }
        device.setModel(deviceModel);
        // Add required entity
        Site site = SiteResourceIT.createEntity(em);
        User currentUser = userRepository.getOne(UserResourceIT.CURRENT_USER_ID);
        site.setUser(currentUser);
        em.persist(site);
        em.flush();

        device.setSite(site);
        return device;
    }

    @BeforeEach
    public void initTest() {
        device = createEntity(em);
        currentUserDevice = createEntityForCurrentUser(em);
    }

    @Test
    @Transactional
    public void createDevice() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();
        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);
        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate + 1);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDevice.getSerialNo()).isEqualTo(DEFAULT_SERIAL_NO);
    }

    @Test
    @Transactional
    public void createDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device with an existing ID
        device.setId(1L);
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceRepository.findAll().size();
        // set the field null
        device.setName(null);

        // Create the Device, which fails.
        DeviceDTO deviceDTO = deviceMapper.toDto(device);


        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSerialNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceRepository.findAll().size();
        // set the field null
        device.setSerialNo(null);

        // Create the Device, which fails.
        DeviceDTO deviceDTO = deviceMapper.toDto(device);


        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDevicesAsNoAdminThenForbidden() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList
        restDeviceMockMvc.perform(get("/api/devices?sort=id,desc"))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    @Sql({"/config/liquibase/fake-data/sqlTestInserts/device_producer.sql",
        "/config/liquibase/fake-data/sqlTestInserts/device_type.sql",
        "/config/liquibase/fake-data/sqlTestInserts/device_model.sql",
        "/config/liquibase/fake-data/sqlTestInserts/site.sql",
        "/config/liquibase/fake-data/sqlTestInserts/device.sql",})
    public void getAllDevices() throws Exception {
        // Database initialized by sql above

        // Reading data directly from csv - it will be the same as in database
        CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/config/liquibase/fake-data/device.csv"), ';');
        final List<String[]> csvData = csvReader.readAll();
        csvData.remove(0); //remove header

        // Get all the deviceList
        final ResultActions result = restDeviceMockMvc.perform(get("/api/devices?sort=id,asc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        TestUtil.compareWithRawData(result, csvData, "id", "name", "serialNo", "modelId", "siteId");
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    @Sql({"/config/liquibase/fake-data/sqlTestInserts/device_producer.sql",
        "/config/liquibase/fake-data/sqlTestInserts/device_type.sql",
        "/config/liquibase/fake-data/sqlTestInserts/device_model.sql",
        "/config/liquibase/fake-data/sqlTestInserts/site.sql",
        "/config/liquibase/fake-data/sqlTestInserts/device.sql",})
    public void getAllUserDevices() throws Exception {
        // Database initialized by sql above

        // Reading data directly from csv - it will be the same as in database
        CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/config/liquibase/fake-data/device.csv"), ';');
        final List<String[]> csvDataForCurrentUser = csvReader.readAll();
        csvDataForCurrentUser.remove(0); //remove header
        List<String[]> currentUserSites = SiteResourceIT.currentUserSites();
        List<String> currentUserSitesIds = currentUserSites.stream().map(siteArray -> siteArray[0]).collect(Collectors.toList());
        csvDataForCurrentUser.removeIf(strings -> !currentUserSitesIds.contains(strings[4])); //remove devices not in current user sites


        // Get all the deviceList
        final ResultActions result = restDeviceMockMvc.perform(get("/api/devices/user?sort=id,asc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        TestUtil.compareWithRawData(result, csvDataForCurrentUser, "id", "name", "serialNo", "modelId", "siteId");
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    @Sql({"/config/liquibase/fake-data/sqlTestInserts/device_producer.sql",
        "/config/liquibase/fake-data/sqlTestInserts/device_type.sql",
        "/config/liquibase/fake-data/sqlTestInserts/device_model.sql",
        "/config/liquibase/fake-data/sqlTestInserts/site.sql",
        "/config/liquibase/fake-data/sqlTestInserts/device.sql",})
    public void getAllDevicesBySiteId() throws Exception {
        // Database initialized by sql above
        final String SITE_ID = "1";

        // Reading data directly from csv - it will be the same as in database
        CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/config/liquibase/fake-data/device.csv"), ';');
        final List<String[]> csvDataForCurrentUser = csvReader.readAll();
        csvDataForCurrentUser.remove(0); //remove header
        csvDataForCurrentUser.removeIf(strings -> !strings[4].equals(SITE_ID)); //remove devices not in site


        // Get all the deviceList
        final ResultActions result = restDeviceMockMvc.perform(get("/api/devices/site/" + SITE_ID + "?sort=id,asc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        TestUtil.compareWithRawData(result, csvDataForCurrentUser, "id", "name", "serialNo", "modelId", "siteId");
    }

    @Test
    @Transactional
    public void getDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(currentUserDevice);

        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", currentUserDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currentUserDevice.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.serialNo").value(DEFAULT_SERIAL_NO));
    }
    @Test
    @Transactional
    public void getNonExistingDevice() throws Exception {
        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void getDeviceOfOtherUserThenNotYourEntityException() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", device.getId()))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("message").value("error.notYourEntity"));
    }


    @Test
    @Transactional
    public void updateDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device
        Device updatedDevice = deviceRepository.findById(device.getId()).get();
        // Disconnect from session so that the updates on updatedDevice are not directly saved in db
        em.detach(updatedDevice);
        updatedDevice
            .name(UPDATED_NAME)
            .serialNo(UPDATED_SERIAL_NO);
        DeviceDTO deviceDTO = deviceMapper.toDto(updatedDevice);

        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDevice.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeDelete = deviceRepository.findAll().size();

        // Delete the device
        restDeviceMockMvc.perform(delete("/api/devices/{id}", device.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
