import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import {CommuneTotalPoubelle, IPoubelle, Poubelle} from 'app/shared/model/poubelle.model';

type EntityResponseType = HttpResponse<IPoubelle>;
type EntityArrayResponseType = HttpResponse<IPoubelle[]>;

@Injectable({ providedIn: 'root' })
export class PoubelleService {
  public resourceUrl = SERVER_API_URL + 'api/poubelles';

  constructor(protected http: HttpClient) {}

  create(poubelle: IPoubelle): Observable<EntityResponseType> {
    return this.http.post<IPoubelle>(this.resourceUrl, poubelle, { observe: 'response' });
  }

  update(poubelle: IPoubelle): Observable<EntityResponseType> {
    return this.http.put<IPoubelle>(this.resourceUrl, poubelle, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPoubelle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPoubelle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  upload(event:any):Observable<EntityArrayResponseType>
  {
    const formData: FormData = new FormData();
    formData.append('file',event.files[0]);
    return this.http.post<Poubelle[]>(`${this.resourceUrl}/upload`,formData,{observe:"response"})
  }

  getTotalPoubelleByCommune():Observable<HttpResponse<CommuneTotalPoubelle[]>>
  {
    return this.http.get<CommuneTotalPoubelle[]>(`${this.resourceUrl}/total-poubelle-by-commune`,{observe:"response"})
  }

  getAll(): Observable<EntityArrayResponseType> {
    return this.http.get<IPoubelle[]>(`${this.resourceUrl}/all`, {observe: 'response' });
  }
}
