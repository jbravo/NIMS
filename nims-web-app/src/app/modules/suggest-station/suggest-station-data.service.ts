import { Injectable } from '@angular/core';
import { SuggestionStationModel } from '@app/modules/suggest-station/model/suggestion-station.model';
import { SuggestionNewSiteModel } from '@app/modules/suggest-station/model/suggestion-new-site.model';

@Injectable({
  providedIn: 'root'
})
export class SuggestStationDataService {
  list: any[] = [];
  listSPECIAL_AREA_TYPE: any[] = [];
  listCabinetSolutionTye: any[] = [];
  listSTATION_TYPE: any[] = [];
  listAntenType: any[] = [];
  listDiplexer: any[] = [];
  mainData: any = {
    // thongTinViTri: {
    //   thongTinChung: {
    //     suggestType: 1,
    //     suggestCode: '123abc',
    //     userName: 'SinhBeoNgu',
    //     beforeDate: new Date(),
    //     suggestStatus: 2
    //   },
    //   thongTinViTri: {
    //     stationCode: 'FuCk123',
    //     lng: 105.21312,
    //     lat: 20.50233,
    //     auditType: 1,
    //     locationId: 120,
    //     address: '83 Vu Trong Phung, Ha noi, Viet Nam',
    //     tenant: 2,
    //     note: 'Thái Bình là đất trồng tre, Trồng tre thì ít mà trồng me thì nhiều'
    //   }
    // },
    //
    // // Node Thong tin de xuat vo tuyen
    // thongTinDeXuatVoTuyen: {
    //   stationTechType: 2,
    //   stationSolutionType: 5,
    //   radiolocationType: 4,
    //   radioPillarType: 3,
    //   radioPillarHeight: 25,
    //   hasConcavePoint: 0,
    //   concaveCode: 2,
    //   heighWithConcavePoint: 23,
    //   concavePointPopulation2G: 23,
    //   concavePointPopulation3GVoice: 35,
    //   concavePointPopulation3GData: 14,
    //   concavePointPopulation4G: 6,
    //   specialArea: 1,
    //   specialAreaType: 1,
    //   specialAreaPopulation2g: 21,
    //   specialAreaPopulation3gVoice: 900,
    //   specialAreaPopulation3gData: 1000,
    //   specialAreaPopulation4g: 2094,
    //   avgdbm2g: 123,
    //   avgdbm3g: 343,
    //   avgdbm4g: 999,
    //   avgDbmCompetitor2G: 12.3,
    //   avgDbmCompetitor3G: 44.2,
    //   avgDbmCompetitor4G: 22.1,
    //   compareWithCompetitor2G: 'ss cc à ??? dồ',
    //   compareWithCompetitor3G: 'Không đủ tuổi',
    //   compareWithCompetitor4G: 'Khá đấy',
    //   suggestReason: 'Thích thì đề xuất'
    // },
    //
    // // Thong tin CallOffs
    // callOff2G: {
    //   cabinetCode: '1241ab',
    //   cabinetCodeSuggest: 'ZSV253',
    //   expectedBroadcastDate: new Date(),
    //   cabinetSolutionTye: 1,
    //   purtationType: 1,
    //   btsSdwidth: 2,
    //   tenthietbi: 'ZUNA',
    //   code_station: 1,
    //   expected_date: 2,
    //   radioPillarType: 3,
    //   radioPillarHeight: '1245',
    //   transType: 2,
    //   transInterface: 'FE/GE',
    //   transCapacity: '124GB',
    //   cfg: 'configurtation',
    //   stationPurpose: 10,
    //   isBuildingEdge: 'Tên biến này a vừa sửa đéo hiểu sao',
    //   designer: 'Minh Tuấn',
    //   // Sectors
    //   sectors: [
    //     {
    //       sectorCode: 'ScemAvc',
    //       sectorCfg: 'cnu.vmuiz.s',
    //       isJointSector: 1,
    //       numberSplitterPerSector: '4/3',
    //       jointSectorCode: '1zzaA',
    //       isJointRru: 0,
    //       numRruPerSector: 123,
    //       increaseCapacitySectorCode: 'zvdv',
    //       isJointAntena: 1,
    //       jointAntenaSectorCode: 'miuni',
    //       diplexerType: 0,
    //       numDiplexerPerSector: 444,
    //       antenaHeight: 121,
    //       antenaHeightGround: 1122,
    //       antenaTypeId: 2,
    //       rruHeight: 87,
    //       lng: 20.324,
    //       lat: 105.2312412,
    //       azimuth: 23,
    //       tiltMechan: 28,
    //       inputiltElect: 74
    //     }
    //   ]
    // },
    // callOff3G: {sectors: [{}]},
    // callOff4G: {sectors: [{}]}
  };

  constructor() {
  }

  resetData() {
    this.mainData = {
      thongTinViTri: {
        thongTinChung: {},
        thongTinViTri: {}
      },
      thongTinDeXuatVoTuyen: {},
      callOff2G: null,
      callOff3G: null,
      callOff4G: null,
    };
  }

  addSector(current: any) {
    if (current.id === 3) {
      if (!this.mainData.callOff2G) {
        this.mainData.callOff2G = {};
      }
      if (!this.mainData.callOff2G.sectors) {
        this.mainData.callOff2G.sectors = [];
      }
      this.mainData.callOff2G.sectors.push({});
    } else if (current.id === 4) {
      if (!this.mainData.callOff3G) {
        this.mainData.callOff3G = {};
      }
      if (!this.mainData.callOff3G.sectors) {
        this.mainData.callOff3G.sectors = [];
      }
      this.mainData.callOff3G.sectors.push({});
    } else {
      if (!this.mainData.callOff4G) {
        this.mainData.callOff4G = {};
      }
      if (!this.mainData.callOff4G.sectors) {
        this.mainData.callOff4G.sectors = [];
      }
      this.mainData.callOff4G.sectors.push({});
    }
  }

  copySector(current: any) {
    // {...this.mainData.callOff3G.sectors[current.index]};
    if (current.parent.id === 3) {
      this.mainData.callOff2G.sectors.push({ ...this.mainData.callOff2G.sectors[current.index] });
    } else if (current.parent.id === 4) {
      this.mainData.callOff3G.sectors.push({ ...this.mainData.callOff3G.sectors[current.index] });
    } else {
      this.mainData.callOff4G.sectors.push({ ...this.mainData.callOff4G.sectors[current.index] });
    }
  }

  mapperToRequest(mainData: any) {
    const suggestionDTO: any = {};
    // suggestionDTO.suggestId = mainData.thongTinViTri.thongTinChung.suggestId;
    suggestionDTO.suggestType = mainData.thongTinViTri.thongTinChung.suggestType;
    suggestionDTO.suggestCode = mainData.thongTinViTri.thongTinChung.suggestCode;
    suggestionDTO.suggestStatus = mainData.thongTinViTri.thongTinChung.suggestStatus;
    suggestionDTO.userName = mainData.thongTinViTri.thongTinChung.userName;
    suggestionDTO.beforeDate = mainData.thongTinViTri.thongTinChung.beforeDate;
    suggestionDTO.afterDate = mainData.thongTinViTri.thongTinChung.afterDate;
    suggestionDTO.updateTime = mainData.thongTinViTri.thongTinChung.updateTime;
    suggestionDTO.rowStatus = mainData.thongTinViTri.thongTinChung.rowStatus;
    suggestionDTO.suggestionNewSiteDTO = mainData.thongTinViTri.thongTinViTri;
    suggestionDTO.suggestionNewSiteDTO.suggestionRadioDTO = mainData.thongTinDeXuatVoTuyen;
    // Mã vùng lõm :
    suggestionDTO.suggestionNewSiteDTO.suggestionRadioDTO.concaveCode = [suggestionDTO.suggestionNewSiteDTO.suggestionRadioDTO.concaveCode];
    console.log('f1');
    if (mainData.callOff2G) {
      suggestionDTO.suggestionCallOff2gDTOList = [];
      suggestionDTO.suggestionCallOff2gDTOList.push((mainData.callOff2G));
      suggestionDTO.suggestionCallOff2gDTOList[0].suggestionSector2gDTOList = [];
      suggestionDTO.suggestionCallOff2gDTOList[0].suggestionSector2gDTOList.push(...mainData.callOff2G.sectors);
    }
    if (mainData.callOff3G) {
      suggestionDTO.suggestionCallOff3gDTOList = [];
      suggestionDTO.suggestionCallOff3gDTOList.push(mainData.callOff3G);
      suggestionDTO.suggestionCallOff3gDTOList[0].suggestionSector3gDTOList = [];
      suggestionDTO.suggestionCallOff3gDTOList[0].suggestionSector3gDTOList.push(...mainData.callOff3G.sectors);
    }
    if (mainData.callOff4G) {
      suggestionDTO.suggestionCallOff4gDTOList = [];
      suggestionDTO.suggestionCallOff4gDTOList.push(mainData.callOff4G);
      suggestionDTO.suggestionCallOff4gDTOList[0].suggestionSector4gDTOList = [];
      suggestionDTO.suggestionCallOff4gDTOList[0].suggestionSector4gDTOList.push(...mainData.callOff4G.sectors);
    }
    return suggestionDTO;
  }

  mapperToResponse(suggestionDTO: SuggestionStationModel, onlyView, save?: any) {
    if (!suggestionDTO.suggestionNewSiteDTO) {
      suggestionDTO.suggestionNewSiteDTO = new SuggestionNewSiteModel();
    }
    const mainData: any = {};
    mainData.thongTinViTri = {
      thongTinChung: {
        suggestType: suggestionDTO.suggestType,
        suggestCode: suggestionDTO.suggestCode,
        userName: suggestionDTO.userName,
        beforeDate: new Date(suggestionDTO.beforeDate),
        afterDate: suggestionDTO.afterDate,
        updateTime: new Date(suggestionDTO.updateTime),
        suggestStatus: suggestionDTO.suggestStatus,
        rowStatus: suggestionDTO.rowStatus,
        checkView: onlyView,
        checkSave: save,
      },
      thongTinViTri: {
        stationCodeApproved: suggestionDTO.suggestionNewSiteDTO.stationCodeApproved,
        stationCode: suggestionDTO.suggestionNewSiteDTO.stationCode,
        lng: this.removePad(suggestionDTO.suggestionNewSiteDTO.lng),
        lat: this.removePad(suggestionDTO.suggestionNewSiteDTO.lat),
        auditType: suggestionDTO.suggestionNewSiteDTO.auditType,
        locationId: suggestionDTO.suggestionNewSiteDTO.locationId,
        address: suggestionDTO.suggestionNewSiteDTO.address,
        tenant: null,
        note: suggestionDTO.suggestionNewSiteDTO.note
      }
    };
    console.log(mainData.thongTinViTri);
    // mainData.thongTinViTri.thongTinViTri = suggestionDTO.suggestionNewSiteDTO;
    mainData.thongTinDeXuatVoTuyen = {};
    mainData.thongTinDeXuatVoTuyen = suggestionDTO.suggestionNewSiteDTO.suggestionRadioDTO || {};


    const c2g: any = suggestionDTO.suggestionCallOff2gDTOList[0] || null;
    const c3g: any = suggestionDTO.suggestionCallOff3gDTOList[0] || null;
    const c4g: any = suggestionDTO.suggestionCallOff4gDTOList[0] || null;
    if (c2g) {
      mainData.callOff2G = c2g;
      mainData.callOff2G.expectedBroadcastDate = new Date(mainData.callOff2G.expectedBroadcastDate);
      mainData.callOff2G.radioPillarHeight = mainData.thongTinDeXuatVoTuyen.radioPillarHeight;
      mainData.callOff2G.radioPillarType = mainData.thongTinDeXuatVoTuyen.radioPillarType;
      mainData.callOff2G.sectors = [];
      mainData.callOff2G.sectors.push(...c2g.suggestionSector2gDTOList);
    }
    console.log(mainData);
    if (c3g) {
      mainData.callOff3G = c3g;
      mainData.callOff3G.expectedBroadcastDate = new Date(mainData.callOff3G.expectedBroadcastDate);
      mainData.callOff3G.radioPillarHeight = mainData.thongTinDeXuatVoTuyen.radioPillarHeight;
      mainData.callOff3G.radioPillarType = mainData.thongTinDeXuatVoTuyen.radioPillarType;
      mainData.callOff3G.sectors = [];
      mainData.callOff3G.sectors.push(...c3g.suggestionSector3gDTOList);
    }
    if (c4g) {
      mainData.callOff4G = c4g;
      mainData.callOff4G.sectors = [];
      mainData.callOff4G.expectedBroadcastDate = new Date(mainData.callOff4G.expectedBroadcastDate);
      mainData.callOff4G.radioPillarHeight = mainData.thongTinDeXuatVoTuyen.radioPillarHeight;
      mainData.callOff4G.radioPillarType = mainData.thongTinDeXuatVoTuyen.radioPillarType;
      mainData.callOff4G.sectors.push(...c4g.suggestionSector4gDTOList);
    }
    return mainData;
  }

  private removePad(lng) {
    const result = lng.toString();
    if (result.substring(result.length - 2, result.length) === '.0') {
      return result.substring(0, result.length - 2);
    }
    return result;
  }

  resetCallOf() {
    this.mainData.callOff2G = null;
    this.mainData.callOff3G = null;
    this.mainData.callOff4G = null;
  }

}
