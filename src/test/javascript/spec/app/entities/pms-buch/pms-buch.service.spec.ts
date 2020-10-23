import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PmsBuchService } from 'app/entities/pms-buch/pms-buch.service';
import { IPmsBuch, PmsBuch } from 'app/shared/model/pms-buch.model';

describe('Service Tests', () => {
  describe('PmsBuch Service', () => {
    let injector: TestBed;
    let service: PmsBuchService;
    let httpMock: HttpTestingController;
    let elemDefault: IPmsBuch;
    let expectedResult: IPmsBuch | IPmsBuch[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PmsBuchService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PmsBuch(0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datumvon: currentDate.format(DATE_FORMAT),
            datumbis: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should return a list of PmsBuch', () => {
        const returnedFromService = Object.assign(
          {
            datumvon: currentDate.format(DATE_FORMAT),
            datumbis: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datumvon: currentDate,
            datumbis: currentDate,
          },
          returnedFromService
        );

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
