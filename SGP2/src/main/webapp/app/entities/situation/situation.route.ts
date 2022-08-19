import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISituation, Situation } from 'app/shared/model/situation.model';
import { SituationService } from './situation.service';
import { SituationComponent } from './situation.component';
import { SituationDetailComponent } from './situation-detail.component';
import { SituationUpdateComponent } from './situation-update.component';

@Injectable({ providedIn: 'root' })
export class SituationResolve implements Resolve<ISituation> {
  constructor(private service: SituationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISituation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((situation: HttpResponse<Situation>) => {
          if (situation.body) {
            return of(situation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Situation());
  }
}

export const situationRoute: Routes = [
  {
    path: '',
    component: SituationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sgpDbApp.situation.home.title',
      title:'Situations'
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: '',
    data: {
      title: 'Situations'
    },
    children: [
      {
        path: ':id/view',
        component: SituationDetailComponent,
        resolve: {
          situation: SituationResolve,
        },
        data: {
          authorities: [Authority.USER],
          pageTitle: 'sgpDbApp.situation.home.title',
          title:'Détails'
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'new',
        component: SituationUpdateComponent,
        resolve: {
          situation: SituationResolve,
        },
        data: {
          authorities: [Authority.USER],
          pageTitle: 'sgpDbApp.situation.home.title',
          title: 'Création'
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: ':id/edit',
        component: SituationUpdateComponent,
        resolve: {
          situation: SituationResolve,
        },
        data: {
          authorities: [Authority.USER],
          pageTitle: 'sgpDbApp.situation.home.title',
          title:'Modification'
        },
        canActivate: [UserRouteAccessService],
      },
    ]
  },

];
