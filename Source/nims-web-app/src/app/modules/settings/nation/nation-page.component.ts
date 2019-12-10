import { Component, OnInit } from '@angular/core';
import { NationService } from '@app/core';

@Component({
  selector: 'nation-page',
  templateUrl: './nation-page.component.html',
  styleUrls: ['./nation-page.component.css']
})
export class NationPageComponent implements OnInit {
  resultList: any = {};
  constructor(
    private nationService: NationService) {
      console.log('constructor NationPageComponent');
  }

  ngOnInit() {
  }

}
