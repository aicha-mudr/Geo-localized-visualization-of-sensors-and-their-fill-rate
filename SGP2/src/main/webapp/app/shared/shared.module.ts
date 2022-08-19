import { NgModule } from '@angular/core';
import { SgpDbSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { UploadComponent } from './upload/upload.component';
import {FileUploadModule} from "primeng/fileupload";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";
import {ProgressSpinnerModule} from "primeng/progressspinner";
import { MapComponent } from './map/map.component';

@NgModule({
  imports: [SgpDbSharedLibsModule, FileUploadModule, ToastModule, ProgressSpinnerModule],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, UploadComponent, MapComponent],
  entryComponents: [LoginModalComponent],
  exports: [
    SgpDbSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
  ],
  providers:[MessageService]
})
export class SgpDbSharedModule {}
