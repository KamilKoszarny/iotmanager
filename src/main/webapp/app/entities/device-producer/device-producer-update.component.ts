import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { DeviceProducer, IDeviceProducer } from 'app/shared/model/device-producer.model';
import { DeviceProducerService } from './device-producer.service';

@Component({
  selector: 'jhi-device-producer-update',
  templateUrl: './device-producer-update.component.html',
})
export class DeviceProducerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
  });

  constructor(protected deviceProducerService: DeviceProducerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceProducer }) => {
      this.updateForm(deviceProducer);
    });
  }

  updateForm(deviceProducer: IDeviceProducer): void {
    this.editForm.patchValue({
      id: deviceProducer.id,
      name: deviceProducer.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deviceProducer = this.createFromForm();
    if (deviceProducer.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceProducerService.update(deviceProducer));
    } else {
      this.subscribeToSaveResponse(this.deviceProducerService.create(deviceProducer));
    }
  }

  private createFromForm(): IDeviceProducer {
    return {
      ...new DeviceProducer(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceProducer>>): void {
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
}
