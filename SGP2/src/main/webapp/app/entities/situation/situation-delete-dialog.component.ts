import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISituation } from 'app/shared/model/situation.model';
import { SituationService } from './situation.service';

@Component({
  templateUrl: './situation-delete-dialog.component.html',
})
export class SituationDeleteDialogComponent {
  situation?: ISituation;

  constructor(protected situationService: SituationService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.situationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('situationListModification');
      this.activeModal.close();
    });
  }
}
