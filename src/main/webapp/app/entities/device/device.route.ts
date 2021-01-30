import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Device, IDevice } from 'app/shared/model/device.model';
import { DeviceService } from './device.service';
import { DeviceComponent } from './device.component';
import { DeviceDetailComponent } from './device-detail.component';
import { DeviceUpdateComponent } from './device-update.component';

@Injectable({ providedIn: 'root' })
export class DeviceResolve implements Resolve<IDevice> {
  constructor(private service: DeviceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDevice> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((device: HttpResponse<Device>) => {
          if (device.body) {
            return of(device.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Device());
  }
}

export const deviceRoute: Routes = [
  {
    path: '',
    component: DeviceComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'iotmanagerApp.device.home.title',
      isAdmin: true,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'user',
    component: DeviceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iotmanagerApp.device.home.userTitle',
      isAdmin: false,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeviceDetailComponent,
    resolve: {
      device: DeviceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.device.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeviceUpdateComponent,
    resolve: {
      device: DeviceResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'iotmanagerApp.device.home.title',
      isAdmin: true,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'user/new',
    component: DeviceUpdateComponent,
    resolve: {
      device: DeviceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.device.home.title',
      isAdmin: false,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeviceUpdateComponent,
    resolve: {
      device: DeviceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.device.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
