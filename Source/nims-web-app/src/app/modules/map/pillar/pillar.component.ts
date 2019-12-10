import {Component, Injectable, OnInit} from '@angular/core';
import leafletKnn from '../../../../../node_modules/leaflet-knn/leaflet-knn.js';
import {MapInfraPillarService} from "@app/core/services/map/map-infraPillar.service";
import {CommonParam} from "@app/core/app-common-param";
declare let L;
@Injectable({
  providedIn: 'root'
})
export class PillarsComponent implements OnInit {

  constructor(private infraPillarService: MapInfraPillarService, private commonParam: CommonParam) {
  }

  ngOnInit() {
  }

  reloadDataPillar(bounds, zoom, mapComponent) {
    console.log('reloadDataPillar');
    const data = {};
    data['maxX'] = bounds.getNorthWest().lat;
    data['maxY'] = bounds.getNorthWest().lng;
    data['minX'] = bounds.getSouthEast().lat;
    data['minY'] = bounds.getSouthEast().lng;
    data['zoom'] = zoom;
    const component = this;
    const output = this.infraPillarService.findByBbox(data).subscribe(res => {
      if (mapComponent.overLayers['Pillars'] != undefined) {
        // groupLayer.removeLayer(overLayers['Pillars']);
        mapComponent.overLayers['Pillars'].clearLayers();
      }
      mapComponent.overLayers['Pillars'] = L.geoJSON(res.pillars[0].features, {
        pointToLayer: function (feature, latlng) {
          const type = 'pillar';
          let marker =
            L.marker(latlng, {
              icon: mapComponent.getPillarOptions(feature, mapComponent)
              // ,
              // contextmenu: true,
              // contextmenuItems: mapComponent.createMenuActionForLayer(type)
            });
          return marker;
        },
        onEachFeature: function (feature, layer) {
          layer.bindTooltip(feature.properties.pillar_code);
        }
      });
      if (mapComponent.addToMaped.Pillars) {
        mapComponent.overLayers['Pillars'].addTo(mapComponent.map);
        mapComponent.map.addLayer(mapComponent.overLayers['Pillars']);
      } else {
        mapComponent.map.removeLayer(mapComponent.overLayers['Pillars']);
      }
      mapComponent.addLayerToPanelLayer('Pillars');
      // mapComponent.indexSearchPillar = leafletKnn(mapComponent.overLayers['Pillars']);

    });
  }
}
