/* eslint-disable*/
import {AfterViewInit, Component, OnInit} from '@angular/core';
import * as L from 'leaflet'
import * as moment from "moment";

import {PoubelleService} from "app/entities/poubelle/poubelle.service";
import {SituationService} from "app/entities/situation/situation.service";
import {MessageService} from "primeng/api";
import {Moment} from "moment";

@Component({
  selector: 'jhi-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit, AfterViewInit {
  poubelles = L.layerGroup([]);
  date : Moment = moment();
  map?: L.Map;
  icon = L.icon({
    iconUrl: '../../../content/images/poubelle-verte.png',
    iconSize: [30, 50],
    iconAnchor: [22, 94],
    popupAnchor: [-3, -76],
    shadowSize: [68, 95],
    shadowAnchor: [22, 94]
  });


  constructor(private poubelleService: PoubelleService, private messageService: MessageService, private situationService: SituationService) {
  }

  ngOnInit(): void {
    this.loadSituation(this.date);
  }

  ngAfterViewInit(): void {
    this.initMap()
  }

  loadSituation({date=this.date}:{date?: any}={})  {
    this.map?.removeLayer(this.poubelles)
    this.situationService.getSituationByDate(moment(this.date).format('YYYY-MM-DD')).subscribe(res => {
      if (res.body!.length <= 0) this.messageService.add({
        severity: 'info',
        summary: 'Aucune situation',
        detail: '',
        life: 3000
      });

      res.body!.forEach(p => {
        if (p.poubelle!.localisation?.latitude && p.poubelle!.localisation.logitude) {
          const marker = L.marker([p.poubelle!.localisation!.latitude!, p.poubelle!.localisation?.logitude!], {icon: this.icon})
          marker.bindPopup(`
                <table mdbTable borderless="true">
                <tr>
                   <th>Poubelle</th>
                   <th>:</th>
                   <th class="text-black-50">${p.poubelle!.ref_okko}</th>
                </tr>
                <tr>
                   <th>Préfecture</th>
                   <th>:</th>
                   <th class="text-black-50">${p.poubelle!.localisation?.collectivite_2}</th>
                </tr>
                <tr>
                   <th>Commune</th>
                   <th>:</th>
                   <th class="text-black-50">${p.poubelle!.localisation?.commune}</th>
                </tr>
                 <tr>
                   <th>Adresse</th>
                   <th>:</th>
                   <th class="text-black-50">${p.poubelle!.localisation?.adresse}</th>
                </tr>
                <tr>
                   <th>Produit</th>
                   <th>:</th>
                   <th class="text-black-50">${p.poubelle!.ref_produit}</th>
                </tr>
                <tr>
                   <th>Volume</th>
                   <th>:</th>
                   <th class="text-black-50">${p.volume} m³</th>
                </tr>

                <tr>
                   <th>Taux de remplissage</th>
                   <th>:</th>
                   <th>
                    <div class="progress-bar bg-info" role="progressbar" [style]="width:{{$p.remplissage}}%" aria-valuenow="34" aria-valuemin="0" aria-valuemax="100">
                           ${p.remplissage} %
                    </div>
                   </th>
                </tr>
                </table>
        `)
          this.poubelles.addLayer(marker);
        }
        this.map!.addLayer(this.poubelles);
      })
    })
  }

  private initMap(): void {

    this.poubelleService.getAll().subscribe(res => {
      this.map = L.map('map', {
        center: [33.9715904, -6.8498129],
        zoom: 13
      });

      let Esri_WorldImagery = L.tileLayer('https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', {
        attribution: '&copy; E.N.S.I.A.S'
      });

      Esri_WorldImagery.addTo(this.map);
      L.control.layers({"Esri_WorldImagery": Esri_WorldImagery}).addTo(this.map);
    })

  }

}


