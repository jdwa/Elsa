import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPmsBuch } from 'app/shared/model/pms-buch.model';

@Component({
  selector: 'jhi-pms-buch-detail',
  templateUrl: './pms-buch-detail.component.html',
})
export class PmsBuchDetailComponent implements OnInit {
  pmsBuch: IPmsBuch | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pmsBuch }) => (this.pmsBuch = pmsBuch));
  }

  previousState(): void {
    window.history.back();
  }
}
