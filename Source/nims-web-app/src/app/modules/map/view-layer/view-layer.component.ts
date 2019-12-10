import {Component, Input, OnInit} from '@angular/core';
import {VIEW_LAYER_CHECKBOX} from "@app/shared/services/constants";
import {CommonParam} from "@app/core/app-common-param";

@Component({
  selector: 'view-layer',
  templateUrl: './view-layer.component.html',
  styleUrls: ['./view-layer.component.css']
})
export class ViewLayerComponent implements OnInit {

  @Input() data;
  listObjectCheckBoxes : any[] = [];
  selectedNames: string[] = [];
  constructor(private commonParam: CommonParam) { }

  ngOnInit() {
    this.listObjectCheckBoxes = VIEW_LAYER_CHECKBOX;
    this.selectedNames = this.data;
  }

  viewLayer(){
    this.commonParam.viewLayer(this.selectedNames);
  }
  closePopup(){
    this.commonParam.closePopupLeft();
  }
}
