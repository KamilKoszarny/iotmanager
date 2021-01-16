import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeviceProducer } from 'app/shared/model/device-producer.model';
import { DeviceProducerService } from './device-producer.service';
import { DeviceProducerDeleteDialogComponent } from './device-producer-delete-dialog.component';

@Component({
  selector: 'jhi-device-producer',
  templateUrl: './device-producer.component.html',
})
export class DeviceProducerComponent implements OnInit, OnDestroy {
  deviceProducers?: IDeviceProducer[];
  eventSubscriber?: Subscription;

  constructor(
    protected deviceProducerService: DeviceProducerService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.deviceProducerService.query().subscribe((res: HttpResponse<IDeviceProducer[]>) => (this.deviceProducers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDeviceProducers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeviceProducer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDeviceProducers(): void {
    this.eventSubscriber = this.eventManager.subscribe('deviceProducerListModification', () => this.loadAll());
  }

  delete(deviceProducer: IDeviceProducer): void {
    const modalRef = this.modalService.open(DeviceProducerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deviceProducer = deviceProducer;
  }
}
