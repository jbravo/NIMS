import {Component, Input, OnInit} from '@angular/core';
import {CommonParam} from "@app/core/app-common-param";

@Component({
  selector: 'list-view-map',
  templateUrl: './list-view-map.component.html',
  styleUrls: ['./list-view-map.component.css']
})
export class ListViewMapComponent implements OnInit {
  @Input() items: any

  constructor(private commonParam: CommonParam) {
  }

  ngOnInit() {
  }

  clickItem(item) {
    if (item.action ==='view'){
      this.commonParam.showProperties(item.type + '' +item.action, item);
    }else {
      this.commonParam.showProperties(item.type, item);
    }
    this.commonParam.onChange(false);
    this.commonParam.focusItem(item);
  }

}
export interface Items {
  id: string
  text: string
  data: any
  type: string
  action: string
}
