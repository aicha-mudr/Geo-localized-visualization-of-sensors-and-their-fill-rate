import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LocalisationService } from 'app/entities/localisation/localisation.service';
import { ILocalisation, Localisation } from 'app/shared/model/localisation.model';

describe('Service Tests', () => {
  describe('Localisation Service', () => {
    let injector: TestBed;
    let service: LocalisationService;
    let httpMock: HttpTestingController;
    let elemDefault: ILocalisation;
    let expectedResult: ILocalisation | ILocalisation[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LocalisationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Localisation(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Localisation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Localisation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Localisation', () => {
        const returnedFromService = Object.assign(
          {
            collectivite_1: 'BBBBBB',
            collectivite_2: 'BBBBBB',
            commune: 'BBBBBB',
            adresse: 'BBBBBB',
            logitude: 1,
            latitude: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Localisation', () => {
        const returnedFromService = Object.assign(
          {
            collectivite_1: 'BBBBBB',
            collectivite_2: 'BBBBBB',
            commune: 'BBBBBB',
            adresse: 'BBBBBB',
            logitude: 1,
            latitude: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Localisation', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
