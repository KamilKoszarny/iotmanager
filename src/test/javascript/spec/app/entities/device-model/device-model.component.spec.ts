import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IotmanagerTestModule } from '../../../test.module';
import { DeviceModelComponent } from 'app/entities/device-model/device-model.component';
import { DeviceModelService } from 'app/entities/device-model/device-model.service';
import { DeviceModel } from 'app/shared/model/device-model.model';

describe('Component Tests', () => {
  describe('DeviceModel Management Component', () => {
    let comp: DeviceModelComponent;
    let fixture: ComponentFixture<DeviceModelComponent>;
    let service: DeviceModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IotmanagerTestModule],
        declarations: [DeviceModelComponent],
      })
        .overrideTemplate(DeviceModelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeviceModelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceModelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DeviceModel(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.deviceModels && comp.deviceModels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
