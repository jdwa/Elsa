import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElsaTestModule } from '../../../test.module';
import { PmsKundenDetailComponent } from 'app/entities/pms-kunden/pms-kunden-detail.component';
import { PmsKunden } from 'app/shared/model/pms-kunden.model';

describe('Component Tests', () => {
  describe('PmsKunden Management Detail Component', () => {
    let comp: PmsKundenDetailComponent;
    let fixture: ComponentFixture<PmsKundenDetailComponent>;
    const route = ({ data: of({ pmsKunden: new PmsKunden(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElsaTestModule],
        declarations: [PmsKundenDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PmsKundenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PmsKundenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pmsKunden on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pmsKunden).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
