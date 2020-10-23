import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAppFunction, AppFunction } from 'app/shared/model/app-function.model';
import { AppFunctionService } from './app-function.service';
import { AppFunctionComponent } from './app-function.component';
import { AppFunctionDetailComponent } from './app-function-detail.component';
import { AppFunctionUpdateComponent } from './app-function-update.component';

@Injectable({ providedIn: 'root' })
export class AppFunctionResolve implements Resolve<IAppFunction> {
  constructor(private service: AppFunctionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAppFunction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((appFunction: HttpResponse<AppFunction>) => {
          if (appFunction.body) {
            return of(appFunction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AppFunction());
  }
}

export const appFunctionRoute: Routes = [
  {
    path: '',
    component: AppFunctionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'elsaApp.appFunction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AppFunctionDetailComponent,
    resolve: {
      appFunction: AppFunctionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'elsaApp.appFunction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AppFunctionUpdateComponent,
    resolve: {
      appFunction: AppFunctionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'elsaApp.appFunction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AppFunctionUpdateComponent,
    resolve: {
      appFunction: AppFunctionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'elsaApp.appFunction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
