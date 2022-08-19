import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { UploadComponent } from 'app/shared/upload/upload.component';
import {MapComponent} from "app/shared/map/map.component";
import {Authority} from "app/shared/constants/authority.constants";

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'situation',
        loadChildren: () => import('./situation/situation.module').then(m => m.SgpDbSituationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
      {
        path: '',
        data: {
          title: 'Dashboard',
        },
        canActivate: [UserRouteAccessService],
        children: [
          {
            path:'map',
            component:MapComponent,
            canActivate: [UserRouteAccessService],
            data:{
              authorities: [Authority.ADMIN,Authority.USER],
              title:'Map'
            }
          },
          {
            path: 'poubelle',
            loadChildren: () => import('./poubelle/poubelle.module').then(m => m.SgpDbPoubelleModule),
          },
          {
            path: 'localisation',
            loadChildren: () => import('./localisation/localisation.module').then(m => m.SgpDbLocalisationModule),
          },
          {
            path: '',
            redirectTo: '/Dashboard',
            pathMatch: 'full',
            data: {
              title: 'Dashboard',
            },
          },
          {
            path: 'upload',
            component: UploadComponent,
            data: {
              title: 'Upload',
            },
            canActivate: [UserRouteAccessService],
          },
        ],
      },
    ]),
  ],
})
export class SgpDbEntityModule {}
