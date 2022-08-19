/* eslint-disable */
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import './vendor';
import {SgpDbSharedModule} from 'app/shared/shared.module';
import {SgpDbCoreModule} from 'app/core/core.module';
import {SgpDbAppRoutingModule} from './app-routing.module';
import {SgpDbHomeModule} from './home/home.module';
import {SgpDbEntityModule} from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import {MainComponent} from './layouts/main/main.component';
import {NavbarComponent} from './layouts/navbar/navbar.component';
import {FooterComponent} from './layouts/footer/footer.component';
import {PageRibbonComponent} from './layouts/profiles/page-ribbon.component';
import {ActiveMenuDirective} from './layouts/navbar/active-menu.directive';
import {ErrorComponent} from './layouts/error/error.component';
import {LoginModule} from "app/login/login.module";
import {AppAsideModule, AppBreadcrumbModule, AppHeaderModule, AppSidebarModule} from "@coreui/angular";
import {PerfectScrollbarModule} from "ngx-perfect-scrollbar";
import {BsDropdownModule} from "ngx-bootstrap/dropdown";
import {ComponentLoaderFactory} from "ngx-bootstrap/component-loader";
import {PositioningService} from "ngx-bootstrap/positioning";
import {TabsModule} from "ngx-bootstrap/tabs";

// @ts-ignore
@NgModule({
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    SgpDbSharedModule,
    SgpDbCoreModule,
    SgpDbHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SgpDbEntityModule,
    SgpDbAppRoutingModule,
    LoginModule,
    AppHeaderModule,
    AppSidebarModule,
    PerfectScrollbarModule,
    BsDropdownModule,
    AppBreadcrumbModule,
    AppAsideModule,
    TabsModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
  providers: [ComponentLoaderFactory, PositioningService]
})
export class SgpDbAppModule {
}
