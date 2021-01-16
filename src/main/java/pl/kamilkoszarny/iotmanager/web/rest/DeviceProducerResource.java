package pl.kamilkoszarny.iotmanager.web.rest;

import pl.kamilkoszarny.iotmanager.domain.DeviceProducer;
import pl.kamilkoszarny.iotmanager.repository.DeviceProducerRepository;
import pl.kamilkoszarny.iotmanager.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.kamilkoszarny.iotmanager.domain.DeviceProducer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeviceProducerResource {

    private final Logger log = LoggerFactory.getLogger(DeviceProducerResource.class);

    private static final String ENTITY_NAME = "deviceProducer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceProducerRepository deviceProducerRepository;

    public DeviceProducerResource(DeviceProducerRepository deviceProducerRepository) {
        this.deviceProducerRepository = deviceProducerRepository;
    }

    /**
     * {@code POST  /device-producers} : Create a new deviceProducer.
     *
     * @param deviceProducer the deviceProducer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceProducer, or with status {@code 400 (Bad Request)} if the deviceProducer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-producers")
    public ResponseEntity<DeviceProducer> createDeviceProducer(@RequestBody DeviceProducer deviceProducer) throws URISyntaxException {
        log.debug("REST request to save DeviceProducer : {}", deviceProducer);
        if (deviceProducer.getId() != null) {
            throw new BadRequestAlertException("A new deviceProducer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceProducer result = deviceProducerRepository.save(deviceProducer);
        return ResponseEntity.created(new URI("/api/device-producers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-producers} : Updates an existing deviceProducer.
     *
     * @param deviceProducer the deviceProducer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceProducer,
     * or with status {@code 400 (Bad Request)} if the deviceProducer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceProducer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-producers")
    public ResponseEntity<DeviceProducer> updateDeviceProducer(@RequestBody DeviceProducer deviceProducer) throws URISyntaxException {
        log.debug("REST request to update DeviceProducer : {}", deviceProducer);
        if (deviceProducer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceProducer result = deviceProducerRepository.save(deviceProducer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceProducer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /device-producers} : get all the deviceProducers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceProducers in body.
     */
    @GetMapping("/device-producers")
    public List<DeviceProducer> getAllDeviceProducers() {
        log.debug("REST request to get all DeviceProducers");
        return deviceProducerRepository.findAll();
    }

    /**
     * {@code GET  /device-producers/:id} : get the "id" deviceProducer.
     *
     * @param id the id of the deviceProducer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceProducer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-producers/{id}")
    public ResponseEntity<DeviceProducer> getDeviceProducer(@PathVariable Long id) {
        log.debug("REST request to get DeviceProducer : {}", id);
        Optional<DeviceProducer> deviceProducer = deviceProducerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deviceProducer);
    }

    /**
     * {@code DELETE  /device-producers/:id} : delete the "id" deviceProducer.
     *
     * @param id the id of the deviceProducer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-producers/{id}")
    public ResponseEntity<Void> deleteDeviceProducer(@PathVariable Long id) {
        log.debug("REST request to delete DeviceProducer : {}", id);
        deviceProducerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
