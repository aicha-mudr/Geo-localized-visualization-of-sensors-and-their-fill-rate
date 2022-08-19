import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoubelle } from 'app/shared/model/poubelle.model';

@Component({
  selector: 'jhi-poubelle-detail',
  templateUrl: './poubelle-detail.component.html',
})
export class PoubelleDetailComponent implements OnInit {
  poubelle: IPoubelle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poubelle }) => (this.poubelle = poubelle));
  }

  previousState(): void {
    window.history.back();
  }
}
