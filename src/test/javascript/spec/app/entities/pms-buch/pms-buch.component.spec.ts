import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { ElsaTestModule } from '../../../test.module';
import { PmsBuchComponent } from 'app/entities/pms-buch/pms-buch.component';
import { PmsBuchService } from 'app/entities/pms-buch/pms-buch.service';
import { PmsBuch } from 'app/shared/model/pms-buch.model';

describe('Component Tests', () => {
  describe('PmsBuch Management Component', () => {
    let comp: PmsBuchComponent;
    let fixture: ComponentFixture<PmsBuchComponent>;
    let service: PmsBuchService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElsaTestModule],
        declarations: [PmsBuchComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(PmsBuchComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PmsBuchComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PmsBuchService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PmsBuch(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pmsBuches && comp.pmsBuches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PmsBuch(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pmsBuches && comp.pmsBuches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
