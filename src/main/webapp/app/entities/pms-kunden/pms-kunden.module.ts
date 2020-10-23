import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElsaSharedModule } from 'app/shared/shared.module';
import { PmsKundenComponent } from './pms-kunden.component';
import { PmsKundenDetailComponent } from './pms-kunden-detail.component';
import { pmsKundenRoute } from './pms-kunden.route';

@NgModule({
  imports: [ElsaSharedModule, RouterModule.forChild(pmsKundenRoute)],
  declarations: [PmsKundenComponent, PmsKundenDetailComponent],
})
export class ElsaPmsKundenModule {}
