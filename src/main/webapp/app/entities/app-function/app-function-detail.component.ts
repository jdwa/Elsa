import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppFunction } from 'app/shared/model/app-function.model';

@Component({
  selector: 'jhi-app-function-detail',
  templateUrl: './app-function-detail.component.html',
})
export class AppFunctionDetailComponent implements OnInit {
  appFunction: IAppFunction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appFunction }) => (this.appFunction = appFunction));
  }

  previousState(): void {
    window.history.back();
  }
}
