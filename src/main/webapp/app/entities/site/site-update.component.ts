import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { conditionalValidator } from 'app/shared/validators/conditional-validator';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISite, Site } from 'app/shared/model/site.model';
import { SiteService } from './site.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-site-update',
  templateUrl: './site-update.component.html',
})
export class SiteUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  isCreateNew!: boolean;
  isAdmin = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(50)]],
    city: [null, [Validators.required, Validators.maxLength(50)]],
    street: [null, [Validators.maxLength(50)]],
    streetNo: [null, [Validators.required, Validators.maxLength(10)]],
    userId: [null, [conditionalValidator(() => this.isAdmin, Validators.required)]],
  });

  constructor(
    protected siteService: SiteService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ site, isAdmin }) => {
      this.isAdmin = isAdmin;
      this.updateForm(site);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(site: ISite): void {
    this.editForm.patchValue({
      id: site.id,
      name: site.name,
      city: site.city,
      street: site.street,
      streetNo: site.streetNo,
      userId: site.userId,
    });
    this.isCreateNew = !this.editForm.get('id')!.value;
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const site = this.createFromForm();
    if (!this.isAdmin && this.isCreateNew) {
      site.userId = undefined;
    }
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
      city: this.editForm.get(['city'])!.value,
      street: this.editForm.get(['street'])!.value,
      streetNo: this.editForm.get(['streetNo'])!.value,
      userId: this.editForm.get(['userId'])!.value,
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
