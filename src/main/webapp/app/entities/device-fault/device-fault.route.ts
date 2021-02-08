import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { DeviceFault, IDeviceFault } from 'app/shared/model/device-fault.model';
import { DeviceFaultService } from './device-fault.service';
import { DeviceFaultComponent } from './device-fault.component';
import { DeviceFaultDetailComponent } from './device-fault-detail.component';
import { DeviceFaultUpdateComponent } from './device-fault-update.component';

@Injectable({ providedIn: 'root' })
export class DeviceFaultResolve implements Resolve<IDeviceFault> {
  constructor(private service: DeviceFaultService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeviceFault> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deviceFault: HttpResponse<DeviceFault>) => {
          if (deviceFault.body) {
            return of(deviceFault.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DeviceFault());
  }
}

export const deviceFaultRoute: Routes = [
  {
    path: '',
    component: DeviceFaultComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iotmanagerApp.deviceFault.home.title',
      isAdmin: true,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'user',
    component: DeviceFaultComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iotmanagerApp.deviceFault.home.userTitle',
      isAdmin: false,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeviceFaultDetailComponent,
    resolve: {
      deviceFault: DeviceFaultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.deviceFault.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeviceFaultUpdateComponent,
    resolve: {
      deviceFault: DeviceFaultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.deviceFault.home.title',
      isAdmin: true,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'user/new',
    component: DeviceFaultUpdateComponent,
    resolve: {
      deviceFault: DeviceFaultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.deviceFault.home.title',
      isAdmin: false,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeviceFaultUpdateComponent,
    resolve: {
      deviceFault: DeviceFaultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.deviceFault.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
