import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeviceType } from 'app/shared/model/device-type.model';
import { DeviceTypeService } from './device-type.service';
import { DeviceTypeDeleteDialogComponent } from './device-type-delete-dialog.component';

@Component({
  selector: 'jhi-device-type',
  templateUrl: './device-type.component.html',
})
export class DeviceTypeComponent implements OnInit, OnDestroy {
  deviceTypes?: IDeviceType[];
  eventSubscriber?: Subscription;

  constructor(protected deviceTypeService: DeviceTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.deviceTypeService.query().subscribe((res: HttpResponse<IDeviceType[]>) => (this.deviceTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDeviceTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeviceType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDeviceTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('deviceTypeListModification', () => this.loadAll());
  }

  delete(deviceType: IDeviceType): void {
    const modalRef = this.modalService.open(DeviceTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deviceType = deviceType;
  }
}
