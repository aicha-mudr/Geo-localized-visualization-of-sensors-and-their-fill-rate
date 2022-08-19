import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SgpDbTestModule } from '../../../test.module';
import { LocalisationDetailComponent } from 'app/entities/localisation/localisation-detail.component';
import { Localisation } from 'app/shared/model/localisation.model';

describe('Component Tests', () => {
  describe('Localisation Management Detail Component', () => {
    let comp: LocalisationDetailComponent;
    let fixture: ComponentFixture<LocalisationDetailComponent>;
    const route = ({ data: of({ localisation: new Localisation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SgpDbTestModule],
        declarations: [LocalisationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LocalisationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocalisationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load localisation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.localisation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
