import {Component, Injectable, OnInit} from '@angular/core';
import leafletKnn from '../../../../../node_modules/leaflet-knn/leaflet-knn.js';
import {MapInfraStationService} from "@app/core/services/map/map-infraStation.service";
import {CommonParam} from "@app/core/app-common-param";
declare let L;
@Injectable({
  providedIn: 'root'
})
export class StationsComponent implements OnInit {

  constructor(private infraStationService: MapInfraStationService, private commonParam: CommonParam) {
  }

  ngOnInit() {
  }


  res: any = {
    'stations': [
      {
        'type': 'FeatureCollection',
        'features': [
          {
            'type': 'Feature',
            'geometry': {
              'type': 'Point',
              'coordinates': [
                105.805870056152,
                21.0074901580811
              ],
            },
            'properties': {
              'station_id': 123656,
              'station_code': 'HNI7257',
              'status': 1,
              'owner_id': 182
            }
          }]
      }]
  };

  reloadDataStation(bounds, zoom, mapComponent) {
    console.log('reloadDataStation');
    const data = {};
    data['maxX'] = bounds.getNorthWest().lat;
    data['maxY'] = bounds.getNorthWest().lng;
    data['minX'] = bounds.getSouthEast().lat;
    data['minY'] = bounds.getSouthEast().lng;
    data['zoom'] = zoom;
    const component = this;
    const output = this.infraStationService.findByBbox(data).subscribe(res => {
      if (mapComponent.overLayers['Stations'] != undefined) {
        // groupLayer.removeLayer(overLayers['Stations']);
        mapComponent.overLayers['Stations'].clearLayers();
      }
      mapComponent.overLayers['Stations'] = L.geoJSON(res.stations[0].features, {
        pointToLayer: function (feature, latlng) {
          const type = 'station';
          let marker =
            L.marker(latlng, {
              icon: mapComponent.getStationOptions(feature, mapComponent)
              // ,
              // contextmenu: true,
              // contextmenuItems: mapComponent.createMenuActionForLayer(type)
            });
          return marker;
        },
        onEachFeature: function (feature, layer) {
          layer.bindTooltip(feature.properties.station_code);
        }
      });
      if (mapComponent.addToMaped.Stations) {
        mapComponent.overLayers['Stations'].addTo(mapComponent.map);
        mapComponent.map.addLayer(mapComponent.overLayers['Stations']);
      } else {
        mapComponent.map.removeLayer(mapComponent.overLayers['Stations']);
      }
      mapComponent.addLayerToPanelLayer('Stations');
      mapComponent.indexSearchStation = leafletKnn(mapComponent.overLayers['Stations']);
      mapComponent.overLayers['Stations'].getLayers().forEach(function (layer) {
        if (layer._icon != undefined) {
          L.DomUtil.addClass(layer._icon, 'stationClass');
        }
        layer.on(
          'contextmenu',
          function (e) {
            mapComponent.isShowContextMenu = false;
            mapComponent.currentlyEditingId = layer._leaflet_id;
            mapComponent.clearSelectedObject()
            mapComponent.changeSizeIcon(layer);
            // mapComponent.onHighlightObject(layer);
            mapComponent.createMenuActionForLayer('stations', layer, e);
          });
        layer.on(
          'dragend',
          function (e) {
            const stationForm = {};
            stationForm['stationId'] = this.feature.properties.station_id;
            stationForm['stationCode'] = this.feature.properties.station_code;
            stationForm['lngLat'] = this._latlng;
            mapComponent.searchs = mapComponent.indexSearchStation.nearest([this.feature.geometry.coordinates[0], this.feature.geometry.coordinates[1]], 10000, 10);
            component.updateStationObject(stationForm, mapComponent, layer);
          });

        layer.on(
          'click',
          function (e) {
            mapComponent.disableContextMenu();
            mapComponent.clearSelectedObject();
            mapComponent.layerSelected=layer;
            // mapComponent.map.setView(this._latlng, mapComponent.map.getZoom());
              mapComponent.changeSizeIcon(layer);
            if (mapComponent.itemAdd == 'cableEdit') {
              if (!mapComponent.cable.sourceId) {
                mapComponent.cable.sourceId = this.feature.properties.station_id;
                mapComponent.cable.source = this._latlng;
                mapComponent.cable.sourceCode = this.feature.properties.station_code;
                document.body.style.cursor = 'default';
                document.getElementById('map').style.cursor = 'default';
              } else {
                if (mapComponent.cable.dest && mapComponent.cable.dest != this._latlng) {
                  mapComponent.clearCable(mapComponent.cable.layer);
                }
                mapComponent.cable.destId = this.feature.properties.station_id;
                mapComponent.cable.dest = this._latlng;
                mapComponent.cable.destCode = this.feature.properties.station_code;
                mapComponent.addCable([mapComponent.cable.source, mapComponent.cable.dest]);
              }
            } else {
              component.commonParam.onChange(false);
              let data = {action: 'view', data: layer.feature.properties};
              component.commonParam.showProperties('stationsview', data);
            }
          });
      });
    });
  }

  updateStationObject(data, mapComponent, layer) {
    const output = this.infraStationService.update(data).subscribe(res => {
      if ("OK" == res.result) {
        // layer.editing.disable();
        layer.disableEdit();
        this.reloadDataStation(mapComponent.map.getBounds(), mapComponent.map.getZoom(), mapComponent);
      } else {
        alert(res.result);
      }
    });
  }


  greenIcon = L.icon({
    iconUrl: 'assets/img/icon/station.png',
    iconSize: [40, 40], // size of the icon
  });

  getColorForStation(ownerId, mapComponent) {
    mapComponent.mapCfg.forEach(function (val) {
      if (ownerId == val.ownerId) {
        let myIcon = L.icon({
          iconUrl: val.station_icon,
          iconSize: [20, 20],
          iconAnchor: new L.Point(16, 16)
        });
        return myIcon;
      } else {
        let myIcon = L.icon({
          iconUrl: 'assets/img/icon/Station1.png',
          iconSize: [20, 20],
          iconAnchor: new L.Point(16, 16)
        });
        return myIcon;
      }
    })
    //   switch (status) {
    //     case 0:
    //       return '#4131ff';
    //     case 1:
    //       return '#ff6802';
    //     case 2:
    //       return '#ccff47';
    //     default:
    //       return '#ff1822';
    //   }
  }
}
