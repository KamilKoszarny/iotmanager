import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { DeviceType, IDeviceType } from 'app/shared/model/device-type.model';
import { DeviceTypeService } from './device-type.service';

@Component({
  selector: 'jhi-device-type-update',
  templateUrl: './device-type-update.component.html',
})
export class DeviceTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    category: [null, [Validators.required]],
  });

  constructor(protected deviceTypeService: DeviceTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceType }) => {
      this.updateForm(deviceType);
    });
  }

  updateForm(deviceType: IDeviceType): void {
    this.editForm.patchValue({
      id: deviceType.id,
      name: deviceType.name,
      category: deviceType.category,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deviceType = this.createFromForm();
    if (deviceType.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceTypeService.update(deviceType));
    } else {
      this.subscribeToSaveResponse(this.deviceTypeService.create(deviceType));
    }
  }

  private createFromForm(): IDeviceType {
    return {
      ...new DeviceType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      category: this.editForm.get(['category'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceType>>): void {
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
