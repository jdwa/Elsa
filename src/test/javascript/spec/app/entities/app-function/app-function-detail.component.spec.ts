import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElsaTestModule } from '../../../test.module';
import { AppFunctionDetailComponent } from 'app/entities/app-function/app-function-detail.component';
import { AppFunction } from 'app/shared/model/app-function.model';

describe('Component Tests', () => {
  describe('AppFunction Management Detail Component', () => {
    let comp: AppFunctionDetailComponent;
    let fixture: ComponentFixture<AppFunctionDetailComponent>;
    const route = ({ data: of({ appFunction: new AppFunction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElsaTestModule],
        declarations: [AppFunctionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AppFunctionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppFunctionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load appFunction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appFunction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
