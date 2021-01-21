import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISite, Site } from 'app/shared/model/site.model';
import { SiteService } from './site.service';
import { SiteComponent } from './site.component';
import { SiteDetailComponent } from './site-detail.component';
import { SiteUpdateComponent } from './site-update.component';

@Injectable({ providedIn: 'root' })
export class SiteResolve implements Resolve<ISite> {
  constructor(private service: SiteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((site: HttpResponse<Site>) => {
          if (site.body) {
            return of(site.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Site());
  }
}

export const siteRoute: Routes = [
  {
    path: '',
    component: SiteComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'iotmanagerApp.site.home.title',
      isAdmin: true,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'user',
    component: SiteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iotmanagerApp.site.home.title',
      isAdmin: false,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SiteDetailComponent,
    resolve: {
      site: SiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.site.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SiteUpdateComponent,
    resolve: {
      site: SiteResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'iotmanagerApp.site.home.title',
      isAdmin: true,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'user/new',
    component: SiteUpdateComponent,
    resolve: {
      site: SiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.site.home.title',
      isAdmin: false,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SiteUpdateComponent,
    resolve: {
      site: SiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iotmanagerApp.site.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
