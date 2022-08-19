export interface ILocalisation {
  id?: number;
  collectivite_1?: string;
  collectivite_2?: string;
  commune?: string;
  adresse?: string;
  logitude?: number;
  latitude?: number;
}

export interface IData
{
  mois: number,
  anne: number,
  idPoubelle: number,
  totalCommune: number
}

export class Localisation implements ILocalisation {
  constructor(
    public id?: number,
    public collectivite_1?: string,
    public collectivite_2?: string,
    public commune?: string,
    public adresse?: string,
    public logitude?: number,
    public latitude?: number
  ) {}
}
