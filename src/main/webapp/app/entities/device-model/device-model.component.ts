import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from './device-model.service';
import { DeviceModelDeleteDialogComponent } from './device-model-delete-dialog.component';

@Component({
  selector: 'jhi-device-model',
  templateUrl: './device-model.component.html',
})
export class DeviceModelComponent implements OnInit, OnDestroy {
  deviceModels?: IDeviceModel[];
  eventSubscriber?: Subscription;

  constructor(
    protected deviceModelService: DeviceModelService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.deviceModelService.query().subscribe((res: HttpResponse<IDeviceModel[]>) => (this.deviceModels = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDeviceModels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeviceModel): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDeviceModels(): void {
    this.eventSubscriber = this.eventManager.subscribe('deviceModelListModification', () => this.loadAll());
  }

  delete(deviceModel: IDeviceModel): void {
    const modalRef = this.modalService.open(DeviceModelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deviceModel = deviceModel;
  }
}
