import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PmsKundenService } from 'app/entities/pms-kunden/pms-kunden.service';
import { IPmsKunden, PmsKunden } from 'app/shared/model/pms-kunden.model';

describe('Service Tests', () => {
  describe('PmsKunden Service', () => {
    let injector: TestBed;
    let service: PmsKundenService;
    let httpMock: HttpTestingController;
    let elemDefault: IPmsKunden;
    let expectedResult: IPmsKunden | IPmsKunden[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PmsKundenService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PmsKunden(0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should return a list of PmsKunden', () => {
        const returnedFromService = Object.assign(
          {
            name1: 'BBBBBB',
            name2: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
