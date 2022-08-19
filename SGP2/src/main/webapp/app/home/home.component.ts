/* eslint-disable*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import {PoubelleService} from "app/entities/poubelle/poubelle.service";
import {CommuneTotalPoubelle, IPoubelle, Poubelle} from "app/shared/model/poubelle.model";
import {ISituation, Situation} from "app/shared/model/situation.model";
import {SituationService} from "app/entities/situation/situation.service";
import * as moment from "moment";
import {LocalisationService} from "app/entities/localisation/localisation.service";
@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  totalPoubelles?:number;
  totalPoubelleByCommune:CommuneTotalPoubelle[]=[];
  situationPoubelle:ISituation[]=[];
  SelectedPoubelle?: IPoubelle;
  poubelles:IPoubelle[]=[]
  mois?:any;
  anne?:any;
  moisList=['Janvier',"Février","Mars","Avril",'Mai','Juin','Juillet','Août','Septembre','Octobre','Novembre','Décembre']
  listMois:any[]=[];
  listAnne:any[]=[]
  totalCommune?:number;
  basicData: any;
  basicOptions: any;
  SelectedPoubelleMoyenne?: IPoubelle;
  anneMoyenne: any;

  constructor(private accountService: AccountService, private situationService:SituationService,
              private loginModalService: LoginModalService, private poubelleService:PoubelleService,
              private localisationService:LocalisationService
              ) {

    for (let i=1;i<=12;i++)
    {
      this.listMois.push({
        mois:this.moisList[i-1],
        value:i
      })
    }
    for (let i=2019; i<= 2019+(moment().year()-2019);i++)
    {
      this.listAnne.push({
        anne:i
      })
    }
    this.mois=this.listMois[0]
    this.anne={
      anne:moment().year()
    }
    this.anneMoyenne=this.anne;


  }

  ngOnInit(): void {
    this.basicData = {
      labels: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet','Août','Septembre','Octobre','Novembre','Décembre'],
      datasets: []
    }
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.localisationService.getTotalCommune().subscribe(res=>{
      this.totalCommune=res.body!.totalCommune
    })

    this.poubelleService.getAll().subscribe(poubelles=>{
      this.poubelles=[...poubelles.body!]
      this.SelectedPoubelle=this.poubelles[0];
      this.SelectedPoubelleMoyenne=this.poubelles[0];
      this.totalPoubelles=poubelles.body!.length;
      this.getData()
      this.getMoyenne()
      this.poubelleService.getTotalPoubelleByCommune().subscribe(res=>
      {
        this.totalPoubelleByCommune=[...res.body!];
      })
    })

  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }


  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  getPercentage(n:number,N:number): any
  {
    return ((n/N)*100).toFixed(2);
  }

  getData()
  {
    this.situationService.situationBuPoubelle({mois:this.mois.value,anne:this.anne.anne,idPoubelle:this.SelectedPoubelle?.id,totalCommune:null}).subscribe(res=>
    {
      this.situationPoubelle=[...res.body!]
    })
  }

  getMoyenne(): void
  {
    this.situationService.getMoyenne({
        mois:null,
        anne:this.anneMoyenne.anne,
        idPoubelle:this.SelectedPoubelleMoyenne?.id,
        totalCommune:null
      }
    ).subscribe(res=> {
        let data:any[]=[];
        res.body!.forEach(m=>{
          data.push(m.moyenne!);
        })
        const datasets=[{
          label: 'Taux Remplissage Moyen',
          data: data,
          fill: false,
          borderColor: '#b22222'
        }]
        this.basicData.datasets=[...datasets];
        this.basicData={...this.basicData};
      }
    )
  }
}
