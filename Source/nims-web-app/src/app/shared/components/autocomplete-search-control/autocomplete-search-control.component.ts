import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges, Type } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/api';
import { AppComponent } from '@app/app.component';
import { PermissionService } from '@app/shared/services/permission.service';
import { PillarService } from '@app/modules/pillars-mgmt/service/pillar.service';
import { PoolService } from '@app/modules/pools-management/service/pool.service';
import { EventBusService } from '@app/shared/services/event-bus.service';
import { StationService } from '@app/modules/stations-management/service/station.service';

@Component({
  selector: 'autocomplete-search-control',
  templateUrl: './autocomplete-search-control.component.html',
  styleUrls: ['./autocomplete-search-control.component.css']
})
export class AutocompleteSearchControlComponent implements OnInit, OnChanges, OnDestroy {
  @Input() contentType: Type<any>;
  @Input() header;
  @Input() placeholder = 'Choose item';
  @Input() width = '70%';
  @Input() control: FormControl;
  @Input() disabled = false;
  @Input() displayFieldType: string;
  @Input() type: string;
  @Input() dataType: string;
  @Input() selectedItemsLabel = 'label.mass.default.selectedItem';
  @Input() styleStation: string;
  @Input() styleDept: string;
  @Input() inputStyleStation: string;
  @Input() inputStyleDept: string;
  @Input() showButton = true;
  @Output() onChange = new EventEmitter();
  @Output() onClean = new EventEmitter();
  @Output() onBlurx = new EventEmitter();

  selectedStationData: any;
  selectedDeptData: any;
  selectedLocationData: any;
  selectedPillarData: any;
  selectedLaneData: any;
  selectedLaneSleeveData: any;
  selectedPoolData: any;
  displayField: any;
  displayFieldTooltip: string;
  // displayFieldOptions: any;
  isOpen = false;
  isDelete = false;
  filteredStation: any[];
  filteredDept: any[];
  filteredLocation: any[];
  filteredPillar: any[];
  filteredLaneCode: any[];
  filteredLaneSleeve: any[];
  filteredPool: any[];
  private deptDataObjSub: Subscription;
  deptDataObj;
  subscription;
  styleB;
  isStationSelect = false;
  isStation = false;

  constructor(
    public dialogService: DialogService,
    private app: AppComponent,
    private permissionService: PermissionService,
    private pillarService: PillarService,
    private poolService: PoolService,
    private eventBusService: EventBusService,
    private stationService: StationService
  ) {
    if (!this.control) {
      this.control = new FormControl();
    }
  }

  ngOnInit() {
    this.subscription = this.eventBusService.on('stationDept').subscribe(val => {
      if (val && val['deptDataObj']) {
        this.selectedDeptData = val['deptDataObj'];
        // const objGetDept = {
        //   deptId: this.deptDataObj.deptId
        // };
        // this.permissionService.findCatDeptByPost(objGetDept).subscribe(res => {
        //   if (res && res.content && res.content.listData && res.content.listData.length) {
        //     const deptArr = res.content.listData.filter(value => {
        //       return value.deptId === this.deptDataObj.deptId;
        //     });
        //     if (deptArr.length) {
        //       this.displayField = {deptName: deptArr[0]['deptName']};
        //     }
        //   }
        // });
      }
    });
    this.subscription = this.eventBusService.on('stationSave').subscribe(val => {
      if (val && val['deptDataObj']) {
        this.selectedDeptData = val['deptDataObj'];
      }
      if (val && val['locationDataObj']) {
        this.selectedLocationData = val['locationDataObj'];
      }
    });
    this.subscription = this.eventBusService.on('pillarList').subscribe(val => {
      if (val && val['deptDataObj']) {
        this.selectedDeptData = val['deptDataObj'];
      }
    });
    this.subscription = this.eventBusService.on('pillarSave').subscribe(val => {
      if (val && val['deptDataObj']) {
        this.selectedDeptData = val['deptDataObj'];
      } else {
        this.selectedDeptData = null;
      }
      if (val && val['locationDataObj']) {
        this.selectedLocationData = val['locationDataObj'];
      }
      if (val && val['laneDataObj']) {
        this.selectedLaneData = val['laneDataObj'];
      }
    });
    this.subscription = this.eventBusService.on('poolList').subscribe(val => {
      if (val && val['deptDataObj']) {
        this.selectedDeptData = val['deptDataObj'];
      }
    });
    this.subscription = this.eventBusService.on('poolSave').subscribe(val => {
      if (val && val['deptDataObj']) {
        this.selectedDeptData = val['deptDataObj'];
      }
      if (val && val['locationDataObj']) {
        this.selectedLocationData = val['locationDataObj'];
      }
    });
    this.subscription = this.eventBusService.on('pillar').subscribe(val => {
      if (val && val['pillarDataObj']) {
        this.selectedPillarData = val['pillarDataObj'];
      }
    });
    this.subscription = this.eventBusService.on('pool').subscribe(val => {
      if (val && val['poolDataObj']) {
        this.selectedPoolData = val['poolDataObj'];
      }
    });
    this.subscription = this.eventBusService.pillarDataObjChange.subscribe(val => {
      if (val && val['pillarDataObj']) {
        this.selectedPillarData = val['pillarDataObj'];
        this.selectedPoolData = null;
      }
    });
    // create by vanba
    this.subscription = this.eventBusService.poolDataObjChange.subscribe(val => {
      if (val && val['poolDataObj']) {
        this.selectedPoolData = val['poolDataObj'];
        this.selectedPillarData = null;
      }
    });
    this.subscription = this.eventBusService.on('sleeveSave').subscribe(val => {
      if (val && val['deptDataObj']) {
        this.selectedDeptData = val['deptDataObj'];
      }
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    // if (changes.control && changes.control.currentValue) {
    //   // console.log(changes.control.currentValue);
    //   setTimeout(() => {
    //     // console.log(changes.control.currentValue);
    //     if (this.displayFieldType === 'station') {
    //       this.displayField = {stationCode: changes.control.currentValue.value.label};
    //     } else if (this.displayFieldType === 'dept') {
    //       this.displayField = {deptName: changes.control.currentValue.value.label};
    //     } else if (this.displayFieldType === 'location') {
    //       this.displayField = {pathLocalName: changes.control.currentValue.value.label};
    //     }
    //   }, 800);
    // }
  }

  ngOnDestroy(): void {
    if (this.deptDataObjSub) {
      this.deptDataObjSub.unsubscribe();
    }
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  onShow() {
    if (this.isOpen) {
      return;
    }
    this.isOpen = !this.isOpen;
    let returnTab;
    this.eventBusService.dataChange.subscribe(val => {
      console.log(val);
      returnTab = val.data;
    }).unsubscribe();
    const config: DynamicDialogConfig = {
      header: this.header,
      width: this.width,
      contentStyle: { 'overflow': 'auto' },
      closeOnEscape: false,
      styleClass: 'autocomplete-modal'
    };
    this.eventBusService.emitDataChange({
      type: 'onTabChangeActive',
      data: {
        tabConfig: config,
        return: returnTab
      }
    });
    const ref: DynamicDialogRef = this.dialogService.open(this.contentType, config);
    ref.onClose.subscribe(res => {
      this.isDelete = false;
      this.isOpen = !this.isOpen;
      if (res) {
        this.onSetThis(res);
      }
    });
    if (this.type === 'location') {
      if (this.selectedDeptData) {
        this.eventBusService.emit({
          selectedDeptData: this.selectedDeptData
        });
      } else if (this.pillarService.selectedDeptData) {
        this.eventBusService.emit({
          selectedDeptData: this.pillarService.selectedDeptData
        });
      }
    }
  }

  onRowSelect(event) {
    if (event) {
      this.isStationSelect = true;
      this.onChange.emit(event);
      this.control.setValue(event);
      if (this.type === 'station') {
        this.selectedStationData = event;
      } else if (this.type === 'dept') {
        this.selectedDeptData = event;
      } else if (this.type === 'location') {
        this.selectedLocationData = event;
      } else if (this.type === 'laneCode') {
        this.selectedLaneData = event;
      } else if (this.type === 'pillar') {
        this.selectedPillarData = event;
      } else if (this.type === 'pool') {
        this.selectedPoolData = event;
      } else if (this.type === 'laneSleeve') {
        this.selectedLaneSleeveData = event;
      }
      if (this.displayFieldType === 'station') {
        this.displayField = { stationCode: event.stationCode };
        this.displayFieldTooltip = event.stationCode;
      } else if (this.displayFieldType === 'dept') {
        this.displayField = { pathName: event.pathName };
        this.displayFieldTooltip = event.pathName;
      } else if (this.displayFieldType === 'location') {
        this.displayField = { pathLocalName: event.pathLocalName };
        this.displayFieldTooltip = event.pathLocalName;
      } else if (this.displayFieldType === 'laneCode') {
        this.displayField = { laneCode: event.laneCode };
        this.displayFieldTooltip = event.laneCode;
      } else if (this.displayFieldType === 'pillar') {
        this.displayField = { pillarCode: event.pillarCode };
        this.displayFieldTooltip = event.pillarCode;
      } else if (this.displayFieldType === 'pool') {
        this.displayField = { poolCode: event.poolCode };
        this.displayFieldTooltip = event.poolCode;
      } else if (this.displayFieldType === 'laneSleeve') {
        this.displayField = { laneCode: event.laneCode };
        this.displayFieldTooltip = event.laneCode;
      }
    }
  }

  onSetThis(res) {
    this.isStationSelect = true;
    this.control.setValue(res);
    this.onChange.emit(res);
    if (res instanceof Array) {
      this.displayField = { displayField: this.app.translation.translate(`${this.selectedItemsLabel}`, { 0: res.length }) };
    } else if (res instanceof Object) {
      const rest: any = res;
      if (this.displayFieldType === 'station') {
        this.displayField = { stationCode: rest.stationCode };
        this.displayFieldTooltip = rest.stationCode;
      } else if (this.displayFieldType === 'dept') {
        this.displayField = { pathName: rest.pathName };
        this.displayFieldTooltip = rest.pathName;
      } else if (this.displayFieldType === 'location') {
        this.displayField = { pathLocalName: rest.pathLocalName };
        this.displayFieldTooltip = rest.pathLocalName;
      } else if (this.displayFieldType === 'laneCode') {
        this.displayField = { laneCode: rest.laneCode };
        this.displayFieldTooltip = rest.laneCode;
      } else if (this.displayFieldType === 'pillar') {
        this.displayField = { pillarCode: rest.pillarCode };
        this.displayFieldTooltip = rest.pillarCode;
      } else if (this.displayFieldType === 'pool') {
        this.displayField = { poolCode: rest.poolCode };
        this.displayFieldTooltip = rest.poolCode;
      } else if (this.displayFieldType === 'laneSleeve') {
        this.displayField = { laneCode: rest.laneCode };
        this.displayFieldTooltip = rest.laneCode;
      }
    }
    if (this.type === 'station') {
      this.selectedStationData = res;
    } else if (this.type === 'dept') {
      this.selectedDeptData = res;
    } else if (this.type === 'location') {
      this.selectedLocationData = res;
    } else if (this.type === 'laneCode') {
      this.selectedLaneData = res;
    } else if (this.type === 'pillar') {
      this.selectedPillarData = res;
    } else if (this.type === 'pool') {
      this.selectedPoolData = res;
    } else if (this.type === 'laneSleeve') {
      this.selectedLaneSleeveData = res;
    }
  }

  onSearchData(event, type?: string) {
    this.type = type;
    if (type === 'station') {
      this.isStationSelect = false;
      this.isStation = true;
      this.stationService.findAdvanceStation({ stationCode: event.query }).subscribe(res => {
        if (res.content.listData) {
          this.filteredStation = res.content.listData;
        } else {
          this.filteredStation = [];
        }

        // setTimeout(() => {
        //   if (!this.isStationSelect) {
        //     subscribe.unsubscribe();
        //   }
        // }, 3000);
      });
    } else if (type === 'dept') {
      this.permissionService.filterDept(event.query).subscribe(res => {
        this.filteredDept = res.content.listData;
      });
    } else if (type === 'location') {
      const dataObj = {
        deptId: this.selectedDeptData.deptId ? this.selectedDeptData.deptId : null,
        locationId: null,
        locationName: event.query,
        isTree: 0
      };
      // this.permissionService.filterLocation(event.query).subscribe(res => {
      this.permissionService.getTreeNodeLocationList(dataObj).subscribe((res) => {
        this.filteredLocation = res.content.listData;
      });
    } else if (type === 'laneCode') {
      const dataObj = {
        laneCode: event.query
      };
      this.pillarService.getAutocompleteLaneCodeList(dataObj).subscribe(res => {
        this.filteredLaneCode = res.content.listData;
      });
    } else if (type === 'pillar') {
      const dataObj = {
        pillarCode: event.query
      };
      this.pillarService.getPillarList(dataObj).subscribe(res => {
        this.filteredPillar = res.content.listData;
      });
    } else if (type === 'pool') {
      const dataObj = [{
        poolCode: event.query
      }];
      this.poolService.findAdvancePool(dataObj).subscribe(res => {
        this.filteredPool = res.content.listData;
      });
    } else if (type === 'laneSleeve') {
      if (this.selectedPillarData) {
        const dataObj = {
          pillarId: this.selectedPillarData.pillarId,
          laneCode: event.query,
        };
        this.pillarService.getListLaneCodeHangPillar(dataObj).subscribe(res => {
          this.filteredLaneSleeve = res.content.listData;
        });
      }
      if (this.selectedPoolData) {
        const dataObj = {
          poolId: this.selectedPoolData.poolId ? this.selectedPoolData.poolId : this.selectedPoolData,
          laneCode: event.query,
        };
        this.pillarService.getListLaneCodeHangPillar(dataObj).subscribe(res => {
          this.filteredLaneSleeve = res.content.listData;
        });
      }
    }
  }

  onClear() {
    this.control.setValue(undefined);
    this.isDelete = true;
    this.onClean.emit();
    this.displayField = { displayField: '' };
    this.displayFieldTooltip = '';
    if (this.type === 'station') {
      this.selectedStationData = undefined;
    } else if (this.type === 'dept') {
      this.selectedDeptData = undefined;
    } else if (this.type === 'location') {
      this.selectedLocationData = undefined;
    } else if (this.type === 'laneCode') {
      this.selectedLaneData = undefined;
    } else if (this.type === 'pillar') {
      this.selectedPillarData = undefined;
    } else if (this.type === 'pool') {
      this.selectedPoolData = undefined;
    } else if (this.type === 'laneSleeve') {
      this.selectedLaneSleeveData = undefined;
    }
  }

  onKeyUp(event) {
    if (event) {
      if (event.currentTarget.value === '') {
        this.displayFieldTooltip = '';
      }
    }
  }

  onBlur(event) {
    if (event) {
      // console.log(event);
      if (!this.isStationSelect && this.isStation) {
        this.onClear();
        this.isStation = false;
      }
      if (event.currentTarget.value === '') {
        this.control.setValue(undefined);
      }
      if (!this.disabled) {
        if (this.type === 'station') {
          if ((this.filteredStation && this.filteredStation.length === 0) || this.selectedStationData === undefined) {
            this.onBlurx.emit();
            this.displayField = { stationCode: '' };
          }
        } else if (this.type === 'dept') {
          if ((this.filteredDept && this.filteredDept.length === 0) || this.selectedDeptData === undefined) {
            this.displayField = { pathName: '' };
          }
        } else if (this.type === 'location') {
          if ((this.filteredLocation && this.filteredLocation.length === 0) || this.selectedLocationData === undefined) {
            this.displayField = { pathLocalName: '' };
          }
        } else if (this.type === 'laneCode') {
          if ((this.filteredLaneCode && this.filteredLaneCode.length === 0) || this.selectedLaneData === undefined) {
            this.displayField = { laneCode: '' };
          }
        } else if (this.type === 'pillar') {
          if ((this.filteredPillar && this.filteredPillar.length === 0) || this.selectedPillarData === undefined) {
            this.displayField = { pillarCode: '' };
          }
        } else if (this.type === 'pool') {
          if ((this.filteredPool && this.filteredPool.length === 0) || this.selectedPoolData === undefined) {
            this.displayField = { poolCode: '' };
          }
        } else if (this.type === 'laneSleeve') {
          if ((this.filteredLaneSleeve && this.filteredLaneSleeve.length === 0) || this.selectedLaneSleeveData === undefined) {
            this.displayField = { laneCode: '' };
          }
        }
      }
    }
  }


}
