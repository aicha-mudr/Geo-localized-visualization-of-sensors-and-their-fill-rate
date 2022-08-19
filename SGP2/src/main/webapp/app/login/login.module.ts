import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginComponent } from './login.component';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {LOGIN_ROUTE} from "./login.route";
import {SgpDbSharedModule} from "../shared/shared.module";

@NgModule({
  declarations: [LoginComponent],
  imports: [CommonModule, RouterModule.forChild([LOGIN_ROUTE]), FontAwesomeModule, SgpDbSharedModule],
  schemas: [NO_ERRORS_SCHEMA],
  exports: [
    LoginComponent
  ]
})
export class LoginModule {}
