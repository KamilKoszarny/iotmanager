import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from './device-model.service';

@Component({
  templateUrl: './device-model-delete-dialog.component.html',
})
export class DeviceModelDeleteDialogComponent {
  deviceModel?: IDeviceModel;

  constructor(
    protected deviceModelService: DeviceModelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deviceModelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deviceModelListModification');
      this.activeModal.close();
    });
  }
}
