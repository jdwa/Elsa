import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAppFunction } from 'app/shared/model/app-function.model';

type EntityResponseType = HttpResponse<IAppFunction>;
type EntityArrayResponseType = HttpResponse<IAppFunction[]>;

@Injectable({ providedIn: 'root' })
export class AppFunctionService {
  public resourceUrl = SERVER_API_URL + 'api/app-functions';

  constructor(protected http: HttpClient) {}

  create(appFunction: IAppFunction): Observable<EntityResponseType> {
    return this.http.post<IAppFunction>(this.resourceUrl, appFunction, { observe: 'response' });
  }

  update(appFunction: IAppFunction): Observable<EntityResponseType> {
    return this.http.put<IAppFunction>(this.resourceUrl, appFunction, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppFunction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppFunction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
