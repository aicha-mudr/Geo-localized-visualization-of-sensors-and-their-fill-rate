/* eslint-disable */
import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ActivatedRouteSnapshot, Resolve, Router, Routes} from '@angular/router';
import {EMPTY, Observable, of} from 'rxjs';
import {flatMap} from 'rxjs/operators';

import {Authority} from 'app/shared/constants/authority.constants';
import {UserRouteAccessService} from 'app/core/auth/user-route-access-service';
import {ILocalisation, Localisation} from 'app/shared/model/localisation.model';
import {LocalisationService} from './localisation.service';
import {LocalisationComponent} from './localisation.component';
import {LocalisationUpdateComponent} from './localisation-update.component';

@Injectable({ providedIn: 'root' })
export class LocalisationResolve implements Resolve<ILocalisation> {
  constructor(private service: LocalisationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocalisation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((localisation: HttpResponse<Localisation>) => {
          if (localisation.body) {
            return of(localisation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Localisation());
  }
}

export const localisationRoute: Routes = [
  // {
  //   path: '',
  //   component: LocalisationComponent,
  //   data: {
  //     authorities: [Authority.USER],
  //     defaultSort: 'id,asc',
  //     pageTitle: 'sgpDbApp.localisation.home.title',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
  // // {
  // //   path: ':id/view',
  // //   component: LocalisationDetailComponent,
  // //   resolve: {
  // //     localisation: LocalisationResolve,
  // //   },
  // //   data: {
  // //     authorities: [Authority.USER],
  // //     pageTitle: 'sgpDbApp.localisation.home.title',
  // //   },
  // //   canActivate: [UserRouteAccessService],
  // // },
  //
  // {
  //   path: 'new',
  //   component: LocalisationUpdateComponent,
  //   resolve: {
  //     localisation: LocalisationResolve,
  //   },
  //   data: {
  //     authorities: [Authority.USER],
  //     pageTitle: 'sgpDbApp.localisation.home.title',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
  // {
  //   path: ':id/edit',
  //   component: LocalisationUpdateComponent,
  //   resolve: {
  //     localisation: LocalisationResolve,
  //   },
  //   data: {
  //     authorities: [Authority.USER],
  //     pageTitle: 'sgpDbApp.localisation.home.title',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
];
