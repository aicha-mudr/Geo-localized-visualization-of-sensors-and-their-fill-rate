/* eslint-disable */
import { Component, OnInit } from '@angular/core';
import {MessageService} from "primeng/api";
import {PoubelleService} from "app/entities/poubelle/poubelle.service";
@Component({
  selector: 'jhi-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss']
})

export class UploadComponent implements OnInit {
  uploadedFiles: any[] = [];
  currentFileUpload?: File;
  progress: { percentage: number } = { percentage: 0 };
  display= false;
  constructor(private messageService: MessageService, private poubelleService:PoubelleService) { }

  ngOnInit(): void {
  }
  onUpload(event:any): void{
    this.display=true;
    this.poubelleService.upload(event).subscribe(res=>{
      this.messageService.add({severity: 'success', summary: 'Données Chargées', detail: '',life:3000});
      this.display=false;
    })
  }

}
