import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPoubelle } from 'app/shared/model/poubelle.model';
import { PoubelleService } from './poubelle.service';

@Component({
  templateUrl: './poubelle-delete-dialog.component.html',
})
export class PoubelleDeleteDialogComponent {
  poubelle?: IPoubelle;

  constructor(protected poubelleService: PoubelleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.poubelleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('poubelleListModification');
      this.activeModal.close();
    });
  }
}
