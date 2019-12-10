import {Injectable} from '@angular/core';
import {BasicService} from "@app/core/services/basic.service";
import leafletKnn from '../../../../../../node_modules/leaflet-knn/leaflet-knn.js';
import {PoolsService} from "@app/modules/map/pools/pools.service";
declare let L;

@Injectable({
  providedIn: 'root'
})
export class PoolsLogic {

  constructor(private poolsService: PoolsService) {
  }


  reloadDataPool(bounds, zoom, mapComponent) {
    const data = {};
    data['maxX'] = bounds.getNorthWest().lat;
    data['maxY'] = bounds.getNorthWest().lng;
    data['minX'] = bounds.getSouthEast().lat;
    data['minY'] = bounds.getSouthEast().lng;
    data['zoom'] = zoom;
    const component = this;
    const output = this.poolsService.findByBbox(data).subscribe(res => {
      if (mapComponent.overLayers['Pools'] != undefined) {
        mapComponent.overLayers['Pools'].clearLayers();
      }
      mapComponent.overLayers['Pools'] = L.geoJSON(res.pools[0].features, {
        pointToLayer: function (feature, latlng) {
          const type = 'pool';
          let marker =
            L.marker(latlng, {
              icon: mapComponent.getPoolOptions(feature, mapComponent)
            });
          return marker;
        },
        onEachFeature: function (feature, layer) {
          layer.bindTooltip(feature.properties.pool_code);
        }
      });
      if (mapComponent.addToMaped.Pools) {
        mapComponent.overLayers['Pools'].addTo(mapComponent.map);
        mapComponent.map.addLayer(mapComponent.overLayers['Pools']);
      } else {
        mapComponent.map.removeLayer(mapComponent.overLayers['Pools']);
      }
      mapComponent.addLayerToPanelLayer('Pools');

    });
    setTimeout(function () {
      const lsStationMarker: NodeListOf<Element> = document.querySelectorAll('.poolClass')
      for (const s of lsStationMarker as any) {

      }
    }, 1000);
  }
}
