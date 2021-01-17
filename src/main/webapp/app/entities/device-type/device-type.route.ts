import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { DeviceType, IDeviceType } from 'app/shared/model/device-type.model';
import { DeviceTypeService } from './device-type.service';
import { DeviceTypeComponent } from './device-type.component';
import { DeviceTypeDetailComponent } from './device-type-detail.component';
import { DeviceTypeUpdateComponent } from './device-type-update.component';

@Injectable({ providedIn: 'root' })
export class DeviceTypeResolve implements Resolve<IDeviceType> {
  constructor(private service: DeviceTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeviceType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deviceType: HttpResponse<DeviceType>) => {
          if (deviceType.body) {
            return of(deviceType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DeviceType());
  }
}

export const deviceTypeRoute: Routes = [
  {
    path: '',
    component: DeviceTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iotmanagerApp.deviceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeviceTypeDetailComponent,
    resolve: {
      deviceType: DeviceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.deviceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeviceTypeUpdateComponent,
    resolve: {
      deviceType: DeviceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.deviceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeviceTypeUpdateComponent,
    resolve: {
      deviceType: DeviceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.deviceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
