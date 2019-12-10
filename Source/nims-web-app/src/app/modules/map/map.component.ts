import {Component, OnInit} from '@angular/core';
import {MapInfraCableService} from '@app/core/services/map/map-infraCable.service';
import {StationsComponent} from '@app/modules/map/stations/stations.component';
import {CablesComponent} from '@app/modules/map/cables/cables.component';
import {PillarsComponent} from '@app/modules/map/pillar/pillar.component';
import {CfgMapService} from '@app/core/services/map/map-cfg.service';
import {CommonParam, CommonParamObserver} from '@app/core/app-common-param';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {MapSidebar} from '@app/modules/map/map-sidebar';
import {MenuItem} from 'primeng/api';
import {Items} from '@app/modules/map/list-view-map/list-view-map.component';
import {ElasticSearch} from '@app/core/services/elastic-search/elastic-search.service';
import {TranslationService} from 'angular-l10n';
import {OdfService} from '@app/modules/odfs-mgmt/service/odf.service';
import 'rxjs/add/operator/toPromise';
import {VIEW_LAYER_CHECKBOX, MAP} from '@app/shared/services/constants';
import {createConsoleLogger} from '@angular-devkit/core/node';
import {PoolsLogic} from '@app/modules/map/pools/service/pools.logic';
import {MapInfraStationService} from '@app/core/services/map/map-infraStation.service';

declare let L;

@Component({
  selector: 'map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
  animations: [
    trigger('changeSide', [
      state('default', style({
        left: '100%'
      })),
      state('colspan', style({
        left: '50px'
      })),

      transition('default => colspan', [
        animate('0.5s')
      ]),
      transition('colspan => default', [
        animate('0.5s')
      ])
    ]),
    trigger('hiddenSide', [
      state('hidden', style({
        display: 'none'
      })),
      state('show', style({
        display: 'block'
      }))
    ]),

    trigger('toggle', [
      state('show', style({
        left: '1px',
      })),
      state('hide', style({
        left: '-350px',
      })),


      state('show-tool-bar', style({
        display: 'block',
      })),
      state('hide-tool-bar', style({
        display: 'none',
      })),

      transition('show => hide', [
        animate('0.5s')
      ]),
      transition('hide => show', [
        animate('0.5s')
      ]),

    ]),
  ]
})
export class MapComponent implements OnInit, CommonParamObserver {
  constructor(private infraCableService: MapInfraCableService
    , private infraStationService: MapInfraStationService
    , private stationComponent: StationsComponent
    , private cableComponent: CablesComponent
    , private pillarComponent: PillarsComponent
    , private poolComponent: PoolsLogic
    , private cfgMapService: CfgMapService
    , private commonParam: CommonParam
    , private elasticSearch: ElasticSearch, private translation: TranslationService
    , private odfService: OdfService) {
  }

  mapItem: string;
  mapItemConfig: MapSidebar = new MapSidebar();
  map;
  groupLayer;
  contextMenu;
  overLayers;
  addToMaped = {'Cables': false, 'Stations': false, 'Pools': false, 'Pillars': false};
  searchs;
  indexSearchCable;
  indexSearchStation;
  disableEvent = false;
  isShowBtnSave = false;
  currentlyEditingId;
  mapCfg: any[];
  private mapContextMenuItem: MenuItem[];
  itemAdd;
  itemSetting;
  // cable
  isChange = true;
  isHidden = true;
  isType: string;
  data: any;
  // topbar
  items: MenuItem[];
  setting: MenuItem[];
  resultData: Items [];

  // do khoang cach start
  _firstLatLng;
  _firstPoint;
  _secondLatLng;
  _secondPoint;
  _length = 0;
  _polyline = new L.LayerGroup();
  // do khoang cach end

  isShowHideSearchBar = false;
  isShowHideToolBar = false;
  isShowSearchGmapIcon = false;
  cable: any;
  makerStation;
  isShowContextMenu = false;
  // hien thi theo layer
  listViewLayer: any[] = [];
  selectedLayer: any[] = [];
  // LOAI SEARCH TRONG BAN DO
  searchType = '';


  layerSelected;

  ngOnInit() {
    this.listViewLayer = VIEW_LAYER_CHECKBOX;
    this.cable = {
      cableId: null,
      sourceId: null,
      source: null,
      destId: null,
      dest: null
    };
    this.items = [
      {
        label: 'Thêm mới nhà trạm', icon: 'assets/img/nims/tool_add.png', command: (event) => {
          document.body.style.cursor = 'url("../assets/img/icon/Station1.png"), default';
          document.getElementById('map').style.cursor = 'url("../assets/img/icon/Station1.png"), default';
          this.addItem('stations');
        }
      }
      ,
      {
        label: 'Thêm mới đoạn cáp', icon: 'assets/img/nims/tool_add.png', command: (event) => {
          document.body.style.cursor = 'pointer';
          document.getElementById('map').style.cursor = 'pointer';
          this.addItem('cableEdit');
        }
      },
      //tudn them tro chuot
      {
        label: 'Thêm mới cột', icon: 'assets/img/nims/tool_add.png', command: (event) => {
          document.body.style.cursor = 'pointer';
          document.getElementById('map').style.cursor = 'pointer';
          this.addItem('pillarEdit');
        }
      },
    ];

    this.setting = [
      {
        label: 'Đo khoảng cách', icon: 'assets/img/nims/tool_setting.png', command: (event) => {
          document.getElementById('map').style.cursor = 'pointer';
          this.addItem('distance');
        }
      },
      {
        label: 'Định vị', icon: 'assets/img/nims/tool_setting.png', command: (event) => {
          this.addItem('locate');
          this.commonParam.onChange(false);
          this.showProperties(this.itemAdd, null);
          console.log(this.map._lastCenter);
        }
      },
      {
        label: 'Cấu hình bản đồ', icon: 'assets/img/nims/tool_setting.png', command: (event) => {
          this.addItem('config');
          this.commonParam.onChange(false);
          this.showProperties(this.itemAdd, this.map._lastCenter);
        }
      },
      {
        label: 'Hiển thị theo layer', icon: 'assets/img/nims/tool_setting.png', command: (event) => {
          this.addItem('layer');
          this.commonParam.onChange(false);
          this.showProperties(this.itemAdd, this.selectedLayer);
        }
      },
      {
        label: 'Tìm kiếm đối tượng địa lý', icon: 'assets/img/nims/tool_setting.png', command: (event) => {
          this.addItem('search-address');
          this.commonParam.onChange(false);
          this.showProperties(this.itemAdd, null);
        }
      }
    ];
    this.mapContextMenuItem = [];
    this.initMap();
    this.getCfgMap();
    this.commonParam.attach(this);
  }

  hideContextMenu(isShow: boolean) {
    this.isShowContextMenu = isShow;
  }

  onChange(isShow: boolean) {
    this.isHidden = isShow;
  }

  handleClickCloseSide() {
    this.isHidden = true;
    this.mapItem = '';
    this.itemAdd = '';
    document.body.style.cursor = 'default';
    document.getElementById('map').style.cursor = 'default';
  }

  // Them moi tram/cot/be/cap/tuyen cap
  addItem(item: string) {
    this.itemAdd = item;
  }

  showProperties(type: string, data: any) {
    this.isType = type;
    this.data = data;
    this.data.component = this;
    if (this.isType === 'cableEdit') {
      this.mapItem = 'CablesManagementComponent';
    }
    if (this.isType === 'cablesview') {
      this.mapItem = 'CableDetailComponent';
    }
    if (this.isType === 'stationsEdit') {
      this.mapItem = 'StationSaveComponent';
    }
    if (this.isType === 'stationsview') {
      this.mapItem = 'StationMapDetailComponent';
    }
    //tudn them moi kieu cho cot
    if (this.isType === 'pillarEdit') {
      this.mapItem = 'PillarSaveComponent';
    }
    if (this.isType === 'pillarsview') {
      this.mapItem = 'PillarDetailComponent';
    }

    if (this.isType === 'locate') {
      this.mapItem = 'LocateComponent';
    }
    if (this.isType === 'config') {
      this.mapItem = 'ConfigMapComponent';
    }
    if (this.isType === 'odf') {
      switch (data.action) {
        case 'view':
          this.mapItem = 'ODFViewComponent';
          break;
        case 'add':
          this.mapItem = 'ODFSaveComponent';
          break;
        default:
          break;
      }
    }
    if (this.isType === 'layer') {
      this.mapItem = 'ViewLayerComponent';
    }
    if (this.isType === 'weldingOdf') {
      this.mapItem = 'WeldingOdfCreateComponent';
    }
    if (this.isType === 'search-address') {
      this.mapItem = 'SearchAddressComponent';
    }
    this.mapItemConfig.keyInputInjector = ['data'];
    this.mapItemConfig.valueInputInjector = [this.data];
  }


  focusItem(data: any) {
    switch (data.type) {
      case MAP.CABLES:
        const cableForm = {};
        cableForm['cableId'] = data.id;
        this.infraCableService.findByData(cableForm).subscribe(res => {
          let fea = res.cables[0];
          if (fea) {
            let lat = fea[0].coordinates[Math.ceil(fea[0].coordinates.length / 2)][1];
            let lng = fea[0].coordinates[Math.ceil(fea[0].coordinates.length / 2)][0];
            this.map.setView({lat, lng}, this.map.getZoom());
          }
        }, () => {
        }, () => {
        });

        setTimeout(() => {
          for (let layer of this.overLayers['Cables'].getLayers()) {
            if (layer.feature.properties.cable_id == data.id) {
              this.onHighlightObject(layer);
              break;
            }
          }
        }, 1000);

        break;
      case MAP.STATIONS:
        const stationForm = {};
        stationForm['stationId'] = data.id;
        this.infraStationService.findByData(stationForm).subscribe(res => {
          let fea = res.stations[0].features;
          if (fea) {
            debugger;
            let lat = fea[0].geometry.coordinates[1];
            let lng = fea[0].geometry.coordinates[0];
            this.map.setView({lat, lng}, this.map.getZoom());
          }
        }, () => {
        }, () => {
        });
        setTimeout(() => {
          for (let layer of this.overLayers['Stations'].getLayers()) {
            if (layer.feature.properties.station_id == data.id) {
              this.changeSizeIcon(layer);
              break;
            }
          }
        }, 1000);
        break;
    }
  }

  initMap() {

    const component = this;
    // Creating a Layer object
    const openStreetMap = new L.TileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png');
    const googleMap = new L.TileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png');
    const baseLayers = {
      'OpenStreetMap': openStreetMap // last one is the default
      , 'GoogleMap': googleMap
    };
    // Adding layer to the map
    // map.addLayer(openStreetMap);
    const cable = new L.geoJSON();
    const station = new L.geoJSON();
    const pool = new L.geoJSON();
    const pillar = new L.geoJSON();
    this.overLayers = {
      'Cables': cable,
      'Stations': station,
      'Pools': pool,
      'Pillars': pillar,
    };
    this.map = new L.map('map', {
      center: [21.0052455, 105.8017395],
      zoom: 30,
      editable: true,
      selectArea: true,
      contextmenu: true,
      zoomControl: false,
    });


    this.map.addLayer(openStreetMap);

    // Creating a map object
    this.map.addControl(new L.Control.Fullscreen({
      title: {
        'false': 'View Fullscreen',
        'true': 'Exit Fullscreen'
      },
      position: 'bottomright'
    }));

    const defaultOptions = {
      title: 'Data Layers',
      layers: [
        {
          group: 'Infra data',
          collapsed: true,
          layers: []
        }

      ]
    };
    const defaultBaseOptions = {
      title: 'Layers',
      layers: [
        {
          group: 'Base maps',
          collapsed: true,
          layers: [{layer: openStreetMap, name: 'OpenStreetMaps'}, {layer: googleMap, name: 'GoogleMap'}]
        }

      ]
    };
    L.control.zoom({
      position: 'bottomright'
    }).addTo(this.map);

    this.groupLayer = L.control.panelLayers(defaultBaseOptions.layers, defaultOptions.layers, {
      title: defaultOptions.title,
      position: 'topright',
      compact: true, // panel will take up only space needed instead of page height
      collapsibleGroups: true, // allows group to be collapsed by user
      collapsed: false // panel will collapsed on mouse off
    });
    // create layer data in groupLayer
    const layerNames = ['Stations', 'Cables', 'Pillars', 'Pools'];
    for (const index in layerNames) {
      try {
        const name = layerNames[index];
        const icon = this.getImageForLayer(name);
        const layer = new L.geoJSON();
        this.groupLayer.addOverlay({
          layer: layer,
          icon: icon,
          name: name
        }, name, 'Infra data');
      } catch (e) {
        // console.log(e);
      }
    }
    this.groupLayer.options.collapsibleGroups = false;
    this.groupLayer.addTo(this.map);
    this.groupLayer.on({
      'panel:selected': function (evt) {
        console.log(evt);
        component.map.addLayer(evt.layer);
        switch (evt.name) {
          case 'Stations':
            component.addToMaped.Stations = true;
            component.stationComponent.reloadDataStation(component.map.getBounds(), component.map.getZoom(), component);
            break;
          case 'Cables':
            component.addToMaped.Cables = true;
            component.cableComponent.reloadDataCable(component.map.getBounds(), component.map.getZoom(), component);
            break;
          case 'Pools':
            component.addToMaped.Pools = true;
            component.poolComponent.reloadDataPool(component.map.getBounds(), component.map.getZoom(), component);
            break;
          case 'Pillars':
            component.addToMaped.Pillars = true;
            component.pillarComponent.reloadDataPillar(component.map.getBounds(), component.map.getZoom(), component);
            break;
          default:
            break;
        }
      }
    });
    this.groupLayer.on({
      'panel:unselected': function (evt) {
        console.log(component.overLayers['Stations']);
        component.map.removeLayer(evt.layer);
        switch (evt.name) {
          case 'Stations':
            component.addToMaped.Stations = false;
            break;
          case 'Cables':
            component.addToMaped.Cables = false;
            break;
          case 'Pools':
            component.addToMaped.Pools = false;
            break;
          case 'Pillars':
            component.addToMaped.Pillars = false;
            break;
          default:
            break;
        }
      }
    });


    const selectedObject = {Stations: [], Cables: []};
    const listIndexContext = [];

    this.map.on('moveend', function (e) {
      console.log('reloadData START');
      const zoom = component.map.getZoom();
      component.isShowBtnSave = false;
      // setTimeout(()=>{
      //   if (!this.disableEvent) {
      if (zoom > 12) {
        component.stationComponent.reloadDataStation(component.map.getBounds(), component.map.getZoom(), component);
        component.cableComponent.reloadDataCable(component.map.getBounds(), component.map.getZoom(), component);
        component.poolComponent.reloadDataPool(component.map.getBounds(), component.map.getZoom(), component);
        component.pillarComponent.reloadDataPillar(component.map.getBounds(), component.map.getZoom(), component);
      } else {
        if (component.overLayers['Stations'] != undefined) {
          component.overLayers['Stations'].clearLayers();
        }
        if (component.overLayers['Cables'] != undefined) {
          component.overLayers['Cables'].clearLayers();
        }
        if (component.overLayers['Pools'] != undefined) {
          component.overLayers['Pools'].clearLayers();
        }
        if (component.overLayers['Pillars'] != undefined) {
          component.overLayers['Pillars'].clearLayers();
        }
      }
      // }
      // });
      console.log('reloadData END');
    });

    this.map.on({
      'click': function (e) {
        component.disableContextMenu();
        setTimeout(() => {
          if (!this.disableEvent) {
            component.clearSelectedObject();
          }
        });
        // // Them moi tram/cot/be/cap/tuyen cap
        if (component.itemAdd == MAP.STATIONS) {
          if (component.makerStation != undefined) {
            component.map.removeLayer(component.makerStation);
          }
          component.addStation(e.latlng);

          const item = {action: 'add', data: e.latlng};
          component.commonParam.onChange(false);
          component.showProperties('stationsEdit', item);
          this.mapItem = '';
        }
        //tudn them mot cot khi an vao ban do
        if (component.itemAdd == 'pillar') {
          if (component.makerStation != undefined) {
            component.map.removeLayer(component.makerStation);
          }
          component.addStation(e.latlng);

          const item = {action: 'add', data: e.latlng};
          component.commonParam.onChange(false);
          component.showProperties('pillarEdit', item);
          this.mapItem = '';
        }

        // do khoang cach start
        if (component.itemAdd == 'distance') {
          if (component._firstLatLng == null) {
            component._firstLatLng = e.latlng;
            component._firstPoint = e.layerPoint;
            L.popup({
              autoClose: true
            }).setLatLng(component._firstLatLng)
              .setContent('Khoảng cách của đường đi là 0 m')
              .openOn(component.map);
            // L.marker(component._firstLatLng).addTo(component.map);
          } else {
            component._secondLatLng = e.latlng;
            component._secondPoint = e.layerPoint;
            // L.marker(component._secondLatLng).addTo(component.map);
          }

          if (component._firstLatLng && component._secondLatLng) {
            component.refreshDistanceAndLength();
            component._firstLatLng = component._secondLatLng;
            component._firstPoint = component._secondPoint;
          }
        }
      }
    });

    this.map.on({
      'contextmenu': function (e) {
        component.disableContextMenu();
      }
    });


    this.map.on('zoomend', function (e) {
      if (component._firstLatLng && component._secondLatLng) {
        component.refreshDistanceAndLength();
      }
    });

    this.map.on('keyup', function (e) {
      if (e.originalEvent.key === 'Escape') {
        if (component.itemAdd == 'distance') {
          component.itemAdd = '';
          component._firstLatLng = null;
          component._secondLatLng = null;
          document.getElementById('map').style.cursor = 'default';
          component._polyline.eachLayer(
            function (layer) {
              component.map.removeLayer(layer);
            });
        }
      }
    });


  }

  refreshDistanceAndLength() {
    this._length = L.GeometryUtil.length([this._firstPoint, this._secondPoint]) + this._length;
    // draw the line between points
    const layer = L.polyline([this._firstLatLng, this._secondLatLng], {
      color: 'red'
    }).addTo(this.map);
    // .bindPopup('Khoảng cách của đường đi là ' + this._length + 'm').openPopup();
    this._polyline.addLayer(layer);
    const popup = L.popup({
      autoClose: false
    }).setLatLng(this._secondLatLng)
      .setContent('Khoảng cách của đường đi là ' + this._length + 'm')
      .openOn(this.map);
  }

// do khoang cach end

  getCfgMap() {
    const component = this;
    this.mapCfg = [];
    this.cfgMapService.getAllOwner().subscribe(res => {
      if (res) {
        res.forEach(function (entry) {
          component.mapCfg.push(entry);
        });
      }
    });
  }

  reloadData() {
    console.log('reloadData START');
    const zoom = this.map.getZoom();
    if (zoom > 12) {
      if (this.addToMaped.Stations) {
        this.stationComponent.reloadDataStation(this.map.getBounds(), this.map.getZoom(), this);
      }
      if (this.addToMaped.Cables) {
        this.cableComponent.reloadDataCable(this.map.getBounds(), this.map.getZoom(), this);
      }
      if (this.addToMaped.Pillars) {
        this.pillarComponent.reloadDataPillar(this.map.getBounds(), this.map.getZoom(), this);
      }
    } else if (zoom <= 12) {
      // clear layers
      if (this.overLayers['Stations'] != undefined) {
        this.overLayers['Stations'].clearLayers();
      }
      if (this.overLayers['Cables'] != undefined) {
        this.overLayers['Cables'].clearLayers();
      }
      if (this.overLayers['Pillars'] != undefined) {
        this.overLayers['Pillars'].clearLayers();
      }
    }
    console.log('reloadData END');
  }


  getImageForLayer(type) {
    if (type == 'Stations') {
      // return '<img src="icon/black/png/16/home.png"></img>';
      return '<img src="assets/img/icon/station.png"></img>';
    } else if (type == 'Cables') {
      return '<img src="assets/img/icon/station.png"></img>';
    } else if (type == 'Pillars') {
      return '<img src="assets/img/icon/station.png"></img>';
    } else {
      return '<img src="assets/img/icon/station.png"></img>';
    }
  }

  // action to station object START


  clearSelectedObject() {
    const component = this;
    component.map.eachLayer(function (layer) {
      if (layer.feature) {
        switch (layer.feature.properties.type) {
          case MAP.STATIONS:
            layer.setIcon(component.getStationOptions(layer.feature, component));
            break;
          case MAP.CABLES:
            layer.setStyle({
              color: component.cableComponent.getColorForCable(layer.feature.properties.status),
              weight: 3,
            });
            break;
          case MAP.POOL:
            layer.setIcon(component.getPoolOptions(layer.feature, component));
            break;
          case MAP.PILLAR:
            layer.setIcon(component.getPillarOptions(layer.feature, component));
            break;
          default:
            break;
        }
      }
    });
  }

  changeSizeIcon(layer) {
    const icon = layer.options.icon;
    icon.options.iconSize = [30, 30];
    layer.setIcon(icon);
  }

  createMenuActionForLayer(type, layer, _evt) {
    const component = this;
    component.mapContextMenuItem = [];
    component.contextMenu = [];
    switch (type) {
      case MAP.STATIONS: {
        component.mapContextMenuItem.push({
          label: this.translation.translate('map.label.station.move'),
          command: (event) => {
            setTimeout(() => component.disableContextMenu());
            component.enableEditObject(type, layer);
          }
          // index: 1,
          // callback: component.enableEditObject(type, layer)
        });
        component.mapContextMenuItem.push({
          label: this.translation.translate('map.label.update.station.info'),
          command: (event) => {
            setTimeout(() => component.disableContextMenu());
            component.updateObject(type, layer, component);
          }
          // index: 2,
          // callback: component.updateObject(type, '', component)
        });
        const lsODF = component.getODFByStationCode(layer, _evt);
        component.mapContextMenuItem.push({
          label: this.translation.translate('map.label.station.view.info.odf'),
          items: [] = lsODF,
          // index: 3,
          // callback: component.getODFByStationCode(layer, _evt),
        });

        component.mapContextMenuItem.push({
          label: this.translation.translate('map.label.station.add.odf'),
          // index: 4,
          // callback: component.updateObject(type, '', component)
          command: (event) => {
            setTimeout(() => component.disableContextMenu());
            const item = {action: 'add', data: layer.feature.properties};
            component.commonParam.showProperties('odf', item);
            component.commonParam.onChange(false);
          }
        });

        // component.mapContextMenuItem.push({
        //   label: this.translation.translate('map.label.station.fix.odf'),
        //   // index: 5,
        //   // callback: component.updateObject(type, '', component)
        //   command: (event) => {
        //     let item = {action: 'add', data: layer.feature.properties};
        //     component.commonParam.showProperties('welding_odf', item);
        //     component.commonParam.onChange(false);
        //   }
        // });

        component.mapContextMenuItem.push({
          label: this.translation.translate('map.label.station.contect.loop.odf'),
          // index: 5,
          // callback: component.updateObject(type, '', component)
          command: (event) => {
            setTimeout(() => component.disableContextMenu());
            const item = {action: 'add', data: layer.feature.properties};
            component.commonParam.showProperties('weldingOdf', item);
            component.commonParam.onChange(false);
          }
        });

        component.mapContextMenuItem.push({

          label: this.translation.translate('map.label.station.delete'),
          // index: 7,
          // callback: component.deleteObject(type)
          command: (event) => {
            setTimeout(() => component.disableContextMenu());
            component.deleteObject(type);
          }
        });
        component.mapContextMenuItem.push({
          label: this.translation.translate('map.label.station.copy'),
          // index: 7,
          // callback: component.deleteObject(type)
        });
        component.isShowContextMenu = true;
        break;
      }
      case MAP.CABLES: {
        component.contextMenu.push({
          text: 'Tinh chỉnh cáp',
          index: 2,
          callback: component.enableEditObject(type, layer)
        });
        component.contextMenu.push({
          text: 'Cập nhật đoạn cáp',
          index: 3,
          callback: component.updateObject(type, layer, component)
        });
        component.contextMenu.push({
          separator: '-',
          index: 4
        });
        component.contextMenu.push({
          text: 'Delete',
          index: 5,
          callback: component.deleteObject(type)
        });
        break;
      }
      default: {
        break;
      }
    }
    return component.contextMenu;
  }

  // Cap nhat tram/cot/be/cap
  updateObject(type, layer, component) {
    if (type == MAP.STATIONS) {
      const item = {action: 'edit', data: layer.feature.properties};
      component.commonParam.showProperties('stationsEdit', item);
      component.commonParam.onChange(false);
    } else if (type == MAP.CABLES) {
      return function (e) {
        component.cable.cableId = layer.feature.properties.cable_id;
        const item = {action: 'edit', data: layer.feature.properties};
        component.commonParam.showProperties('cableEdit', item);
        component.commonParam.onChange(false);

      };

      // component.cable.cableId = null;
    }
  }

  onHighlightObject(layer) {
    if (layer instanceof L.Marker) {
      // layer.setStyle({
      //   fillColor: '#0f0'
      // });
    } else if (layer instanceof L.Polyline) {
      layer.setStyle({
        color: '#0f0',
        zIndex: 99999,
        weight: 5,
      });
    }
  }

  getStationOptions(feature, mapComponent) {
    const component = this;
    const myIcon = L.icon({
      iconUrl: '',
      iconSize: [15, 15],
    });
    if (feature != null && feature != undefined) {
      for (const item of mapComponent.mapCfg) {
        if (feature.properties.owner_id == item.ownerId) {
          myIcon.options.iconUrl = item.stationIcon;
          break;
        }
      }
      myIcon.options.iconUrl = myIcon.options.iconUrl == '' ? 'assets/img/icon/Station1.png' : myIcon.options.iconUrl;
      return myIcon;
    }


    // return component.getColorForStation(feature.properties.owner_id, mapComponent);
    // return {
    //   radius: 10,
    //   fillColor: component.getColorForStation(feature.properties.owner_id, mapComponent),
    //   color: '#000',
    //   weight: 1,
    //   opacity: 1,
    //   fillOpacity: 0.8,
    //   icon: component.greenIcon,
    //   draggable: false
    // };
  }

  getPoolOptions(feature, mapComponent) {
    const component = this;
    const myIcon = L.icon({
      iconUrl: '',
      iconSize: [15, 15],
    });
    if (feature != null && feature != undefined) {
      for (const item of mapComponent.mapCfg) {
        if (feature.properties.owner_id == item.ownerId) {
          myIcon.options.iconUrl = item.poolIcon;
          break;
        }
      }
      myIcon.options.iconUrl = myIcon.options.iconUrl == '' ? 'assets/img/icon/Tank1.png' : myIcon.options.iconUrl;
      return myIcon;
    }

    return myIcon;
  }

  getPillarOptions(feature, mapComponent) {
    const component = this;
    const myIcon = L.icon({
      iconUrl: '',
      iconSize: [15, 15],
    });
    if (feature != null && feature != undefined) {
      for (const item of mapComponent.mapCfg) {
        if (feature.properties.owner_id == item.ownerId) {
          myIcon.options.iconUrl = item.pillarIcon;
          break;
        }
      }
      myIcon.options.iconUrl = myIcon.options.iconUrl == '' ? 'assets/img/icon/Pole1.png' : myIcon.options.iconUrl;
      return myIcon;
    }
  }

  addLayerToPanelLayer(type) {
    let added = false;
    for (let index = 0; index < this.groupLayer._layers.length; index++) {
      if (this.groupLayer._layers[index].name == type) {
        this.groupLayer._layers[index].layer.clearLayers;
        this.groupLayer._layers[index].layer = this.overLayers[type];
        added = true;
        break;
      }
    }
    if (!added) {
      this.groupLayer.addOverlay({
        layer: this.overLayers[type],
        icon: this.getImageForLayer(type)
      }, type, 'Infra data');
    }
  }


  enableEditObject(type, _layer) {
    const component = this;

    let layer;
    if (type === 'stations') {
      layer = _layer;
      layer.options.editing || (layer.options.editing = {});
      // layer.editing.enable();
      // layer.dragging.enable();
      layer.enableEdit();
      // layer.setZIndex(999);
    } else if (type === 'cables') {
      return function () {
        this.disableEvent = true;
        layer = _layer;
        layer.options.editing || (layer.options.editing = {});
        // layer.editing.enable();
        layer.enableEdit();
        component.isShowBtnSave = true;
      };
      // let toolbar = new L.EditToolbar({
      //   featureGroup: featureGroup
      // });

      // let editHandler = toolbar.getModeHandlers()[0].handler;
      // editHandler._enableLayerEdit(layer);
    }

  }

  stopEditing(evt) {
    this.disableEvent = false;
    const cable = this.overLayers['Cables'].getLayer(this.currentlyEditingId);
    // cable.editing.disable();
    const cableForm = {};
    cableForm['cableId'] = cable.feature.properties.cable_id;
    cableForm['cableCode'] = cable.feature.properties.cable_code;
    const arrays1 = [];
    let lc;
    for (const k in cable._latlngs) {
      lc = {};
      lc.lng = cable._latlngs[k].lng;
      lc.lat = cable._latlngs[k].lat;
      arrays1.push(lc);
    }
    cableForm['lngLats'] = arrays1;
    this.cableComponent.onUpdateCable(cableForm, this);
  }


  checkPolylineInBox(bbox, latlngs) {
    let firstPoint = null;
    let secondPoint = null;
    // var line;
    let k = 0;
    for (; latlngs[k];) {
      const latlng = latlngs[k];
      k++;
      // latlngs.forEach(function (latlng) {
      if (firstPoint == null) {
        firstPoint = latlng;
      } else {
        secondPoint = latlng;
        // line = L.Polyline([[firstPoint.lat, firstPoint.lng], [secondPoint.lat, secondPoint.lng]], {color: '#3333', opacity: 1});
        // if (lineRect(firstPoint.lat, firstPoint.lng, secondPoint.lat, secondPoint.lng, bbox._southWest.lat, bbox._southWest.lng, bbox._northEast.lat, bbox._northEast.lng)) {
        //     return true;
        // }
        if (lineIntersectsRect(firstPoint, secondPoint, bbox)) {
          return true;
        }
        firstPoint = secondPoint;
      }
      // });
    }
    return false;
  }

  lineIntersectsRect(firstPoint1, secondPoint, bound) {
    if (bound.contains(firstPoint1)) {
      return true;
    }
    if (bound.contains(secondPoint)) {
      return true;
    }
    if (lineIntersectsLine(firstPoint1, secondPoint, {
      lat: bound._southWest.lat,
      lng: bound._southWest.lng
    }, {lat: bound._southWest.lat, lng: bound._northEast.lng})) {
      return true;
    }
    if (lineIntersectsLine(firstPoint1, secondPoint, {
      lat: bound._southWest.lat,
      lng: bound._northEast.lng
    }, {lat: bound._northEast.lat, lng: bound._northEast.lng})) {
      return true;
    }
    if (lineIntersectsLine(firstPoint1, secondPoint, {
      lat: bound._northEast.lat,
      lng: bound._northEast.lng
    }, {lat: bound._northEast.lat, lng: bound._southWest.lng})) {
      return true;
    }
    if (lineIntersectsLine(firstPoint1, secondPoint, {
      lat: bound._northEast.lat,
      lng: bound._southWest.lng
    }, {lat: bound._southWest.lat, lng: bound._southWest.lng})) {
      return true;
    }
    return false;
  }

  lineIntersectsLine(l1p1, l1p2, l2p1, l2p2) {
    let q = (l1p1.lng - l2p1.lng) * (l2p2.lat - l2p1.lat) - (l1p1.lat - l2p1.lat) * (l2p2.lng - l2p1.lng);
    const d = (l1p2.lat - l1p1.lat) * (l2p2.lng - l2p1.lng) - (l1p2.lng - l1p1.lng) * (l2p2.lat - l2p1.lat);
    if (d == 0) {
      return false;
    }
    const r = q / d;
    q = (l1p1.lng - l2p1.lng) * (l1p2.lat - l1p1.lat) - (l1p1.lat - l2p1.lat) * (l1p2.lng - l1p1.lng);
    const s = q / d;
    if (0 > r) {
      return false;
    }
    if (r > 1) {
      return false;
    }
    if (0 > s) {
      return false;
    }
    if (s > 1) {
      return false;
    }
    return true;
  }

  deleteObject(layer) {
    return function (e) {
      const form = {};
      if (layer instanceof L.Marker) {
        form['type'] = 'station';
        form['id'] = e.relatedTarget.feature.properties.station_id;
      } else if (layer instanceof L.Polyline) {
        form['type'] = 'cable';
        form['id'] = e.relatedTarget.feature.properties.cable_id;
      }
      // this.http.ajax({
      //   type: "POST",
      //   contentType: "application/json",
      //   url: "http://localhost:8061/nims/infraStations/delete",
      //   data: JSON.stringify(form),
      //   dataType: 'json',
      //   cache: false,
      //   timeout: 600000,
      //   success: function () {
      //     if (layer instanceof L.CircleMarker) {
      //       reloadDataStation(map.getBounds(), map.getZoom());
      //     } else if (layer instanceof L.Polyline) {
      //       reloadDataCable()
      //     }
      //   }
      // });
    };
  }

  getODFByStationCode(layer, _evt, callback?: Function) {
    const component = this;
    const item = {};
    item['stationCode'] = layer.feature.properties.station_code;
    item['rows'] = 10;
    item['first'] = 0;
    const rs = [];
    rs.push({label: this.translation.translate('map.label.station.list.odf.code')}, {separator: true});
    component.odfService.findAdvanceOdf(item).subscribe(res => {
      if (res.content != null && res.content.totalRecords !== 0) {
        if (res.content.listData) {
          for (const it of res.content.listData) {
            const val = {
              label: it.odfCode, command: (event) => {
                setTimeout(() => component.disableContextMenu());
                this.viewODF(it.odfId);
              }
            };
            rs.push(val);
          }
        }
      }
    });
    return rs;
  }

  viewODF(odfId) {
    this.addItem('odf');
    const item = {action: 'view', data: odfId};
    this.commonParam.onChange(false);
    this.showProperties(this.itemAdd, item);
  }


// search -start
  valueChange(evt) {
    this.resultData = [];
    if (evt.target.value != undefined && evt.target.value.trim() != '') {
      const item = {};
      item['query'] = evt.target.value == undefined ? '' : evt.target.value;
      item['type'] = this.searchType;
      this.search(item);
    }
  }

  onFocusInputSearch(event) {
    if (event) {
      this.isShowSearchGmapIcon = true;
    }
  }

  onBlurInputSearch(event) {
    if (event) {
      this.isShowSearchGmapIcon = event.currentTarget.value !== '';
    }
  }

// search -start
  searchStation() {
    this.searchType = 'stations';
    this.resultData = [];
    const item = {};
    item['query'] = '';
    item['type'] = this.searchType;
    this.search(item);
  }

// search -start
  searchCables() {
    this.searchType = 'cables';
    this.resultData = [];
    const item = {};
    item['query'] = '';
    item['type'] = this.searchType;
    this.search(item);
  }

  addCable(lst) {
    const layer = L.polyline(lst).addTo(this.map);
    this.cable.layer = layer;
    let item = {action: 'add', data: this.cable};
    this.commonParam.onChange(false);
    this.commonParam.showProperties(this.itemAdd, item);
    this.itemAdd = '';
  }

  clearCable(layerCable: any) {
    this.map.removeLayer(layerCable);
  }

  addStation(latlng) {
    const myIcon = L.icon({
      iconUrl: 'assets/img/icon/Station1.png',
      iconSize: [25, 25],
    });
    this.makerStation = L.marker(latlng, {icon: myIcon}).addTo(this.map);
  }

  // hien thi theo layer
  viewLayer(listObject: any[]) {
    const listObj = [];
    for (let i = 0; i < this.listViewLayer.length; i++) {
      listObj.push(this.listViewLayer[i].name);
    }
    this.selectedLayer = listObject;
    if (listObject.length > 0) {
      for (let i = 0; i < listObject.length; i++) {
        switch (listObject[i]) {
          case 'station': {
            listObj.splice(listObj.indexOf(listObject[i]), 1);
            this.addToMaped.Stations = true;
            this.stationComponent.reloadDataStation(this.map.getBounds(), this.map.getZoom(), this);
            break;
          }
          case 'cable': {
            listObj.splice(listObj.indexOf(listObject[i]), 1);
            this.addToMaped.Cables = true;
            this.cableComponent.reloadDataCable(this.map.getBounds(), this.map.getZoom(), this);
            break;
          }
        }
      }
    }

    if (listObj.length > 0) {
      for (let i = 0; i < listObj.length; i++) {
        switch (listObj[i]) {
          case 'station': {
            this.addToMaped.Stations = false;
            this.map.removeLayer(this.overLayers['Stations']);
            break;
          }
          case 'cable': {
            this.addToMaped.Cables = false;
            this.map.removeLayer(this.overLayers['Cables']);
            break;
          }
        }
      }
    }
    this.handleClickCloseSide();
  }

  // Dinh vi
  locateFunction(longitude, latitude) {
    const myIcon = L.icon({
      iconUrl: 'assets/img/nims/star-2.png',
      iconSize: [20, 20],
    });
    L.marker([latitude, longitude], {icon: myIcon}).addTo(this.map);
    this.map.panTo(new L.LatLng(latitude, longitude));
  }

  // Dong popup left
  closePopupLeft() {
    this.handleClickCloseSide();
  }


  search(item: any) {
    const component = this;
    const items = new SearchOjects(item.query, item.type);

    this.elasticSearch.searchObject((item.type == null || item.type == '') ? items.searchAll : items.searchObject).subscribe(res => {
      component.resultData = [];
      res.hits.hits.forEach(function (items) {
        const i = {
          id: items._source._object_id
          , text: items._source._code + ' - ' + items._source.type
          , data: {geometry: '', properties: ''}
          , type: items._source.type
          , action: 'view'
        };
        component.resultData.push(i);
      });
    });
  }

  disableContextMenu() {
    this.isShowContextMenu = false;
  }

  modifyContexMenuMap(e) {
    setTimeout(() => {
      if (document.getElementsByClassName('map-contextmenu')[0] != undefined) {
        let contextMenu = (<HTMLElement>document.getElementsByClassName('map-contextmenu')[0]);
        contextMenu.style.left = (contextMenu.offsetLeft - document.getElementById('nav-container').offsetWidth) + 'px';
        contextMenu.style.top = (contextMenu.offsetTop - ((<HTMLElement>document.getElementsByClassName('layout-topbar')[0]).offsetHeight + (<HTMLElement>document.getElementsByClassName('list_tab')[0]).offsetHeight)) + 'px';
      }
    });
  }
}

export class SearchOjects {
  searchAll: any = {};
  searchObject: any = {};

  constructor(query: string, type: string) {

    this.searchAll = {
      query: {
        bool: {
          should: [
            {
              query_string: {
                query: '*' + query + '*',
                fields: [
                  '_code',
                  '_src',
                  '_dest',
                  '_lane_code',
                  '_holder'
                ]
              }
            }, {
              multi_match: {
                query: query,
                fields: [
                  '_address',
                  '_department',
                  '_location'
                ],
                'analyzer': 'my_analyzer'
              }
            }
          ]
        }
      }
    };
    this.searchObject = {
      query: {
        bool: {
          must: [
            {
              match: {
                _object_type: type
              }
            },
            {
              bool: {
                should: [
                  {
                    query_string: {
                      query: '*' + query + '*',
                      fields: [
                        '_code',
                        '_src',
                        '_dest',
                        '_lane_code',
                        '_holder'
                      ]
                    }
                  },
                  {
                    multi_match: {
                      query: query,
                      fields: [
                        '_address',
                        '_department',
                        '_location'
                      ],
                      'analyzer': 'my_analyzer'
                    }
                  }
                ]
              }
            }
          ]
        }
      }
    };

  }

}
