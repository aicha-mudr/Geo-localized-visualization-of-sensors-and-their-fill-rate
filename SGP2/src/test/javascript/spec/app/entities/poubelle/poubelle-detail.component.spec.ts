import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SgpDbTestModule } from '../../../test.module';
import { PoubelleDetailComponent } from 'app/entities/poubelle/poubelle-detail.component';
import { Poubelle } from 'app/shared/model/poubelle.model';

describe('Component Tests', () => {
  describe('Poubelle Management Detail Component', () => {
    let comp: PoubelleDetailComponent;
    let fixture: ComponentFixture<PoubelleDetailComponent>;
    const route = ({ data: of({ poubelle: new Poubelle(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SgpDbTestModule],
        declarations: [PoubelleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PoubelleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PoubelleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load poubelle on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.poubelle).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
