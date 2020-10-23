import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppFunction } from 'app/shared/model/app-function.model';
import { AppFunctionService } from './app-function.service';

@Component({
  templateUrl: './app-function-delete-dialog.component.html',
})
export class AppFunctionDeleteDialogComponent {
  appFunction?: IAppFunction;

  constructor(
    protected appFunctionService: AppFunctionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.appFunctionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('appFunctionListModification');
      this.activeModal.close();
    });
  }
}
