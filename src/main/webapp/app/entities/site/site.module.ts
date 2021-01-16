import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IotmanagerSharedModule } from 'app/shared/shared.module';
import { SiteComponent } from './site.component';
import { SiteDetailComponent } from './site-detail.component';
import { SiteUpdateComponent } from './site-update.component';
import { SiteDeleteDialogComponent } from './site-delete-dialog.component';
import { siteRoute } from './site.route';

@NgModule({
  imports: [IotmanagerSharedModule, RouterModule.forChild(siteRoute)],
  declarations: [SiteComponent, SiteDetailComponent, SiteUpdateComponent, SiteDeleteDialogComponent],
  entryComponents: [SiteDeleteDialogComponent],
})
export class IotmanagerSiteModule {}
