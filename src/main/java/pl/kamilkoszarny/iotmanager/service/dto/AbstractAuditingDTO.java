package pl.kamilkoszarny.iotmanager.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AbstractAuditingDTO {

    protected String createdBy;

    protected Instant createdDate;

    protected String lastModifiedBy;

    protected Instant lastModifiedDate;
}
