import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IotmanagerSharedModule } from 'app/shared/shared.module';
import { DeviceProducerComponent } from './device-producer.component';
import { DeviceProducerDetailComponent } from './device-producer-detail.component';
import { DeviceProducerUpdateComponent } from './device-producer-update.component';
import { DeviceProducerDeleteDialogComponent } from './device-producer-delete-dialog.component';
import { deviceProducerRoute } from './device-producer.route';

@NgModule({
  imports: [IotmanagerSharedModule, RouterModule.forChild(deviceProducerRoute)],
  declarations: [
    DeviceProducerComponent,
    DeviceProducerDetailComponent,
    DeviceProducerUpdateComponent,
    DeviceProducerDeleteDialogComponent,
  ],
  entryComponents: [DeviceProducerDeleteDialogComponent],
})
export class IotmanagerDeviceProducerModule {}
