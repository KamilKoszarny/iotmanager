import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IotmanagerTestModule } from '../../../test.module';
import { DeviceTypeComponent } from 'app/entities/device-type/device-type.component';
import { DeviceTypeService } from 'app/entities/device-type/device-type.service';
import { DeviceType } from 'app/shared/model/device-type.model';

describe('Component Tests', () => {
  describe('DeviceType Management Component', () => {
    let comp: DeviceTypeComponent;
    let fixture: ComponentFixture<DeviceTypeComponent>;
    let service: DeviceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IotmanagerTestModule],
        declarations: [DeviceTypeComponent],
      })
        .overrideTemplate(DeviceTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeviceTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DeviceType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.deviceTypes && comp.deviceTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
