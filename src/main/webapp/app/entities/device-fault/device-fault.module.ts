import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IotmanagerSharedModule } from 'app/shared/shared.module';
import { DeviceFaultComponent } from './device-fault.component';
import { DeviceFaultDetailComponent } from './device-fault-detail.component';
import { DeviceFaultUpdateComponent } from './device-fault-update.component';
import { DeviceFaultDeleteDialogComponent } from './device-fault-delete-dialog.component';
import { deviceFaultRoute } from './device-fault.route';

@NgModule({
  imports: [IotmanagerSharedModule, RouterModule.forChild(deviceFaultRoute)],
  declarations: [DeviceFaultComponent, DeviceFaultDetailComponent, DeviceFaultUpdateComponent, DeviceFaultDeleteDialogComponent],
  entryComponents: [DeviceFaultDeleteDialogComponent],
})
export class IotmanagerDeviceFaultModule {}
