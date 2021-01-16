import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'device',
        loadChildren: () => import('./device/device.module').then(m => m.IotmanagerDeviceModule),
      },
      {
        path: 'device-type',
        loadChildren: () => import('./device-type/device-type.module').then(m => m.IotmanagerDeviceTypeModule),
      },
      {
        path: 'device-model',
        loadChildren: () => import('./device-model/device-model.module').then(m => m.IotmanagerDeviceModelModule),
      },
      {
        path: 'device-producer',
        loadChildren: () => import('./device-producer/device-producer.module').then(m => m.IotmanagerDeviceProducerModule),
      },
      {
        path: 'site',
        loadChildren: () => import('./site/site.module').then(m => m.IotmanagerSiteModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.IotmanagerAddressModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class IotmanagerEntityModule {}
