import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISite, Site } from 'app/shared/model/site.model';
import { SiteService } from './site.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';

@Component({
  selector: 'jhi-site-update',
  templateUrl: './site-update.component.html',
})
export class SiteUpdateComponent implements OnInit {
  isSaving = false;
  addresses: IAddress[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    address: [],
  });

  constructor(
    protected siteService: SiteService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ site }) => {
      this.updateForm(site);

      this.addressService
        .query({ filter: 'site-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!site.address || !site.address.id) {
            this.addresses = resBody;
          } else {
            this.addressService
              .find(site.address.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.addresses = concatRes));
          }
        });
    });
  }

  updateForm(site: ISite): void {
    this.editForm.patchValue({
      id: site.id,
      name: site.name,
      address: site.address,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const site = this.createFromForm();
    if (site.id !== undefined) {
      this.subscribeToSaveResponse(this.siteService.update(site));
    } else {
      this.subscribeToSaveResponse(this.siteService.create(site));
    }
  }

  private createFromForm(): ISite {
    return {
      ...new Site(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISite>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAddress): any {
    return item.id;
  }
}
