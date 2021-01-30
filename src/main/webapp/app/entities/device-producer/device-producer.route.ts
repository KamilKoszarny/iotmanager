import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { DeviceProducer, IDeviceProducer } from 'app/shared/model/device-producer.model';
import { DeviceProducerService } from './device-producer.service';
import { DeviceProducerComponent } from './device-producer.component';
import { DeviceProducerDetailComponent } from './device-producer-detail.component';
import { DeviceProducerUpdateComponent } from './device-producer-update.component';

@Injectable({ providedIn: 'root' })
export class DeviceProducerResolve implements Resolve<IDeviceProducer> {
  constructor(private service: DeviceProducerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeviceProducer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deviceProducer: HttpResponse<DeviceProducer>) => {
          if (deviceProducer.body) {
            return of(deviceProducer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DeviceProducer());
  }
}

export const deviceProducerRoute: Routes = [
  {
    path: '',
    component: DeviceProducerComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iotmanagerApp.deviceProducer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeviceProducerDetailComponent,
    resolve: {
      deviceProducer: DeviceProducerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.deviceProducer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeviceProducerUpdateComponent,
    resolve: {
      deviceProducer: DeviceProducerResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'iotmanagerApp.deviceProducer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeviceProducerUpdateComponent,
    resolve: {
      deviceProducer: DeviceProducerResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'iotmanagerApp.deviceProducer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
