/* eslint-disable*/

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILocalisation, Localisation } from 'app/shared/model/localisation.model';
import { LocalisationService } from './localisation.service';

@Component({
  selector: 'jhi-localisation-update',
  templateUrl: './localisation-update.component.html',
})
export class LocalisationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    'collectivite_1': [],
    'collectivite_2': [],
    commune: [],
    adresse: [],
    logitude: [],
    latitude: [],
  });

  constructor(protected localisationService: LocalisationService,
              protected activatedRoute: ActivatedRoute,
              private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ localisation }) => {
      this.updateForm(localisation);
    });

  }

  updateForm(localisation: ILocalisation): void {
    this.editForm.patchValue({
      id: localisation.id,
      collectivite_1: localisation.collectivite_1,
      collectivite_2: localisation.collectivite_2,
      commune: localisation.commune,
      adresse: localisation.adresse,
      logitude: localisation.logitude,
      latitude: localisation.latitude,
    });
  }
  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const localisation = this.createFromForm();
    if (localisation.id !== undefined) {
      this.subscribeToSaveResponse(this.localisationService.update(localisation));
    } else {
      this.subscribeToSaveResponse(this.localisationService.create(localisation));
    }
  }

  private createFromForm(): ILocalisation {
    return {
      ...new Localisation(),
      id: this.editForm.get(['id'])!.value,
      collectivite_1: this.editForm.get(['collectivite_1'])!.value,
      collectivite_2: this.editForm.get(['collectivite_2'])!.value,
      commune: this.editForm.get(['commune'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      logitude: this.editForm.get(['logitude'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalisation>>): void {
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
}
