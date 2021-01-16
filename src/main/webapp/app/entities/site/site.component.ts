import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISite } from 'app/shared/model/site.model';
import { SiteService } from './site.service';
import { SiteDeleteDialogComponent } from './site-delete-dialog.component';

@Component({
  selector: 'jhi-site',
  templateUrl: './site.component.html',
})
export class SiteComponent implements OnInit, OnDestroy {
  sites?: ISite[];
  eventSubscriber?: Subscription;

  constructor(protected siteService: SiteService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.siteService.query().subscribe((res: HttpResponse<ISite[]>) => (this.sites = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSites(): void {
    this.eventSubscriber = this.eventManager.subscribe('siteListModification', () => this.loadAll());
  }

  delete(site: ISite): void {
    const modalRef = this.modalService.open(SiteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.site = site;
  }
}
