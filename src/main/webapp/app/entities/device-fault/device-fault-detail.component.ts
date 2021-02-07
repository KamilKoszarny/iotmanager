import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeviceFault } from 'app/shared/model/device-fault.model';

@Component({
  selector: 'jhi-device-fault-detail',
  templateUrl: './device-fault-detail.component.html',
})
export class DeviceFaultDetailComponent implements OnInit {
  deviceFault: IDeviceFault | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceFault }) => (this.deviceFault = deviceFault));
  }

  previousState(): void {
    window.history.back();
  }
}
