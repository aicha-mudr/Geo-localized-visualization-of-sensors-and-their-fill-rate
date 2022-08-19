import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SgpDbSharedModule } from 'app/shared/shared.module';
import { LocalisationComponent } from './localisation.component';
import { LocalisationDetailComponent } from './localisation-detail.component';
import { LocalisationUpdateComponent } from './localisation-update.component';
import { LocalisationDeleteDialogComponent } from './localisation-delete-dialog.component';
import { localisationRoute } from './localisation.route';

@NgModule({
  imports: [SgpDbSharedModule, RouterModule.forChild(localisationRoute)],
  declarations: [LocalisationComponent, LocalisationDetailComponent, LocalisationUpdateComponent, LocalisationDeleteDialogComponent],
  entryComponents: [LocalisationDeleteDialogComponent],
})
export class SgpDbLocalisationModule {}
