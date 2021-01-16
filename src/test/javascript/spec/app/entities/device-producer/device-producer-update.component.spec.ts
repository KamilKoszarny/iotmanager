import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IotmanagerTestModule } from '../../../test.module';
import { DeviceProducerUpdateComponent } from 'app/entities/device-producer/device-producer-update.component';
import { DeviceProducerService } from 'app/entities/device-producer/device-producer.service';
import { DeviceProducer } from 'app/shared/model/device-producer.model';

describe('Component Tests', () => {
  describe('DeviceProducer Management Update Component', () => {
    let comp: DeviceProducerUpdateComponent;
    let fixture: ComponentFixture<DeviceProducerUpdateComponent>;
    let service: DeviceProducerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IotmanagerTestModule],
        declarations: [DeviceProducerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DeviceProducerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeviceProducerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceProducerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeviceProducer(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeviceProducer();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
