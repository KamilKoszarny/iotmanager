import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { Device, IDevice } from 'app/shared/model/device.model';
import { DeviceService } from './device.service';
import { IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';
import { ISite } from 'app/shared/model/site.model';
import { SiteService } from 'app/entities/site/site.service';

type SelectableEntity = IDeviceModel | ISite;

@Component({
  selector: 'jhi-device-update',
  templateUrl: './device-update.component.html',
})
export class DeviceUpdateComponent implements OnInit {
  isSaving = false;
  devicemodels: IDeviceModel[] = [];
  sites: ISite[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    serialNo: [null, [Validators.required]],
    deviceModelId: [],
    siteId: [],
  });

  constructor(
    protected deviceService: DeviceService,
    protected deviceModelService: DeviceModelService,
    protected siteService: SiteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ device }) => {
      this.updateForm(device);

      this.deviceModelService.query().subscribe((res: HttpResponse<IDeviceModel[]>) => (this.devicemodels = res.body || []));

      this.siteService.query().subscribe((res: HttpResponse<ISite[]>) => (this.sites = res.body || []));
    });
  }

  updateForm(device: IDevice): void {
    this.editForm.patchValue({
      id: device.id,
      name: device.name,
      serialNo: device.serialNo,
      deviceModelId: device.deviceModelId,
      siteId: device.siteId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const device = this.createFromForm();
    if (device.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceService.update(device));
    } else {
      this.subscribeToSaveResponse(this.deviceService.create(device));
    }
  }

  private createFromForm(): IDevice {
    return {
      ...new Device(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      serialNo: this.editForm.get(['serialNo'])!.value,
      deviceModelId: this.editForm.get(['deviceModelId'])!.value,
      siteId: this.editForm.get(['siteId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDevice>>): void {
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
