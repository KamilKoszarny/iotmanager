package pl.kamilkoszarny.iotmanager.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.kamilkoszarny.iotmanager.service.DeviceProducerService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceProducerDTO;
import pl.kamilkoszarny.iotmanager.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.kamilkoszarny.iotmanager.domain.DeviceProducer}.
 */
@RestController
@RequestMapping("/api")
public class DeviceProducerResource {

    private final Logger log = LoggerFactory.getLogger(DeviceProducerResource.class);

    private static final String ENTITY_NAME = "deviceProducer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceProducerService deviceProducerService;

    public DeviceProducerResource(DeviceProducerService deviceProducerService) {
        this.deviceProducerService = deviceProducerService;
    }

    /**
     * {@code POST  /device-producers} : Create a new deviceProducer.
     *
     * @param deviceProducerDTO the deviceProducerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceProducerDTO, or with status {@code 400 (Bad Request)} if the deviceProducer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-producers")
    public ResponseEntity<DeviceProducerDTO> createDeviceProducer(@RequestBody DeviceProducerDTO deviceProducerDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceProducer : {}", deviceProducerDTO);
        if (deviceProducerDTO.getId() != null) {
            throw new BadRequestAlertException("A new deviceProducer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceProducerDTO result = deviceProducerService.save(deviceProducerDTO);
        return ResponseEntity.created(new URI("/api/device-producers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-producers} : Updates an existing deviceProducer.
     *
     * @param deviceProducerDTO the deviceProducerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceProducerDTO,
     * or with status {@code 400 (Bad Request)} if the deviceProducerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceProducerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-producers")
    public ResponseEntity<DeviceProducerDTO> updateDeviceProducer(@RequestBody DeviceProducerDTO deviceProducerDTO) throws URISyntaxException {
        log.debug("REST request to update DeviceProducer : {}", deviceProducerDTO);
        if (deviceProducerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceProducerDTO result = deviceProducerService.save(deviceProducerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceProducerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /device-producers} : get all the deviceProducers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceProducers in body.
     */
    @GetMapping("/device-producers")
    public ResponseEntity<List<DeviceProducerDTO>> getAllDeviceProducers(Pageable pageable) {
        log.debug("REST request to get a page of DeviceProducers");
        Page<DeviceProducerDTO> page = deviceProducerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /device-producers/:id} : get the "id" deviceProducer.
     *
     * @param id the id of the deviceProducerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceProducerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-producers/{id}")
    public ResponseEntity<DeviceProducerDTO> getDeviceProducer(@PathVariable Long id) {
        log.debug("REST request to get DeviceProducer : {}", id);
        Optional<DeviceProducerDTO> deviceProducerDTO = deviceProducerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceProducerDTO);
    }

    /**
     * {@code DELETE  /device-producers/:id} : delete the "id" deviceProducer.
     *
     * @param id the id of the deviceProducerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-producers/{id}")
    public ResponseEntity<Void> deleteDeviceProducer(@PathVariable Long id) {
        log.debug("REST request to delete DeviceProducer : {}", id);
        deviceProducerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
