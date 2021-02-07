import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IotmanagerTestModule } from '../../../test.module';
import { DeviceFaultUpdateComponent } from 'app/entities/device-fault/device-fault-update.component';
import { DeviceFaultService } from 'app/entities/device-fault/device-fault.service';
import { DeviceFault } from 'app/shared/model/device-fault.model';

describe('Component Tests', () => {
  describe('DeviceFault Management Update Component', () => {
    let comp: DeviceFaultUpdateComponent;
    let fixture: ComponentFixture<DeviceFaultUpdateComponent>;
    let service: DeviceFaultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IotmanagerTestModule],
        declarations: [DeviceFaultUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DeviceFaultUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeviceFaultUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceFaultService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeviceFault(123);
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
        const entity = new DeviceFault();
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
