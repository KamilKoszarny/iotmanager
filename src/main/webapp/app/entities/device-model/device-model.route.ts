import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { DeviceModel, IDeviceModel } from 'app/shared/model/device-model.model';
import { DeviceModelService } from './device-model.service';
import { DeviceModelComponent } from './device-model.component';
import { DeviceModelDetailComponent } from './device-model-detail.component';
import { DeviceModelUpdateComponent } from './device-model-update.component';

@Injectable({ providedIn: 'root' })
export class DeviceModelResolve implements Resolve<IDeviceModel> {
  constructor(private service: DeviceModelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeviceModel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deviceModel: HttpResponse<DeviceModel>) => {
          if (deviceModel.body) {
            return of(deviceModel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DeviceModel());
  }
}

export const deviceModelRoute: Routes = [
  {
    path: '',
    component: DeviceModelComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iotmanagerApp.deviceModel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeviceModelDetailComponent,
    resolve: {
      deviceModel: DeviceModelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.deviceModel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeviceModelUpdateComponent,
    resolve: {
      deviceModel: DeviceModelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.deviceModel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeviceModelUpdateComponent,
    resolve: {
      deviceModel: DeviceModelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.deviceModel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
