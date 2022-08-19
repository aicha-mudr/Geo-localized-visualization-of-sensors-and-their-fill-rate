import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SgpDbSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import {DropdownModule} from "primeng/dropdown";
import {ButtonModule} from "primeng/button";
import {ChartModule} from "primeng/chart";

@NgModule({
    imports: [SgpDbSharedModule, RouterModule.forChild([HOME_ROUTE]), DropdownModule, ButtonModule, ChartModule],
  declarations: [HomeComponent],
})
export class SgpDbHomeModule {}
