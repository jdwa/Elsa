import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pms-kunden',
        loadChildren: () => import('./pms-kunden/pms-kunden.module').then(m => m.ElsaPmsKundenModule),
      },
      {
        path: 'app-function',
        loadChildren: () => import('./app-function/app-function.module').then(m => m.ElsaAppFunctionModule),
      },
      {
        path: 'pms-buch',
        loadChildren: () => import('./pms-buch/pms-buch.module').then(m => m.ElsaPmsBuchModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ElsaEntityModule {}
