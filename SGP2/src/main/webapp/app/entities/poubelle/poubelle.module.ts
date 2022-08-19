import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SgpDbSharedModule } from 'app/shared/shared.module';
import { PoubelleComponent } from './poubelle.component';
import { PoubelleDetailComponent } from './poubelle-detail.component';
import { PoubelleUpdateComponent } from './poubelle-update.component';
import { PoubelleDeleteDialogComponent } from './poubelle-delete-dialog.component';
import { poubelleRoute } from './poubelle.route';

@NgModule({
  imports: [SgpDbSharedModule, RouterModule.forChild(poubelleRoute)],
  declarations: [PoubelleComponent, PoubelleDetailComponent, PoubelleUpdateComponent, PoubelleDeleteDialogComponent],
  entryComponents: [PoubelleDeleteDialogComponent],
})
export class SgpDbPoubelleModule {}
