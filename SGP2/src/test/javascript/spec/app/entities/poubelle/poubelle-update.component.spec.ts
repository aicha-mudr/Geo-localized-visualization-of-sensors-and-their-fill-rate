import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SgpDbTestModule } from '../../../test.module';
import { PoubelleUpdateComponent } from 'app/entities/poubelle/poubelle-update.component';
import { PoubelleService } from 'app/entities/poubelle/poubelle.service';
import { Poubelle } from 'app/shared/model/poubelle.model';

describe('Component Tests', () => {
  describe('Poubelle Management Update Component', () => {
    let comp: PoubelleUpdateComponent;
    let fixture: ComponentFixture<PoubelleUpdateComponent>;
    let service: PoubelleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SgpDbTestModule],
        declarations: [PoubelleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PoubelleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PoubelleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoubelleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Poubelle(123);
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
        const entity = new Poubelle();
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
