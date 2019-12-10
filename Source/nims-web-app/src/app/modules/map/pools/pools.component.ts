import { Component, OnInit } from '@angular/core';
import {MapInfraStationService} from "@app/core/services/map/map-infraStation.service";
import {PoolsService} from "@app/modules/map/pools/pools.service";
import leafletKnn from '../../../../../node_modules/leaflet-knn/leaflet-knn.js';
declare let L;

@Component({
  selector: 'pools',
  templateUrl: './pools.component.html',
  styleUrls: ['./pools.component.css']
})
export class PoolsComponent implements OnInit {

  constructor(private poolsService: PoolsService) { }

  ngOnInit() {
  }

  reloadDataPool(bounds, zoom, mapComponent) {
    console.log('reloadDataStation');
    const data = {};
    data['maxX'] = bounds.getNorthWest().lat;
    data['maxY'] = bounds.getNorthWest().lng;
    data['minX'] = bounds.getSouthEast().lat;
    data['minY'] = bounds.getSouthEast().lng;
    data['zoom'] = zoom;
    const component = this;
    const output = this.poolsService.findByBbox(data).subscribe(res => {
      if (mapComponent.overLayers['Pools'] != undefined) {
        // groupLayer.removeLayer(overLayers['Stations']);
        mapComponent.overLayers['Pools'].clearLayers();
      }
      mapComponent.overLayers['Pools'] = L.geoJSON(res.stations[0].features, {
        pointToLayer: function (feature, latlng) {
          const type = 'pool';
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
        mapComponent.overLayers['Pools'].addTo(mapComponent.map);
        mapComponent.map.addLayer(mapComponent.overLayers['Pools']);
      } else {
        mapComponent.map.removeLayer(mapComponent.overLayers['Pools']);
      }
      mapComponent.addLayerToPanelLayer('Pools');
      mapComponent.indexSearchStation = leafletKnn(mapComponent.overLayers['Pools']);
      mapComponent.overLayers['Pools'].getLayers().forEach(function (layer) {
        if (layer._icon != undefined) {
          L.DomUtil.addClass(layer._icon, 'poolClass');
        }
        layer.on(
          'contextmenu',
          function (e) {
            mapComponent.isShowContextMenu = false;
            mapComponent.currentlyEditingId = layer._leaflet_id;
            mapComponent.clearSelectedObject()
            mapComponent.changeSizeIcon(layer);
            // mapComponent.onHighlightObject(layer);
            mapComponent.createMenuActionForLayer('pools', layer, e);
          });
        layer.on(
          'dragend',
          function (e) {
            const stationForm = {};
            stationForm['stationId'] = this.feature.properties.station_id;
            stationForm['stationCode'] = this.feature.properties.station_code;
            stationForm['lngLat'] = this._latlng;
            mapComponent.searchs = mapComponent.indexSearchStation.nearest([this.feature.geometry.coordinates[0], this.feature.geometry.coordinates[1]], 10000, 10);
            //component.updateStationObject(stationForm, mapComponent, layer);
          });

        layer.on(
          'click',
          function (e) {
            mapComponent.disableContextMenu();
            mapComponent.clearSelectedObject()
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
              //component.commonParam.onChange(false);
              let data = {action: 'view', data: layer.feature.properties};
              //component.commonParam.showProperties('poolsview', data);
            }
          });
      });
    });
    setTimeout(function () {
      const lsStationMarker: NodeListOf<Element> = document.querySelectorAll('.poolClass')
      for (const s of lsStationMarker as any) {

      }
    }, 1000);
    // console.log(output);
  }

}
