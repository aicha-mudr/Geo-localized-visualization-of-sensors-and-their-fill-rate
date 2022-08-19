import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPoubelle, Poubelle } from 'app/shared/model/poubelle.model';
import { PoubelleService } from './poubelle.service';
import { PoubelleComponent } from './poubelle.component';
import { PoubelleDetailComponent } from './poubelle-detail.component';
import { PoubelleUpdateComponent } from './poubelle-update.component';
import {LocalisationDetailComponent} from "../localisation/localisation-detail.component";
import {LocalisationResolve} from "../localisation/localisation.route";
import {LocalisationUpdateComponent} from "../localisation/localisation-update.component";

@Injectable({ providedIn: 'root' })
export class PoubelleResolve implements Resolve<IPoubelle> {
  constructor(private service: PoubelleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPoubelle> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((poubelle: HttpResponse<Poubelle>) => {
          if (poubelle.body) {
            return of(poubelle.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Poubelle());
  }
}

export const poubelleRoute: Routes = [
  {
    path: '',
    component: PoubelleComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sgpDbApp.poubelle.home.title',
      title:'Listes Poubelles'
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path:'',
    data:{
      title: 'Poubelles'
    },
    children:[
      {
        path: ':id/view',
        component: PoubelleDetailComponent,
        resolve: {
          poubelle: PoubelleResolve,
        },
        data: {
          authorities: [Authority.USER],
          pageTitle: 'sgpDbApp.poubelle.home.title',
          title:'Détails'
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'new',
        component: PoubelleUpdateComponent,
        resolve: {
          poubelle: PoubelleResolve,
        },
        data: {
          authorities: [Authority.USER],
          pageTitle: 'sgpDbApp.poubelle.home.title',
          title: 'Création'
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: ':id/localisation',
        component: LocalisationDetailComponent,
        resolve: {
          localisation: LocalisationResolve,
        },
        data: {
          authorities: [Authority.USER],
          pageTitle: 'sgpDbApp.localisation.home.title',
          title: 'Détail Localisation'
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: ':id/editlocalisation',
        component: LocalisationUpdateComponent,
        resolve: {
          localisation: LocalisationResolve,
        },
        data: {
          authorities: [Authority.USER],
          pageTitle: 'sgpDbApp.localisation.home.title',
          title: 'Modifier Localisation'
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: ':id/edit',
        component: PoubelleUpdateComponent,
        resolve: {
          poubelle: PoubelleResolve,
        },
        data: {
          authorities: [Authority.USER],
          pageTitle: 'sgpDbApp.poubelle.home.title',
          title:'Modification'
        },
        canActivate: [UserRouteAccessService],
      },
    ]
  }

];
