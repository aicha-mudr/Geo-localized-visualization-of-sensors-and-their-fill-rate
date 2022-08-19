import { ILocalisation } from 'app/shared/model/localisation.model';

export interface CommuneTotalPoubelle{
  commune: string;
  totalPoubelle:number;
}

export interface IPoubelle {
  id?: number;
  ref_okko?: string;
  ref_mineris?: string;
  ref_produit?: string;
  localisation?: ILocalisation;
}

export class Poubelle implements IPoubelle {
  constructor(
    public id?: number,
    public ref_okko?: string,
    public ref_mineris?: string,
    public ref_produit?: string,
    public localisation?: ILocalisation
  ) {}
}
