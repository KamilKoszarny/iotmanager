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
import pl.kamilkoszarny.iotmanager.service.DeviceTypeService;
import pl.kamilkoszarny.iotmanager.service.dto.DeviceTypeDTO;
import pl.kamilkoszarny.iotmanager.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.kamilkoszarny.iotmanager.domain.DeviceType}.
 */
@RestController
@RequestMapping("/api")
public class DeviceTypeResource {

    private final Logger log = LoggerFactory.getLogger(DeviceTypeResource.class);

    private static final String ENTITY_NAME = "deviceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceTypeService deviceTypeService;

    public DeviceTypeResource(DeviceTypeService deviceTypeService) {
        this.deviceTypeService = deviceTypeService;
    }

    /**
     * {@code POST  /device-types} : Create a new deviceType.
     *
     * @param deviceTypeDTO the deviceTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceTypeDTO, or with status {@code 400 (Bad Request)} if the deviceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-types")
    public ResponseEntity<DeviceTypeDTO> createDeviceType(@RequestBody DeviceTypeDTO deviceTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceType : {}", deviceTypeDTO);
        if (deviceTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new deviceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceTypeDTO result = deviceTypeService.save(deviceTypeDTO);
        return ResponseEntity.created(new URI("/api/device-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-types} : Updates an existing deviceType.
     *
     * @param deviceTypeDTO the deviceTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceTypeDTO,
     * or with status {@code 400 (Bad Request)} if the deviceTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-types")
    public ResponseEntity<DeviceTypeDTO> updateDeviceType(@RequestBody DeviceTypeDTO deviceTypeDTO) throws URISyntaxException {
        log.debug("REST request to update DeviceType : {}", deviceTypeDTO);
        if (deviceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceTypeDTO result = deviceTypeService.save(deviceTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /device-types} : get all the deviceTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceTypes in body.
     */
    @GetMapping("/device-types")
    public ResponseEntity<List<DeviceTypeDTO>> getAllDeviceTypes(Pageable pageable) {
        log.debug("REST request to get a page of DeviceTypes");
        Page<DeviceTypeDTO> page = deviceTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /device-types/:id} : get the "id" deviceType.
     *
     * @param id the id of the deviceTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-types/{id}")
    public ResponseEntity<DeviceTypeDTO> getDeviceType(@PathVariable Long id) {
        log.debug("REST request to get DeviceType : {}", id);
        Optional<DeviceTypeDTO> deviceTypeDTO = deviceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceTypeDTO);
    }

    /**
     * {@code DELETE  /device-types/:id} : delete the "id" deviceType.
     *
     * @param id the id of the deviceTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-types/{id}")
    public ResponseEntity<Void> deleteDeviceType(@PathVariable Long id) {
        log.debug("REST request to delete DeviceType : {}", id);
        deviceTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
