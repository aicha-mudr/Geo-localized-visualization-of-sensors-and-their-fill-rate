import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SgpDbTestModule } from '../../../test.module';
import { LocalisationUpdateComponent } from 'app/entities/localisation/localisation-update.component';
import { LocalisationService } from 'app/entities/localisation/localisation.service';
import { Localisation } from 'app/shared/model/localisation.model';

describe('Component Tests', () => {
  describe('Localisation Management Update Component', () => {
    let comp: LocalisationUpdateComponent;
    let fixture: ComponentFixture<LocalisationUpdateComponent>;
    let service: LocalisationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SgpDbTestModule],
        declarations: [LocalisationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LocalisationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocalisationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocalisationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Localisation(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Localisation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
