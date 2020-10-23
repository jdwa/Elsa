import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPmsKunden, PmsKunden } from 'app/shared/model/pms-kunden.model';
import { PmsKundenService } from './pms-kunden.service';
import { PmsKundenComponent } from './pms-kunden.component';
import { PmsKundenDetailComponent } from './pms-kunden-detail.component';

@Injectable({ providedIn: 'root' })
export class PmsKundenResolve implements Resolve<IPmsKunden> {
  constructor(private service: PmsKundenService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPmsKunden> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pmsKunden: HttpResponse<PmsKunden>) => {
          if (pmsKunden.body) {
            return of(pmsKunden.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PmsKunden());
  }
}

export const pmsKundenRoute: Routes = [
  {
    path: '',
    component: PmsKundenComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'elsaApp.pmsKunden.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PmsKundenDetailComponent,
    resolve: {
      pmsKunden: PmsKundenResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'elsaApp.pmsKunden.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
