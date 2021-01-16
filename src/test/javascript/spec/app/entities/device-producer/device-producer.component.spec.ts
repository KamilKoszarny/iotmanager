import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IotmanagerTestModule } from '../../../test.module';
import { DeviceProducerComponent } from 'app/entities/device-producer/device-producer.component';
import { DeviceProducerService } from 'app/entities/device-producer/device-producer.service';
import { DeviceProducer } from 'app/shared/model/device-producer.model';

describe('Component Tests', () => {
  describe('DeviceProducer Management Component', () => {
    let comp: DeviceProducerComponent;
    let fixture: ComponentFixture<DeviceProducerComponent>;
    let service: DeviceProducerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IotmanagerTestModule],
        declarations: [DeviceProducerComponent],
      })
        .overrideTemplate(DeviceProducerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeviceProducerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceProducerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DeviceProducer(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.deviceProducers && comp.deviceProducers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
