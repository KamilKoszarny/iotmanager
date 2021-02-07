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
import pl.kamilkoszarny.iotmanager.service.DeviceFaultService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceFaultDTO;
import pl.kamilkoszarny.iotmanager.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.kamilkoszarny.iotmanager.domain.DeviceFault}.
 */
@RestController
@RequestMapping("/api")
public class DeviceFaultResource {

    private final Logger log = LoggerFactory.getLogger(DeviceFaultResource.class);

    private static final String ENTITY_NAME = "deviceFault";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceFaultService deviceFaultService;

    public DeviceFaultResource(DeviceFaultService deviceFaultService) {
        this.deviceFaultService = deviceFaultService;
    }

    /**
     * {@code POST  /device-faults} : Create a new deviceFault.
     *
     * @param deviceFaultDTO the deviceFaultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceFaultDTO, or with status {@code 400 (Bad Request)} if the deviceFault has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-faults")
    public ResponseEntity<DeviceFaultDTO> createDeviceFault(@Valid @RequestBody DeviceFaultDTO deviceFaultDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceFault : {}", deviceFaultDTO);
        if (deviceFaultDTO.getId() != null) {
            throw new BadRequestAlertException("A new deviceFault cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceFaultDTO result = deviceFaultService.save(deviceFaultDTO);
        return ResponseEntity.created(new URI("/api/device-faults/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-faults} : Updates an existing deviceFault.
     *
     * @param deviceFaultDTO the deviceFaultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceFaultDTO,
     * or with status {@code 400 (Bad Request)} if the deviceFaultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceFaultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-faults")
    public ResponseEntity<DeviceFaultDTO> updateDeviceFault(@Valid @RequestBody DeviceFaultDTO deviceFaultDTO) throws URISyntaxException {
        log.debug("REST request to update DeviceFault : {}", deviceFaultDTO);
        if (deviceFaultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceFaultDTO result = deviceFaultService.save(deviceFaultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceFaultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /device-faults} : get all the deviceFaults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceFaults in body.
     */
    @GetMapping("/device-faults")
    public ResponseEntity<List<DeviceFaultDTO>> getAllDeviceFaults(Pageable pageable) {
        log.debug("REST request to get a page of DeviceFaults");
        Page<DeviceFaultDTO> page = deviceFaultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /device-faults/:id} : get the "id" deviceFault.
     *
     * @param id the id of the deviceFaultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceFaultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-faults/{id}")
    public ResponseEntity<DeviceFaultDTO> getDeviceFault(@PathVariable Long id) {
        log.debug("REST request to get DeviceFault : {}", id);
        Optional<DeviceFaultDTO> deviceFaultDTO = deviceFaultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceFaultDTO);
    }

    /**
     * {@code DELETE  /device-faults/:id} : delete the "id" deviceFault.
     *
     * @param id the id of the deviceFaultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-faults/{id}")
    public ResponseEntity<Void> deleteDeviceFault(@PathVariable Long id) {
        log.debug("REST request to delete DeviceFault : {}", id);
        deviceFaultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
