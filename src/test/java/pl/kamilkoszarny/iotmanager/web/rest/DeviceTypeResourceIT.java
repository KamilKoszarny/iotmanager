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
import pl.kamilkoszarny.iotmanager.domain.DeviceType;
import pl.kamilkoszarny.iotmanager.domain.enumeration.DeviceCategory;
import pl.kamilkoszarny.iotmanager.repository.DeviceTypeRepository;
import pl.kamilkoszarny.iotmanager.service.DeviceTypeService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceTypeDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceTypeMapper;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link DeviceTypeResource} REST controller.
 */
@SpringBootTest(classes = IotmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeviceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final DeviceCategory DEFAULT_CATEGORY = DeviceCategory.RTV;
    private static final DeviceCategory UPDATED_CATEGORY = DeviceCategory.AGD;

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Autowired
    private DeviceTypeService deviceTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceTypeMockMvc;

    private DeviceType deviceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceType createEntity(EntityManager em) {
        DeviceType deviceType = new DeviceType()
            .name(DEFAULT_NAME)
            .category(DEFAULT_CATEGORY);
        return deviceType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceType createUpdatedEntity(EntityManager em) {
        DeviceType deviceType = new DeviceType()
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY);
        return deviceType;
    }

    @BeforeEach
    public void initTest() {
        deviceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceType() throws Exception {
        int databaseSizeBeforeCreate = deviceTypeRepository.findAll().size();
        // Create the DeviceType
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);
        restDeviceTypeMockMvc.perform(post("/api/device-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceType testDeviceType = deviceTypeList.get(deviceTypeList.size() - 1);
        assertThat(testDeviceType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeviceType.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    public void createDeviceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceTypeRepository.findAll().size();

        // Create the DeviceType with an existing ID
        deviceType.setId(1L);
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceTypeMockMvc.perform(post("/api/device-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceTypeRepository.findAll().size();
        // set the field null
        deviceType.setName(null);

        // Create the DeviceType, which fails.
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);


        restDeviceTypeMockMvc.perform(post("/api/device-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceTypeRepository.findAll().size();
        // set the field null
        deviceType.setCategory(null);

        // Create the DeviceType, which fails.
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);


        restDeviceTypeMockMvc.perform(post("/api/device-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeviceTypes() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList
        restDeviceTypeMockMvc.perform(get("/api/device-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }

    @Test
    @Transactional
    public void getDeviceType() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get the deviceType
        restDeviceTypeMockMvc.perform(get("/api/device-types/{id}", deviceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDeviceType() throws Exception {
        // Get the deviceType
        restDeviceTypeMockMvc.perform(get("/api/device-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceType() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();

        // Update the deviceType
        DeviceType updatedDeviceType = deviceTypeRepository.findById(deviceType.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceType are not directly saved in db
        em.detach(updatedDeviceType);
        updatedDeviceType
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY);
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(updatedDeviceType);

        restDeviceTypeMockMvc.perform(put("/api/device-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
        DeviceType testDeviceType = deviceTypeList.get(deviceTypeList.size() - 1);
        assertThat(testDeviceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeviceType.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceType() throws Exception {
        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();

        // Create the DeviceType
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceTypeMockMvc.perform(put("/api/device-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeviceType() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        int databaseSizeBeforeDelete = deviceTypeRepository.findAll().size();

        // Delete the deviceType
        restDeviceTypeMockMvc.perform(delete("/api/device-types/{id}", deviceType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
