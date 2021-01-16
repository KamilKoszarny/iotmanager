import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IotmanagerTestModule } from '../../../test.module';
import { SiteComponent } from 'app/entities/site/site.component';
import { SiteService } from 'app/entities/site/site.service';
import { Site } from 'app/shared/model/site.model';

describe('Component Tests', () => {
  describe('Site Management Component', () => {
    let comp: SiteComponent;
    let fixture: ComponentFixture<SiteComponent>;
    let service: SiteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IotmanagerTestModule],
        declarations: [SiteComponent],
      })
        .overrideTemplate(SiteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SiteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SiteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Site(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sites && comp.sites[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
