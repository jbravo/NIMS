import {Component, Input, OnInit} from '@angular/core';
import {MapCablesService} from '@app/layouts/map-cables/service/map-cables.service';

@Component({
  selector: 'map-cables',
  templateUrl: './map-cables.component.html',
  styleUrls: ['./map-cables.component.css']
})
export class MapCablesComponent implements OnInit {

  @Input() data;
  result: any;

  constructor(private mapCablesService: MapCablesService) {
  }

  ngOnInit() {
    this.mapCablesService.findCableById(this.data.properties.cable_id).subscribe(res => {
      // this.result = res.content;
    });
  }

  copyData(event) {
  }
}
