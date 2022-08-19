import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocalisation } from 'app/shared/model/localisation.model';
import { LocalisationService } from './localisation.service';

@Component({
  templateUrl: './localisation-delete-dialog.component.html',
})
export class LocalisationDeleteDialogComponent {
  localisation?: ILocalisation;

  constructor(
    protected localisationService: LocalisationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.localisationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('localisationListModification');
      this.activeModal.close();
    });
  }
}
