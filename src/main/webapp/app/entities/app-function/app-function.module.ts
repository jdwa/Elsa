import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElsaSharedModule } from 'app/shared/shared.module';
import { AppFunctionComponent } from './app-function.component';
import { AppFunctionDetailComponent } from './app-function-detail.component';
import { AppFunctionUpdateComponent } from './app-function-update.component';
import { AppFunctionDeleteDialogComponent } from './app-function-delete-dialog.component';
import { appFunctionRoute } from './app-function.route';

@NgModule({
  imports: [ElsaSharedModule, RouterModule.forChild(appFunctionRoute)],
  declarations: [AppFunctionComponent, AppFunctionDetailComponent, AppFunctionUpdateComponent, AppFunctionDeleteDialogComponent],
  entryComponents: [AppFunctionDeleteDialogComponent],
})
export class ElsaAppFunctionModule {}
