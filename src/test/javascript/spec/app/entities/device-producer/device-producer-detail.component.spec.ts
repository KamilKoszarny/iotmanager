import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IotmanagerTestModule } from '../../../test.module';
import { DeviceProducerDetailComponent } from 'app/entities/device-producer/device-producer-detail.component';
import { DeviceProducer } from 'app/shared/model/device-producer.model';

describe('Component Tests', () => {
  describe('DeviceProducer Management Detail Component', () => {
    let comp: DeviceProducerDetailComponent;
    let fixture: ComponentFixture<DeviceProducerDetailComponent>;
    const route = ({ data: of({ deviceProducer: new DeviceProducer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IotmanagerTestModule],
        declarations: [DeviceProducerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DeviceProducerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceProducerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deviceProducer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deviceProducer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
