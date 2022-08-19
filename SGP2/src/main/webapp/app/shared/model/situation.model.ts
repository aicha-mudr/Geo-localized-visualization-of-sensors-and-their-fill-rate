import { Moment } from 'moment';
import { IPoubelle } from 'app/shared/model/poubelle.model';

export interface Moyenne{
  refPoubelle?:string;
  moyenne?:number;
  year?:number;
 mois?: number;
}

export interface ISituation {
  id?: number;
  date?: Moment;
  volume?: number;
  remplissage?: number;
  poubelle?: IPoubelle;
}

export class Situation implements ISituation {
  constructor(public id?: number, public date?: Moment, public volume?: number, public remplissage?: number, public poubelle?: IPoubelle) {}
}
