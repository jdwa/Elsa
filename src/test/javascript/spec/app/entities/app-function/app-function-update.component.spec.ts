import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElsaTestModule } from '../../../test.module';
import { AppFunctionUpdateComponent } from 'app/entities/app-function/app-function-update.component';
import { AppFunctionService } from 'app/entities/app-function/app-function.service';
import { AppFunction } from 'app/shared/model/app-function.model';

describe('Component Tests', () => {
  describe('AppFunction Management Update Component', () => {
    let comp: AppFunctionUpdateComponent;
    let fixture: ComponentFixture<AppFunctionUpdateComponent>;
    let service: AppFunctionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElsaTestModule],
        declarations: [AppFunctionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AppFunctionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppFunctionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppFunctionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AppFunction(123);
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
        const entity = new AppFunction();
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
