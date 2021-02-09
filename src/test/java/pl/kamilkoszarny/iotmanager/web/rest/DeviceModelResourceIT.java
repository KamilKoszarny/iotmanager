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
import pl.kamilkoszarny.iotmanager.domain.DeviceModel;
import pl.kamilkoszarny.iotmanager.domain.DeviceProducer;
import pl.kamilkoszarny.iotmanager.domain.DeviceType;
import pl.kamilkoszarny.iotmanager.repository.DeviceModelRepository;
import pl.kamilkoszarny.iotmanager.security.AuthoritiesConstants;
import pl.kamilkoszarny.iotmanager.service.DeviceModelService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceModelDTO;
import pl.kamilkoszarny.iotmanager.service.mapper.DeviceModelMapper;

import javax.persistence.EntityManager;
import java.io.FileReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DeviceModelResource} REST controller.
 */
@SpringBootTest(classes = IotmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeviceModelResourceIT {

    private final String baseUrl = "/api/device-models";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DeviceModelRepository deviceModelRepository;

    @Autowired
    private DeviceModelMapper deviceModelMapper;

    @Autowired
    private DeviceModelService deviceModelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceModelMockMvc;

    private DeviceModel deviceModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceModel createEntity(EntityManager em) {
        DeviceModel deviceModel = new DeviceModel()
            .name(DEFAULT_NAME);
        // Add required entity
        DeviceProducer deviceProducer;
        if (TestUtil.findAll(em, DeviceProducer.class).isEmpty()) {
            deviceProducer = DeviceProducerResourceIT.createEntity(em);
            em.persist(deviceProducer);
            em.flush();
        } else {
            deviceProducer = TestUtil.findAll(em, DeviceProducer.class).get(0);
        }
        deviceModel.setProducer(deviceProducer);
        // Add required entity
        DeviceType deviceType;
        if (TestUtil.findAll(em, DeviceType.class).isEmpty()) {
            deviceType = DeviceTypeResourceIT.createEntity(em);
            em.persist(deviceType);
            em.flush();
        } else {
            deviceType = TestUtil.findAll(em, DeviceType.class).get(0);
        }
        deviceModel.setType(deviceType);
        return deviceModel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceModel createUpdatedEntity(EntityManager em) {
        DeviceModel deviceModel = new DeviceModel()
            .name(UPDATED_NAME);
        // Add required entity
        DeviceProducer deviceProducer;
        if (TestUtil.findAll(em, DeviceProducer.class).isEmpty()) {
            deviceProducer = DeviceProducerResourceIT.createUpdatedEntity(em);
            em.persist(deviceProducer);
            em.flush();
        } else {
            deviceProducer = TestUtil.findAll(em, DeviceProducer.class).get(0);
        }
        deviceModel.setProducer(deviceProducer);
        // Add required entity
        DeviceType deviceType;
        if (TestUtil.findAll(em, DeviceType.class).isEmpty()) {
            deviceType = DeviceTypeResourceIT.createUpdatedEntity(em);
            em.persist(deviceType);
            em.flush();
        } else {
            deviceType = TestUtil.findAll(em, DeviceType.class).get(0);
        }
        deviceModel.setType(deviceType);
        return deviceModel;
    }

    @BeforeEach
    public void initTest() {
        deviceModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceModelAsNoAdminThenForbidden() throws Exception {
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);
        restDeviceModelMockMvc.perform(post("/api/device-models")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    public void createDeviceModel() throws Exception {
        int databaseSizeBeforeCreate = deviceModelRepository.findAll().size();
        // Create the DeviceModel
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);
        restDeviceModelMockMvc.perform(post(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceModel testDeviceModel = deviceModelList.get(deviceModelList.size() - 1);
        assertThat(testDeviceModel.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    public void createDeviceModelWithExistingIdThenBadRequest() throws Exception {
        int databaseSizeBeforeCreate = deviceModelRepository.findAll().size();

        // Create the DeviceModel with an existing ID
        deviceModel.setId(1L);
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceModelMockMvc.perform(post(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    public void checkNameIsRequiredThenBadRequest() throws Exception {
        int databaseSizeBeforeTest = deviceModelRepository.findAll().size();
        // set the field null
        deviceModel.setName(null);

        // Create the DeviceModel, which fails.
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);


        restDeviceModelMockMvc.perform(post(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    @Sql({"/config/liquibase/fake-data/sql-for-tests/device_producer.sql",
            "/config/liquibase/fake-data/sql-for-tests/device_type.sql",
            "/config/liquibase/fake-data/sql-for-tests/device_model.sql"})
    public void getAllDeviceModels() throws Exception {
        // Database initialized by sql above

        // Reading data directly from csv - it will be the same as in database
        CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/config/liquibase/fake-data/device_model.csv"), ';');
        final List<String[]> csvData = csvReader.readAll();
        csvData.remove(0); //remove header

        // Get all the deviceModelList
        final ResultActions result = restDeviceModelMockMvc.perform(get(baseUrl + "?sort=id,asc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        TestUtil.compareWithRawData(result, csvData, "id", "name", "producerId", "typeId");
    }

    @Test
    @Transactional
    @Sql({"/config/liquibase/fake-data/sql-for-tests/device_producer.sql",
            "/config/liquibase/fake-data/sql-for-tests/device_type.sql",
            "/config/liquibase/fake-data/sql-for-tests/device_model.sql"})
    public void getAllDeviceModelsByTypeAndProducer() throws Exception {
        // Database initialized by sql above
        final String PRODUCER_ID = "2";
        final String TYPE_ID = "1";

        // Reading data directly from csv - it will be the same as in database
        CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/config/liquibase/fake-data/device_model.csv"), ';');
        final List<String[]> filteredCsvData = csvReader.readAll();
        filteredCsvData.remove(0); //remove header
        filteredCsvData.removeIf(strings -> !strings[2].equals(PRODUCER_ID) || !strings[3].equals(TYPE_ID));

        // Get all the deviceModelList
        final ResultActions result = restDeviceModelMockMvc.perform(get(baseUrl + "/filter?sort=id,asc")
            .param("typeId", TYPE_ID)
            .param("producerId", PRODUCER_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        TestUtil.compareWithRawData(result, filteredCsvData, "id", "name", "producerId", "typeId");
    }

    @Test
    @Transactional
    @Sql({"/config/liquibase/fake-data/sql-for-tests/device_producer.sql",
            "/config/liquibase/fake-data/sql-for-tests/device_type.sql",
            "/config/liquibase/fake-data/sql-for-tests/device_model.sql"})
    public void getAllDeviceModelsByType() throws Exception {
        // Database initialized by sql above
        final String TYPE_ID = "1";

        // Reading data directly from csv - it will be the same as in database
        CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/config/liquibase/fake-data/device_model.csv"), ';');
        final List<String[]> filteredCsvData = csvReader.readAll();
        filteredCsvData.remove(0); //remove header
        filteredCsvData.removeIf(strings -> !strings[3].equals(TYPE_ID));

        // Get all the deviceModelList
        final ResultActions result = restDeviceModelMockMvc.perform(get(baseUrl + "/filter?sort=id,asc")
            .param("typeId", TYPE_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        TestUtil.compareWithRawData(result, filteredCsvData, "id", "name", "producerId", "typeId");
    }

    @Test
    @Transactional
    @Sql({"/config/liquibase/fake-data/sql-for-tests/device_producer.sql",
            "/config/liquibase/fake-data/sql-for-tests/device_type.sql",
            "/config/liquibase/fake-data/sql-for-tests/device_model.sql"})
    public void getAllDeviceModelsByProducer() throws Exception {
        // Database initialized by sql above
        final String PRODUCER_ID = "2";

        // Reading data directly from csv - it will be the same as in database
        CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/config/liquibase/fake-data/device_model.csv"), ';');
        final List<String[]> filteredCsvData = csvReader.readAll();
        filteredCsvData.remove(0); //remove header
        filteredCsvData.removeIf(strings -> !strings[2].equals(PRODUCER_ID));

        // Get all the deviceModelList
        final ResultActions result = restDeviceModelMockMvc.perform(get(baseUrl + "/filter?sort=id,asc")
            .param("producerId", PRODUCER_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        TestUtil.compareWithRawData(result, filteredCsvData, "id", "name", "producerId", "typeId");
    }

    @Test
    @Transactional
    @Sql({"/config/liquibase/fake-data/sql-for-tests/device_producer.sql",
            "/config/liquibase/fake-data/sql-for-tests/device_type.sql",
            "/config/liquibase/fake-data/sql-for-tests/device_model.sql"})
    public void getAllDeviceWithEmptyFilter() throws Exception {
        // Database initialized by sql above

        // Reading data directly from csv - it will be the same as in database
        CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/config/liquibase/fake-data/device_model.csv"), ';');
        final List<String[]> csvData = csvReader.readAll();
        csvData.remove(0); //remove header

        // Get all the deviceModelList
        final ResultActions result = restDeviceModelMockMvc.perform(get(baseUrl + "/filter?sort=id,asc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        TestUtil.compareWithRawData(result, csvData, "id", "name", "producerId", "typeId");
    }

    @Test
    @Transactional
    public void getDeviceModel() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get the deviceModel
        restDeviceModelMockMvc.perform(get(baseUrl + "/{id}", deviceModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceModel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingDeviceModel() throws Exception {
        // Get the deviceModel
        restDeviceModelMockMvc.perform(get(baseUrl + "/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceModelAsNoAdminThenForbidden() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        int databaseSizeBeforeUpdate = deviceModelRepository.findAll().size();

        // Update the deviceModel
        DeviceModel updatedDeviceModel = deviceModelRepository.findById(deviceModel.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceModel are not directly saved in db
        em.detach(updatedDeviceModel);
        updatedDeviceModel
            .name(UPDATED_NAME);
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(updatedDeviceModel);

        restDeviceModelMockMvc.perform(put(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    public void updateDeviceModel() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        int databaseSizeBeforeUpdate = deviceModelRepository.findAll().size();

        // Update the deviceModel
        DeviceModel updatedDeviceModel = deviceModelRepository.findById(deviceModel.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceModel are not directly saved in db
        em.detach(updatedDeviceModel);
        updatedDeviceModel
            .name(UPDATED_NAME);
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(updatedDeviceModel);

        restDeviceModelMockMvc.perform(put(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeUpdate);
        DeviceModel testDeviceModel = deviceModelList.get(deviceModelList.size() - 1);
        assertThat(testDeviceModel.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    public void updateNonExistingDeviceModelThenBadRequest() throws Exception {
        int databaseSizeBeforeUpdate = deviceModelRepository.findAll().size();

        // Create the DeviceModel
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceModelMockMvc.perform(put(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeviceModelAsNoAdminThenForbidden() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Delete the deviceModel
        restDeviceModelMockMvc.perform(delete(baseUrl + "/{id}", deviceModel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    public void deleteDeviceModel() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        int databaseSizeBeforeDelete = deviceModelRepository.findAll().size();

        // Delete the deviceModel
        restDeviceModelMockMvc.perform(delete(baseUrl + "/{id}", deviceModel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
