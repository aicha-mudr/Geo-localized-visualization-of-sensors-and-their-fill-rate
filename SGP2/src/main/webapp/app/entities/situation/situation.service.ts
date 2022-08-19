import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import {ISituation, Moyenne} from 'app/shared/model/situation.model';

type EntityResponseType = HttpResponse<ISituation>;
type EntityArrayResponseType = HttpResponse<ISituation[]>;

@Injectable({ providedIn: 'root' })
export class SituationService {
  public resourceUrl = SERVER_API_URL + 'api/situations';

  constructor(protected http: HttpClient) {}

  create(situation: ISituation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situation);
    return this.http
      .post<ISituation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(situation: ISituation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(situation);
    return this.http
      .put<ISituation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISituation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISituation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  situationBuPoubelle(body:any): Observable<EntityArrayResponseType> {
    return this.http
      .post<ISituation[]>(this.resourceUrl+'/poubelle',body, {observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  getSituationByDate(date:any):Observable<EntityArrayResponseType>
  {
    return this.http.get<ISituation[]>(this.resourceUrl+'/by-date/'+date, { observe: 'response' });
  }
  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMoyenne(data:any):Observable<HttpResponse<Moyenne[]>>
  {
    return this.http.post<Moyenne[]>(`${this.resourceUrl}/moyen`,data,{observe:'response'})
  }

  protected convertDateFromClient(situation: ISituation): ISituation {
    const copy: ISituation = Object.assign({}, situation, {
      date: situation.date && situation.date.isValid() ? situation.date.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((situation: ISituation) => {
        situation.date = situation.date ? moment(situation.date) : undefined;
      });
    }
    return res;
  }
  getAll(): Observable<EntityArrayResponseType> {
    return this.http.get<ISituation[]>(`${this.resourceUrl}/all`, { observe: 'response' });
  }
}
