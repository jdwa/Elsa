import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPmsBuch, PmsBuch } from 'app/shared/model/pms-buch.model';
import { PmsBuchService } from './pms-buch.service';
import { PmsBuchComponent } from './pms-buch.component';
import { PmsBuchDetailComponent } from './pms-buch-detail.component';

@Injectable({ providedIn: 'root' })
export class PmsBuchResolve implements Resolve<IPmsBuch> {
  constructor(private service: PmsBuchService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPmsBuch> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pmsBuch: HttpResponse<PmsBuch>) => {
          if (pmsBuch.body) {
            return of(pmsBuch.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PmsBuch());
  }
}

export const pmsBuchRoute: Routes = [
  {
    path: '',
    component: PmsBuchComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'elsaApp.pmsBuch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PmsBuchDetailComponent,
    resolve: {
      pmsBuch: PmsBuchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'elsaApp.pmsBuch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
