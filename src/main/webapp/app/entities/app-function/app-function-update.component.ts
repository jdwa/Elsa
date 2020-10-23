import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAppFunction, AppFunction } from 'app/shared/model/app-function.model';
import { AppFunctionService } from './app-function.service';

@Component({
  selector: 'jhi-app-function-update',
  templateUrl: './app-function-update.component.html',
})
export class AppFunctionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
  });

  constructor(protected appFunctionService: AppFunctionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appFunction }) => {
      this.updateForm(appFunction);
    });
  }

  updateForm(appFunction: IAppFunction): void {
    this.editForm.patchValue({
      id: appFunction.id,
      name: appFunction.name,
      description: appFunction.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appFunction = this.createFromForm();
    if (appFunction.id !== undefined) {
      this.subscribeToSaveResponse(this.appFunctionService.update(appFunction));
    } else {
      this.subscribeToSaveResponse(this.appFunctionService.create(appFunction));
    }
  }

  private createFromForm(): IAppFunction {
    return {
      ...new AppFunction(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppFunction>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
