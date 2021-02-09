import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { Device, IDevice } from 'app/shared/model/device.model';
import { DeviceService } from './device.service';
import { DeviceModel, IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';
import { ISite, Site } from 'app/shared/model/site.model';
import { SiteService } from 'app/entities/site/site.service';
import { DeviceType, IDeviceType } from '../../shared/model/device-type.model';
import { DeviceProducer, IDeviceProducer } from '../../shared/model/device-producer.model';
import { DeviceProducerService } from '../device-producer/device-producer.service';
import { DeviceTypeService } from '../device-type/device-type.service';

type SelectableEntity = IDeviceModel | ISite;

@Component({
  selector: 'jhi-device-update',
  templateUrl: './device-update.component.html',
})
export class DeviceUpdateComponent implements OnInit {
  isSaving = false;
  deviceTypes: IDeviceType[] = [];
  deviceTypeId!: number;
  deviceProducers: IDeviceProducer[] = [];
  deviceProducerId!: number;
  deviceModels: IDeviceModel[] = [];
  sites: ISite[] = [];
  isCreateNew!: boolean;
  isAdmin = false;
  isNewForm!: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(50)]],
    serialNo: [null, [Validators.required, Validators.maxLength(80)]],
    typeId: [null],
    producerId: [null],
    modelId: [null, Validators.required],
    siteId: [null, Validators.required],
  });

  constructor(
    protected deviceService: DeviceService,
    protected deviceTypeService: DeviceTypeService,
    protected deviceProducerService: DeviceProducerService,
    protected deviceModelService: DeviceModelService,
    protected siteService: SiteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ device, isAdmin }) => {
      this.updateForm(device);
      this.isAdmin = isAdmin;

      this.deviceTypeService.query().subscribe((res: HttpResponse<IDeviceType[]>) => {
        this.deviceTypes = res.body || [];
        this.deviceTypes.unshift(new DeviceType());
      });
      this.deviceProducerService.query().subscribe((res: HttpResponse<IDeviceProducer[]>) => {
        this.deviceProducers = res.body || [];
        this.deviceProducers.unshift(new DeviceProducer());
      });
      this.deviceModelService.query().subscribe((res: HttpResponse<IDeviceModel[]>) => {
        this.deviceModels = res.body || [];
        this.deviceModels.unshift(new DeviceModel());
      });
      this.siteService.query(this.isAdmin).subscribe((res: HttpResponse<ISite[]>) => {
        this.sites = res.body || [];
        this.sites.unshift(new Site());
        const passedSiteId = this.activatedRoute.snapshot.queryParamMap.get('siteId');
        if (passedSiteId) {
          this.editForm.controls['siteId'].setValue(+passedSiteId, { onlySelf: true });
        }
      });
    });
  }

  updateForm(device: IDevice): void {
    this.editForm.patchValue({
      id: device.id,
      name: device.name,
      serialNo: device.serialNo,
      modelId: device.modelId,
      siteId: device.siteId,
    });
    this.isCreateNew = !this.editForm.get('id')!.value;
    this.isNewForm = true;
  }

  filterDeviceModels(): void {
    if (!this.isNewForm || this.deviceTypeId !== undefined || this.deviceProducerId !== undefined) {
      this.deviceModelService
        .queryByTypeAndProducer({ typeId: this.deviceTypeId, producerId: this.deviceProducerId })
        .subscribe((res: HttpResponse<IDeviceModel[]>) => {
          this.deviceModels = res.body || [];
          this.deviceModels.unshift(new DeviceModel());
        });
      this.isNewForm = false;
    }
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
      modelId: this.editForm.get(['modelId'])!.value,
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
