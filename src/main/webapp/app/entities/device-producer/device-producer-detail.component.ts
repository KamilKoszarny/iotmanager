import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeviceProducer } from 'app/shared/model/device-producer.model';

@Component({
  selector: 'jhi-device-producer-detail',
  templateUrl: './device-producer-detail.component.html',
})
export class DeviceProducerDetailComponent implements OnInit {
  deviceProducer: IDeviceProducer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceProducer }) => (this.deviceProducer = deviceProducer));
  }

  previousState(): void {
    window.history.back();
  }
}
