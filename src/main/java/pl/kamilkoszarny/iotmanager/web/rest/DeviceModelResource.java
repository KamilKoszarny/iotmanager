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
import pl.kamilkoszarny.iotmanager.service.DeviceModelService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceModelDTO;
import pl.kamilkoszarny.iotmanager.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.kamilkoszarny.iotmanager.domain.DeviceModel}.
 */
@RestController
@RequestMapping("/api")
public class DeviceModelResource {

    private final Logger log = LoggerFactory.getLogger(DeviceModelResource.class);

    private static final String ENTITY_NAME = "deviceModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceModelService deviceModelService;

    public DeviceModelResource(DeviceModelService deviceModelService) {
        this.deviceModelService = deviceModelService;
    }

    /**
     * {@code POST  /device-models} : Create a new deviceModel.
     *
     * @param deviceModelDTO the deviceModelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceModelDTO, or with status {@code 400 (Bad Request)} if the deviceModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-models")
    public ResponseEntity<DeviceModelDTO> createDeviceModel(@Valid @RequestBody DeviceModelDTO deviceModelDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceModel : {}", deviceModelDTO);
        if (deviceModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new deviceModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceModelDTO result = deviceModelService.save(deviceModelDTO);
        return ResponseEntity.created(new URI("/api/device-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-models} : Updates an existing deviceModel.
     *
     * @param deviceModelDTO the deviceModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceModelDTO,
     * or with status {@code 400 (Bad Request)} if the deviceModelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-models")
    public ResponseEntity<DeviceModelDTO> updateDeviceModel(@Valid @RequestBody DeviceModelDTO deviceModelDTO) throws URISyntaxException {
        log.debug("REST request to update DeviceModel : {}", deviceModelDTO);
        if (deviceModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceModelDTO result = deviceModelService.save(deviceModelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /device-models} : get all the deviceModels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceModels in body.
     */
    @GetMapping("/device-models")
    public ResponseEntity<List<DeviceModelDTO>> getAllDeviceModels(Pageable pageable) {
        log.debug("REST request to get a page of DeviceModels");
        Page<DeviceModelDTO> page = deviceModelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /device-models/:id} : get the "id" deviceModel.
     *
     * @param id the id of the deviceModelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceModelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-models/{id}")
    public ResponseEntity<DeviceModelDTO> getDeviceModel(@PathVariable Long id) {
        log.debug("REST request to get DeviceModel : {}", id);
        Optional<DeviceModelDTO> deviceModelDTO = deviceModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceModelDTO);
    }

    /**
     * {@code DELETE  /device-models/:id} : delete the "id" deviceModel.
     *
     * @param id the id of the deviceModelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-models/{id}")
    public ResponseEntity<Void> deleteDeviceModel(@PathVariable Long id) {
        log.debug("REST request to delete DeviceModel : {}", id);
        deviceModelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
