import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SgpDbSharedModule } from 'app/shared/shared.module';
import { SituationComponent } from './situation.component';
import { SituationDetailComponent } from './situation-detail.component';
import { SituationUpdateComponent } from './situation-update.component';
import { SituationDeleteDialogComponent } from './situation-delete-dialog.component';
import { situationRoute } from './situation.route';

@NgModule({
  imports: [SgpDbSharedModule, RouterModule.forChild(situationRoute)],
  declarations: [SituationComponent, SituationDetailComponent, SituationUpdateComponent, SituationDeleteDialogComponent],
  entryComponents: [SituationDeleteDialogComponent],
})
export class SgpDbSituationModule {}
