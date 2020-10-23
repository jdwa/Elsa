import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElsaSharedModule } from 'app/shared/shared.module';
import { PmsBuchComponent } from './pms-buch.component';
import { PmsBuchDetailComponent } from './pms-buch-detail.component';
import { pmsBuchRoute } from './pms-buch.route';

@NgModule({
  imports: [ElsaSharedModule, RouterModule.forChild(pmsBuchRoute)],
  declarations: [PmsBuchComponent, PmsBuchDetailComponent],
})
export class ElsaPmsBuchModule {}
