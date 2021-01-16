import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeviceType } from 'app/shared/model/device-type.model';
import { DeviceTypeService } from './device-type.service';

@Component({
  templateUrl: './device-type-delete-dialog.component.html',
})
export class DeviceTypeDeleteDialogComponent {
  deviceType?: IDeviceType;

  constructor(
    protected deviceTypeService: DeviceTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deviceTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deviceTypeListModification');
      this.activeModal.close();
    });
  }
}
