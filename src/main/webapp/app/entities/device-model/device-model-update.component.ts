import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDeviceModel, DeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from './device-model.service';
import { IDeviceProducer } from 'app/shared/model/device-producer.model';
import { DeviceProducerService } from 'app/entities/device-producer/device-producer.service';
import { IDeviceType } from 'app/shared/model/device-type.model';
import { DeviceTypeService } from 'app/entities/device-type/device-type.service';

type SelectableEntity = IDeviceProducer | IDeviceType;

@Component({
  selector: 'jhi-device-model-update',
  templateUrl: './device-model-update.component.html',
})
export class DeviceModelUpdateComponent implements OnInit {
  isSaving = false;
  deviceproducers: IDeviceProducer[] = [];
  devicetypes: IDeviceType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    deviceProducer: [],
    deviceType: [],
  });

  constructor(
    protected deviceModelService: DeviceModelService,
    protected deviceProducerService: DeviceProducerService,
    protected deviceTypeService: DeviceTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceModel }) => {
      this.updateForm(deviceModel);

      this.deviceProducerService.query().subscribe((res: HttpResponse<IDeviceProducer[]>) => (this.deviceproducers = res.body || []));

      this.deviceTypeService.query().subscribe((res: HttpResponse<IDeviceType[]>) => (this.devicetypes = res.body || []));
    });
  }

  updateForm(deviceModel: IDeviceModel): void {
    this.editForm.patchValue({
      id: deviceModel.id,
      name: deviceModel.name,
      deviceProducer: deviceModel.deviceProducer,
      deviceType: deviceModel.deviceType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deviceModel = this.createFromForm();
    if (deviceModel.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceModelService.update(deviceModel));
    } else {
      this.subscribeToSaveResponse(this.deviceModelService.create(deviceModel));
    }
  }

  private createFromForm(): IDeviceModel {
    return {
      ...new DeviceModel(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deviceProducer: this.editForm.get(['deviceProducer'])!.value,
      deviceType: this.editForm.get(['deviceType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceModel>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
