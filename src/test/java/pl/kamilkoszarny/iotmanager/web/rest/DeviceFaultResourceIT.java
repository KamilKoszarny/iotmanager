package pl.kamilkoszarny.iotmanager.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkoszarny.iotmanager.IotmanagerApp;
import pl.kamilkoszarny.iotmanager.domain.Device;
import pl.kamilkoszarny.iotmanager.domain.DeviceFault;
import pl.kamilkoszarny.iotmanager.repository.DeviceFaultRepository;
import pl.kamilkoszarny.iotmanager.service.DeviceFaultService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFaultDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceFaultMapper;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DeviceFaultResource} REST controller.
 */
@SpringBootTest(classes = IotmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeviceFaultResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_URGENCY = 1;
    private static final Integer UPDATED_URGENCY = 2;

    @Autowired
    private DeviceFaultRepository deviceFaultRepository;

    @Autowired
    private DeviceFaultMapper deviceFaultMapper;

    @Autowired
    private DeviceFaultService deviceFaultService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceFaultMockMvc;

    private DeviceFault deviceFault;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceFault createEntity(EntityManager em) {
        DeviceFault deviceFault = new DeviceFault()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .urgency(DEFAULT_URGENCY);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        deviceFault.setDevice(device);
        return deviceFault;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceFault createUpdatedEntity(EntityManager em) {
        DeviceFault deviceFault = new DeviceFault()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .urgency(UPDATED_URGENCY);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createUpdatedEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        deviceFault.setDevice(device);
        return deviceFault;
    }

    @BeforeEach
    public void initTest() {
        deviceFault = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceFault() throws Exception {
        int databaseSizeBeforeCreate = deviceFaultRepository.findAll().size();
        // Create the DeviceFault
        DeviceFaultDTO deviceFaultDTO = deviceFaultMapper.toDto(deviceFault);
        restDeviceFaultMockMvc.perform(post("/api/device-faults")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceFaultDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceFault in the database
        List<DeviceFault> deviceFaultList = deviceFaultRepository.findAll();
        assertThat(deviceFaultList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceFault testDeviceFault = deviceFaultList.get(deviceFaultList.size() - 1);
        assertThat(testDeviceFault.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeviceFault.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDeviceFault.getUrgency()).isEqualTo(DEFAULT_URGENCY);
    }

    @Test
    @Transactional
    public void createDeviceFaultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceFaultRepository.findAll().size();

        // Create the DeviceFault with an existing ID
        deviceFault.setId(1L);
        DeviceFaultDTO deviceFaultDTO = deviceFaultMapper.toDto(deviceFault);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceFaultMockMvc.perform(post("/api/device-faults")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceFaultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceFault in the database
        List<DeviceFault> deviceFaultList = deviceFaultRepository.findAll();
        assertThat(deviceFaultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceFaultRepository.findAll().size();
        // set the field null
        deviceFault.setName(null);

        // Create the DeviceFault, which fails.
        DeviceFaultDTO deviceFaultDTO = deviceFaultMapper.toDto(deviceFault);


        restDeviceFaultMockMvc.perform(post("/api/device-faults")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceFaultDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceFault> deviceFaultList = deviceFaultRepository.findAll();
        assertThat(deviceFaultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceFaultRepository.findAll().size();
        // set the field null
        deviceFault.setDescription(null);

        // Create the DeviceFault, which fails.
        DeviceFaultDTO deviceFaultDTO = deviceFaultMapper.toDto(deviceFault);


        restDeviceFaultMockMvc.perform(post("/api/device-faults")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceFaultDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceFault> deviceFaultList = deviceFaultRepository.findAll();
        assertThat(deviceFaultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeviceFaults() throws Exception {
        // Initialize the database
        deviceFaultRepository.saveAndFlush(deviceFault);

        // Get all the deviceFaultList
        restDeviceFaultMockMvc.perform(get("/api/device-faults?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceFault.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].urgency").value(hasItem(DEFAULT_URGENCY)));
    }

    @Test
    @Transactional
    public void getDeviceFault() throws Exception {
        // Initialize the database
        deviceFaultRepository.saveAndFlush(deviceFault);

        // Get the deviceFault
        restDeviceFaultMockMvc.perform(get("/api/device-faults/{id}", deviceFault.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceFault.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.urgency").value(DEFAULT_URGENCY));
    }
    @Test
    @Transactional
    public void getNonExistingDeviceFault() throws Exception {
        // Get the deviceFault
        restDeviceFaultMockMvc.perform(get("/api/device-faults/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceFault() throws Exception {
        // Initialize the database
        deviceFaultRepository.saveAndFlush(deviceFault);

        int databaseSizeBeforeUpdate = deviceFaultRepository.findAll().size();

        // Update the deviceFault
        DeviceFault updatedDeviceFault = deviceFaultRepository.findById(deviceFault.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceFault are not directly saved in db
        em.detach(updatedDeviceFault);
        updatedDeviceFault
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .urgency(UPDATED_URGENCY);
        DeviceFaultDTO deviceFaultDTO = deviceFaultMapper.toDto(updatedDeviceFault);

        restDeviceFaultMockMvc.perform(put("/api/device-faults")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceFaultDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceFault in the database
        List<DeviceFault> deviceFaultList = deviceFaultRepository.findAll();
        assertThat(deviceFaultList).hasSize(databaseSizeBeforeUpdate);
        DeviceFault testDeviceFault = deviceFaultList.get(deviceFaultList.size() - 1);
        assertThat(testDeviceFault.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeviceFault.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeviceFault.getUrgency()).isEqualTo(UPDATED_URGENCY);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceFault() throws Exception {
        int databaseSizeBeforeUpdate = deviceFaultRepository.findAll().size();

        // Create the DeviceFault
        DeviceFaultDTO deviceFaultDTO = deviceFaultMapper.toDto(deviceFault);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceFaultMockMvc.perform(put("/api/device-faults")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceFaultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceFault in the database
        List<DeviceFault> deviceFaultList = deviceFaultRepository.findAll();
        assertThat(deviceFaultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeviceFault() throws Exception {
        // Initialize the database
        deviceFaultRepository.saveAndFlush(deviceFault);

        int databaseSizeBeforeDelete = deviceFaultRepository.findAll().size();

        // Delete the deviceFault
        restDeviceFaultMockMvc.perform(delete("/api/device-faults/{id}", deviceFault.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceFault> deviceFaultList = deviceFaultRepository.findAll();
        assertThat(deviceFaultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
