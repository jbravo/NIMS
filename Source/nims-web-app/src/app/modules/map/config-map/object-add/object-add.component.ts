import {Component, OnInit, Type} from '@angular/core';
import {AutocompleteSelectObjComponent} from "@app/modules/map/config-map/autocomplete-select-obj/autocomplete-select-obj.component";

@Component({
  selector: 'object-add',
  templateUrl: './object-add.component.html',
  styleUrls: ['./object-add.component.css']
})
export class ObjectAddComponent implements OnInit {

  groupObjects;
  selectedRecord;
  obj: Type<any> = AutocompleteSelectObjComponent;
  constructor() { }

  ngOnInit() {
  }

}
