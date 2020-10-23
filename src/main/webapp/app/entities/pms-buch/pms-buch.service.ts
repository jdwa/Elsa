import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPmsBuch } from 'app/shared/model/pms-buch.model';

type EntityResponseType = HttpResponse<IPmsBuch>;
type EntityArrayResponseType = HttpResponse<IPmsBuch[]>;

@Injectable({ providedIn: 'root' })
export class PmsBuchService {
  public resourceUrl = SERVER_API_URL + 'api/pms-buches';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPmsBuch>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPmsBuch[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(pmsBuch: IPmsBuch): IPmsBuch {
    const copy: IPmsBuch = Object.assign({}, pmsBuch, {
      datumvon: pmsBuch.datumvon && pmsBuch.datumvon.isValid() ? pmsBuch.datumvon.format(DATE_FORMAT) : undefined,
      datumbis: pmsBuch.datumbis && pmsBuch.datumbis.isValid() ? pmsBuch.datumbis.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datumvon = res.body.datumvon ? moment(res.body.datumvon) : undefined;
      res.body.datumbis = res.body.datumbis ? moment(res.body.datumbis) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pmsBuch: IPmsBuch) => {
        pmsBuch.datumvon = pmsBuch.datumvon ? moment(pmsBuch.datumvon) : undefined;
        pmsBuch.datumbis = pmsBuch.datumbis ? moment(pmsBuch.datumbis) : undefined;
      });
    }
    return res;
  }
}
