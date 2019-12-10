import {Router} from '@angular/router';
import {MessageService} from 'primeng/primeng';
import {AfterViewInit, Component, OnInit} from '@angular/core';
import {SuggestionStationModel} from '@app/modules/suggest-station/model/suggestion-station.model';
import {SuggestionNewSiteModel} from '@app/modules/suggest-station/model/suggestion-new-site.model';
import {RadioSuggestionInforModel} from '@app/modules/suggest-station/model/radioSuggestionInfor.model';
import {SuggestionStationService} from './suggestion-station.service';
import {SuggestionCallOff2gDTOModel} from './model/Call-off/SuggestionCallOff2gDTO.model';

@Component({
  selector: 'suggestion-station',
  templateUrl: './suggestion-station.component.html',
  styleUrls: ['./suggestion-station.component.scss']
})
export class SuggestionStationComponent implements OnInit, AfterViewInit {


  suggestionNewSiteDTO: SuggestionNewSiteModel;
  radioSuggestionInforModel: RadioSuggestionInforModel;
  suggestionCallOff2gDTOModel: SuggestionCallOff2gDTOModel;

  suggestionStationModelView: SuggestionStationModel;
  suggestionNewSiteDTOView: SuggestionNewSiteModel;
  radioSuggestionInforModelView: RadioSuggestionInforModel;
  value = 0;

  testView: any;

  constructor(private route: Router,
              private messageService: MessageService,
              private suggestionStationService: SuggestionStationService
  ) {
  }

  ngOnInit() {

    this.suggestionNewSiteDTO = new SuggestionNewSiteModel();
    this.radioSuggestionInforModel = new RadioSuggestionInforModel();
    this.suggestionCallOff2gDTOModel = new SuggestionCallOff2gDTOModel;
  }

  ngAfterViewInit() {
  }

  // onChecked(e) {
  //   if (this.value1) {
  //     this.value2 = false;
  //   } else if (this.value2) {
  //     this.value1 = false;
  //   }
  // }

  // onChecked2(e) {
  //   if (this.value2) this.value1 = false;
  //   else if (this.value1) this.value2 = false;
  // }


  onRow(row) {
    console.log(row[0].suggestCode);
  }


  MoveData(data: any) {

  }

}
