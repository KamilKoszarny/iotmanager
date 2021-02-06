import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISite } from 'app/shared/model/site.model';
import { SiteService } from './site.service';
import { IDevice } from '../../shared/model/device.model';
import { DeviceService } from '../device/device.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  templateUrl: './site-delete-dialog.component.html',
})
export class SiteDeleteDialogComponent {
  site?: ISite;
  devices: IDevice[] = [];
  readyToShow = false;

  constructor(
    protected siteService: SiteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    protected deviceService: DeviceService
  ) {
    this.waitForSiteAndQueryModels();
  }

  waitForSiteAndQueryModels(): void {
    if (this.site) {
      this.deviceService.queryBySiteId(this.site.id).subscribe((res: HttpResponse<IDevice[]>) => {
        this.devices = res.body || [];
        this.readyToShow = true;
      });
    } else {
      setTimeout(() => {
        this.waitForSiteAndQueryModels();
      }, 100);
    }
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.siteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('siteListModification');
      this.activeModal.close();
    });
  }
}
