import { Route } from '@angular/router';

import { HomeComponent } from './home.component';
import {UserRouteAccessService} from "app/core/auth/user-route-access-service";
import {Authority} from "app/shared/constants/authority.constants";

export const HOME_ROUTE: Route = {
  path: 'Dashboard',
  canActivate:[UserRouteAccessService],
  component: HomeComponent,
  data: {
    authorities: [Authority.USER],
    pageTitle: 'home.title',
    title:'Dashboard'
  },
};
