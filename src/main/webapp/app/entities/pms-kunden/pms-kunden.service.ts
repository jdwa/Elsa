import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPmsKunden } from 'app/shared/model/pms-kunden.model';

type EntityResponseType = HttpResponse<IPmsKunden>;
type EntityArrayResponseType = HttpResponse<IPmsKunden[]>;

@Injectable({ providedIn: 'root' })
export class PmsKundenService {
  public resourceUrl = SERVER_API_URL + 'api/pms-kundens';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPmsKunden>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPmsKunden[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
