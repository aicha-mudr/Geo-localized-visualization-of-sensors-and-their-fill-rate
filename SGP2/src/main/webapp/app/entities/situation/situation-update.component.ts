import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISituation, Situation } from 'app/shared/model/situation.model';
import { SituationService } from './situation.service';
import { IPoubelle } from 'app/shared/model/poubelle.model';
import { PoubelleService } from 'app/entities/poubelle/poubelle.service';

@Component({
  selector: 'jhi-situation-update',
  templateUrl: './situation-update.component.html',
})
export class SituationUpdateComponent implements OnInit {
  isSaving = false;
  poubelles: IPoubelle[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    volume: [],
    remplissage: [],
    poubelle: [],
  });

  constructor(
    protected situationService: SituationService,
    protected poubelleService: PoubelleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ situation }) => {
      this.updateForm(situation);

      this.poubelleService.query().subscribe((res: HttpResponse<IPoubelle[]>) => (this.poubelles = res.body || []));
    });
  }

  updateForm(situation: ISituation): void {
    this.editForm.patchValue({
      id: situation.id,
      date: situation.date,
      volume: situation.volume,
      remplissage: situation.remplissage,
      poubelle: situation.poubelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const situation = this.createFromForm();
    if (situation.id !== undefined) {
      this.subscribeToSaveResponse(this.situationService.update(situation));
    } else {
      this.subscribeToSaveResponse(this.situationService.create(situation));
    }
  }

  private createFromForm(): ISituation {
    return {
      ...new Situation(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      volume: this.editForm.get(['volume'])!.value,
      remplissage: this.editForm.get(['remplissage'])!.value,
      poubelle: this.editForm.get(['poubelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISituation>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPoubelle): any {
    return item.id;
  }
}
