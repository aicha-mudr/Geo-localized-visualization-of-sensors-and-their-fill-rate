/* eslint-disable*/

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPoubelle, Poubelle } from 'app/shared/model/poubelle.model';
import { PoubelleService } from './poubelle.service';
import { ILocalisation } from 'app/shared/model/localisation.model';
import { LocalisationService } from 'app/entities/localisation/localisation.service';

@Component({
  selector: 'jhi-poubelle-update',
  templateUrl: './poubelle-update.component.html',
})
export class PoubelleUpdateComponent implements OnInit {
  isSaving = false;
  localisations: ILocalisation[] = [];

  editForm = this.fb.group({
    id: [],
    ref_okko: [null, []],
    ref_mineris: [],
    ref_produit: [],
    localisation: [],
  });

  constructor(
    protected poubelleService: PoubelleService,
    protected localisationService: LocalisationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poubelle }) => {
      this.updateForm(poubelle);

      this.localisationService.query().subscribe((res: HttpResponse<ILocalisation[]>) => (this.localisations = res.body || []));
    });
  }

  updateForm(poubelle: IPoubelle): void {
    this.editForm.patchValue({
      id: poubelle.id,
      ref_okko: poubelle.ref_okko,
      ref_mineris: poubelle.ref_mineris,
      ref_produit: poubelle.ref_produit,
      localisation: poubelle.localisation,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const poubelle = this.createFromForm();
    if (poubelle.id !== undefined) {
      this.subscribeToSaveResponse(this.poubelleService.update(poubelle));
    } else {
      this.subscribeToSaveResponse(this.poubelleService.create(poubelle));
    }
  }

  private createFromForm(): IPoubelle {
    return {
      ...new Poubelle(),
      id: this.editForm.get(['id'])!.value,
      ref_okko: this.editForm.get(['ref_okko'])!.value,
      ref_mineris: this.editForm.get(['ref_mineris'])!.value,
      ref_produit: this.editForm.get(['ref_produit'])!.value,
      localisation: this.editForm.get(['localisation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoubelle>>): void {
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

  trackById(index: number, item: ILocalisation): any {
    return item.id;
  }
}
