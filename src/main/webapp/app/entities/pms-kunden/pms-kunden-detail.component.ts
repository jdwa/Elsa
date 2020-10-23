import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPmsKunden } from 'app/shared/model/pms-kunden.model';

@Component({
  selector: 'jhi-pms-kunden-detail',
  templateUrl: './pms-kunden-detail.component.html',
})
export class PmsKundenDetailComponent implements OnInit {
  pmsKunden: IPmsKunden | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pmsKunden }) => (this.pmsKunden = pmsKunden));
  }

  previousState(): void {
    window.history.back();
  }
}
