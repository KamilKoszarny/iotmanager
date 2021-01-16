import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeviceProducer } from 'app/shared/model/device-producer.model';
import { DeviceProducerService } from './device-producer.service';

@Component({
  templateUrl: './device-producer-delete-dialog.component.html',
})
export class DeviceProducerDeleteDialogComponent {
  deviceProducer?: IDeviceProducer;

  constructor(
    protected deviceProducerService: DeviceProducerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deviceProducerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deviceProducerListModification');
      this.activeModal.close();
    });
  }
}
