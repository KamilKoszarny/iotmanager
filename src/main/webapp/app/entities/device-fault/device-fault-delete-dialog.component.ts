import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeviceFault } from 'app/shared/model/device-fault.model';
import { DeviceFaultService } from './device-fault.service';

@Component({
  templateUrl: './device-fault-delete-dialog.component.html',
})
export class DeviceFaultDeleteDialogComponent {
  deviceFault?: IDeviceFault;

  constructor(
    protected deviceFaultService: DeviceFaultService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deviceFaultService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deviceFaultListModification');
      this.activeModal.close();
    });
  }
}
