import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IotmanagerTestModule } from '../../../test.module';
import { DeviceFaultDetailComponent } from 'app/entities/device-fault/device-fault-detail.component';
import { DeviceFault } from 'app/shared/model/device-fault.model';

describe('Component Tests', () => {
  describe('DeviceFault Management Detail Component', () => {
    let comp: DeviceFaultDetailComponent;
    let fixture: ComponentFixture<DeviceFaultDetailComponent>;
    const route = ({ data: of({ deviceFault: new DeviceFault(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IotmanagerTestModule],
        declarations: [DeviceFaultDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DeviceFaultDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceFaultDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deviceFault on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deviceFault).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
