import {Injectable, OnInit} from "@angular/core";
import leafletKnn from "../../../../../node_modules/leaflet-knn/leaflet-knn.js";
import {MapInfraCableService} from "@app/core/services/map/map-infraCable.service";
import {CommonParam} from "@app/core/app-common-param";
import {Router} from "@angular/router";
declare let L;

@Injectable({
  providedIn: 'root'
})
export class CablesComponent implements OnInit {

  constructor(private infraCableService: MapInfraCableService,
              private commonParam: CommonParam,
              private router: Router) {
  }

  ngOnInit() {

  }

  reloadDataCable(bounds, zoom, mapComponent) {
    const component = this;
    console.log('reloadDataCables', bounds);
    const data = {};
    data['maxX'] = bounds.getNorthWest().lat;
    data['maxY'] = bounds.getNorthWest().lng;
    data['minX'] = bounds.getSouthEast().lat;
    data['minY'] = bounds.getSouthEast().lng;
    data['zoom'] = zoom;
    const output = this.infraCableService.findByBbox(data).subscribe(res => {
      if (mapComponent.overLayers['Cables'] != undefined) {
        try {
          mapComponent.overLayers['Cables'].clearLayers();
        } catch (e) {
          console.log("remove layer");
        }
      }
      mapComponent.overLayers['Cables'] = L.geoJSON(res.cables[0].features, {
        style: function (feature) {
          return component.getCableOptions(feature);
        },
        onEachFeature: function (feature, layer) {
          try {
            layer.bindTooltip(feature.properties.cable_code);
            layer.bindContextMenu({
              contextmenu: true,
              contextmenuItems: mapComponent.createMenuActionForLayer('cables', layer)
            });
          } catch (e) {
            console.log(e);
          }
        }
      }).addTo(mapComponent.map);
      if (mapComponent.addToMaped.Cables) {
        mapComponent.overLayers['Cables'].addTo(mapComponent.map);
        mapComponent.map.addLayer(mapComponent.overLayers['Cables']);
      } else {
        mapComponent.map.removeLayer(mapComponent.overLayers['Cables']);
      }
      mapComponent.addLayerToPanelLayer('Cables');
      mapComponent.indexSearchCable = leafletKnn(mapComponent.overLayers['Cables']);
      mapComponent.overLayers['Cables'].getLayers().forEach(function (layer) {
        layer.on(
          'click',
          function (e) {
            mapComponent.disableContextMenu();
            try {
              mapComponent.clearSelectedObject();
              setTimeout(() => {
                mapComponent.onHighlightObject(layer)
              }, 100);
              component.commonParam.onChange(false);
              let data = {action: 'view', data: layer.feature};
              component.commonParam.showProperties('cablesview', data);
              // component.router.navigate(['/map',{outlets: {sidebar: ['cables'] } }]);
            } catch (Ex) {
              console.log(Ex);
            }
          });
        layer.on(
          'contextmenu',
          function (e) {
            mapComponent.isShowContextMenu = false;
            mapComponent.currentlyEditingId = layer._leaflet_id;
            mapComponent.clearSelectedObject();
            mapComponent.onHighlightObject(layer);
            mapComponent.createMenuActionForLayer('cables',layer, e);
            // mapComponent.createMenuActionForLayer(layer, mapComponent.overLayers['Cables']);
          });
        layer.on(
          'editable:vertex:dragend',
          function (e) {
            const cableForm = {};
            cableForm['cableId'] = this.feature.properties.cable_id;
            cableForm['cableCode'] = this.feature.properties.cable_code;
            // var k = 0;
            const arrays1 = [];
            let lc;
            for (const k in this._latlngs) {
              lc = {};
              lc.lng = this._latlngs[k].lng;
              lc.lat = this._latlngs[k].lat;
              arrays1.push(lc);
              // k++;
            }
            console.log("drag event : " + cableForm);
            cableForm['lngLats'] = arrays1;
            // component.onUpdateCable(cableForm);
          });
        layer.on(
          "editable:vertex:click", function (e) {
          }
        );
      });
    });
  }

  onUpdateCable(cableForm, mapComponent) {
    this.infraCableService.update(cableForm).subscribe(res => {
      if ("OK" == res.result) {
        // mapComponent.currentlyEditing.remove();
        // mapComponent.map.removeLayer(mapComponent.currentlyEditing);

        let myNode = document.getElementsByClassName('leaflet-marker-pane')[0];
        if (myNode != undefined) {
          myNode.innerHTML = '';
        }

        mapComponent.isShowBtnSave = false;
        // this.reloadDataCable(mapComponent.map.getBounds(), mapComponent.map.getZoom(), mapComponent);
        mapComponent.reloadData();
      }
    });

  }


  getCableOptions(feature) {
    return {
      color: this.getColorForCable(feature.properties.status),
      weight: 3,
      opacity: 1
    };
  }

  getColorForCable(status) {
    switch (status) {
      case 0:
        return '#ff6802';
      case 1:
        return '#4131ff';
      case 2:
        return '#ccff47';
      default:
        return '#ff1822';
    }
  }
}
