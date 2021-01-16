import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISite } from 'app/shared/model/site.model';
import { SiteService } from './site.service';

@Component({
  templateUrl: './site-delete-dialog.component.html',
})
export class SiteDeleteDialogComponent {
  site?: ISite;

  constructor(protected siteService: SiteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.siteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('siteListModification');
      this.activeModal.close();
    });
  }
}
