import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IotmanagerSharedModule } from 'app/shared/shared.module';
import { DeviceTypeComponent } from './device-type.component';
import { DeviceTypeDetailComponent } from './device-type-detail.component';
import { DeviceTypeUpdateComponent } from './device-type-update.component';
import { DeviceTypeDeleteDialogComponent } from './device-type-delete-dialog.component';
import { deviceTypeRoute } from './device-type.route';

@NgModule({
  imports: [IotmanagerSharedModule, RouterModule.forChild(deviceTypeRoute)],
  declarations: [DeviceTypeComponent, DeviceTypeDetailComponent, DeviceTypeUpdateComponent, DeviceTypeDeleteDialogComponent],
  entryComponents: [DeviceTypeDeleteDialogComponent],
})
export class IotmanagerDeviceTypeModule {}
