import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StationComponent {
  features: Array<any> = [{
    'type': 'Feature',
    'properties': {
      'station_id': 'Coors Field',
      'station_code': 'Baseball Stadium',
      'sub_status': 'Baseball Stadium',
      'status': '0'
    },
    'geometry': {
      'type': 'Point',
      'coordinates': [21.0052455, 105.8017395]
    }

  }, {
    'type': 'Feature',
    'properties': {
      'station_id': 'Coors Field',
      'station_code': 'Baseball Stadium',
      'sub_status': 'This is where the Rockies play!',
      'status': '1'
    },

    'geometry': {
      'type': 'Point',
      'coordinates': [21.0131923, 105.810063]
    }
  }];


  constructor() {
  }


  drawStation(bounds, zoom, layer, L, that) {
    // data['maxX'] = bounds.getNorthWest().lat;
    // data['maxY'] = bounds.getNorthWest().lng;
    // data['minX'] = bounds.getSouthEast().lat;
    // data['minY'] = bounds.getSouthEast().lng;
    // data['zoom'] = zoom;
    const component = this;
    console.log(layer);
    if (layer != undefined) {
      layer.clearLayers();
    }
    this.features.forEach(res => {
      layer = L.marker(res.geometry.coordinates);
      that.map.addLayer(layer);
      layer.bindTooltip(res.properties.sub_status);
      layer.addTo(that.map);
      layer.on(
        'contextmenu',
        function (e) {
          that.clearSelectedObject();
          that.onHighlightObject(layer);
          that.createMenuActionForLayer(layer, e);
        });
      layer.on(
        'dragend',
        function (e) {
          const stationForm = {};
          stationForm['stationId'] = res.properties.station_id;
          stationForm['stationCode'] = res.properties.station_code;
          stationForm['lngLat'] = that._latlng;
        });
    });
  }

}
