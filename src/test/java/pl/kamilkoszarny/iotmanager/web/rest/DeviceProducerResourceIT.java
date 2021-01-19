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
import pl.kamilkoszarny.iotmanager.domain.DeviceProducer;
import pl.kamilkoszarny.iotmanager.repository.DeviceProducerRepository;
import pl.kamilkoszarny.iotmanager.service.DeviceProducerService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceProducerDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceProducerMapper;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DeviceProducerResource} REST controller.
 */
@SpringBootTest(classes = IotmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeviceProducerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DeviceProducerRepository deviceProducerRepository;

    @Autowired
    private DeviceProducerMapper deviceProducerMapper;

    @Autowired
    private DeviceProducerService deviceProducerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceProducerMockMvc;

    private DeviceProducer deviceProducer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceProducer createEntity(EntityManager em) {
        DeviceProducer deviceProducer = new DeviceProducer()
            .name(DEFAULT_NAME);
        return deviceProducer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceProducer createUpdatedEntity(EntityManager em) {
        DeviceProducer deviceProducer = new DeviceProducer()
            .name(UPDATED_NAME);
        return deviceProducer;
    }

    @BeforeEach
    public void initTest() {
        deviceProducer = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceProducer() throws Exception {
        int databaseSizeBeforeCreate = deviceProducerRepository.findAll().size();
        // Create the DeviceProducer
        DeviceProducerDTO deviceProducerDTO = deviceProducerMapper.toDto(deviceProducer);
        restDeviceProducerMockMvc.perform(post("/api/device-producers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceProducerDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceProducer in the database
        List<DeviceProducer> deviceProducerList = deviceProducerRepository.findAll();
        assertThat(deviceProducerList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceProducer testDeviceProducer = deviceProducerList.get(deviceProducerList.size() - 1);
        assertThat(testDeviceProducer.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDeviceProducerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceProducerRepository.findAll().size();

        // Create the DeviceProducer with an existing ID
        deviceProducer.setId(1L);
        DeviceProducerDTO deviceProducerDTO = deviceProducerMapper.toDto(deviceProducer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceProducerMockMvc.perform(post("/api/device-producers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceProducerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceProducer in the database
        List<DeviceProducer> deviceProducerList = deviceProducerRepository.findAll();
        assertThat(deviceProducerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceProducerRepository.findAll().size();
        // set the field null
        deviceProducer.setName(null);

        // Create the DeviceProducer, which fails.
        DeviceProducerDTO deviceProducerDTO = deviceProducerMapper.toDto(deviceProducer);


        restDeviceProducerMockMvc.perform(post("/api/device-producers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceProducerDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceProducer> deviceProducerList = deviceProducerRepository.findAll();
        assertThat(deviceProducerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeviceProducers() throws Exception {
        // Initialize the database
        deviceProducerRepository.saveAndFlush(deviceProducer);

        // Get all the deviceProducerList
        restDeviceProducerMockMvc.perform(get("/api/device-producers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceProducer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getDeviceProducer() throws Exception {
        // Initialize the database
        deviceProducerRepository.saveAndFlush(deviceProducer);

        // Get the deviceProducer
        restDeviceProducerMockMvc.perform(get("/api/device-producers/{id}", deviceProducer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceProducer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDeviceProducer() throws Exception {
        // Get the deviceProducer
        restDeviceProducerMockMvc.perform(get("/api/device-producers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceProducer() throws Exception {
        // Initialize the database
        deviceProducerRepository.saveAndFlush(deviceProducer);

        int databaseSizeBeforeUpdate = deviceProducerRepository.findAll().size();

        // Update the deviceProducer
        DeviceProducer updatedDeviceProducer = deviceProducerRepository.findById(deviceProducer.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceProducer are not directly saved in db
        em.detach(updatedDeviceProducer);
        updatedDeviceProducer
            .name(UPDATED_NAME);
        DeviceProducerDTO deviceProducerDTO = deviceProducerMapper.toDto(updatedDeviceProducer);

        restDeviceProducerMockMvc.perform(put("/api/device-producers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceProducerDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceProducer in the database
        List<DeviceProducer> deviceProducerList = deviceProducerRepository.findAll();
        assertThat(deviceProducerList).hasSize(databaseSizeBeforeUpdate);
        DeviceProducer testDeviceProducer = deviceProducerList.get(deviceProducerList.size() - 1);
        assertThat(testDeviceProducer.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceProducer() throws Exception {
        int databaseSizeBeforeUpdate = deviceProducerRepository.findAll().size();

        // Create the DeviceProducer
        DeviceProducerDTO deviceProducerDTO = deviceProducerMapper.toDto(deviceProducer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceProducerMockMvc.perform(put("/api/device-producers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceProducerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceProducer in the database
        List<DeviceProducer> deviceProducerList = deviceProducerRepository.findAll();
        assertThat(deviceProducerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeviceProducer() throws Exception {
        // Initialize the database
        deviceProducerRepository.saveAndFlush(deviceProducer);

        int databaseSizeBeforeDelete = deviceProducerRepository.findAll().size();

        // Delete the deviceProducer
        restDeviceProducerMockMvc.perform(delete("/api/device-producers/{id}", deviceProducer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceProducer> deviceProducerList = deviceProducerRepository.findAll();
        assertThat(deviceProducerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
