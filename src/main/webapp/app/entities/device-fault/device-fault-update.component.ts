import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { DeviceFault, IDeviceFault } from 'app/shared/model/device-fault.model';
import { DeviceFaultService } from './device-fault.service';
import { IDevice } from 'app/shared/model/device.model';
import { DeviceService } from 'app/entities/device/device.service';

@Component({
  selector: 'jhi-device-fault-update',
  templateUrl: './device-fault-update.component.html',
})
export class DeviceFaultUpdateComponent implements OnInit {
  isSaving = false;
  devices: IDevice[] = [];
  isCreateNew!: boolean;
  isAdmin = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(50)]],
    description: [null, [Validators.required, Validators.maxLength(1000)]],
    urgency: [null, [Validators.min(1), Validators.max(10)]],
    deviceId: [null, Validators.required],
  });

  constructor(
    protected deviceFaultService: DeviceFaultService,
    protected deviceService: DeviceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceFault, isAdmin }) => {
      this.updateForm(deviceFault);
      this.isAdmin = isAdmin;

      this.deviceService.query(this.isAdmin).subscribe((res: HttpResponse<IDevice[]>) => (this.devices = res.body || []));
    });
  }

  updateForm(deviceFault: IDeviceFault): void {
    this.editForm.patchValue({
      id: deviceFault.id,
      name: deviceFault.name,
      description: deviceFault.description,
      urgency: deviceFault.urgency,
      deviceId: deviceFault.deviceId,
    });
    this.isCreateNew = !this.editForm.get('id')!.value;
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deviceFault = this.createFromForm();
    if (deviceFault.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceFaultService.update(deviceFault));
    } else {
      this.subscribeToSaveResponse(this.deviceFaultService.create(deviceFault));
    }
  }

  private createFromForm(): IDeviceFault {
    return {
      ...new DeviceFault(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      urgency: this.editForm.get(['urgency'])!.value,
      deviceId: this.editForm.get(['deviceId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceFault>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IDevice): any {
    return item.id;
  }

  urgencyStyle(): { [p: string]: any } | null {
    const red = this.editForm.get('urgency')?.value * 25;
    const green = (10 - this.editForm.get('urgency')?.value) * 25;
    return { backgroundColor: `rgb(${red}, ${green}, 0)` };
  }
}
