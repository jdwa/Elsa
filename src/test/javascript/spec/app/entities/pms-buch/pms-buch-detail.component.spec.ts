import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElsaTestModule } from '../../../test.module';
import { PmsBuchDetailComponent } from 'app/entities/pms-buch/pms-buch-detail.component';
import { PmsBuch } from 'app/shared/model/pms-buch.model';

describe('Component Tests', () => {
  describe('PmsBuch Management Detail Component', () => {
    let comp: PmsBuchDetailComponent;
    let fixture: ComponentFixture<PmsBuchDetailComponent>;
    const route = ({ data: of({ pmsBuch: new PmsBuch(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElsaTestModule],
        declarations: [PmsBuchDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PmsBuchDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PmsBuchDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pmsBuch on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pmsBuch).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
