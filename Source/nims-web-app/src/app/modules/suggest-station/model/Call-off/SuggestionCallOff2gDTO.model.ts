import {SuggestionSector2gDTOModel} from '@app/modules/suggest-station/model/sector/suggestionSector2gDTO.model';

export class SuggestionCallOff2gDTOModel {
  suggestionCallOffId: number; // id
  cabinetCode: string; // mã trạm
  cabinetCodeSuggest: string; // mã trạm đề xuất
  expectedBroadcastDate: Date; // ngày dự kiến phát sóng
  cabinetSolutionType: string; // phân loại trạm theo giải pháp phủ sóng
  bandwidth: string; // bang tần
  btsStationType: number; // phân loại nhà trạm indoor/outdoor
  purposeType: string; // loại trạm vp/ll/vp + ll
  transType: number; // loại truyền dẫn
  transInterface: number; // giao diện truyền dẫn
  transCapacity: number; // dung lượng truyền dẫn
  cfg: number; // cấu hình
  isBuildingEdge: number; // triển khai cột ở các mép tòa nhà
  designer: string; // người thiết kế
  deviceTypeId: number ; //// loại thiết bị ???
  itemId: any; // ???
  radioPillarType: string; // loại cột
  radioPillarHeight: string; // độ cao cột
  deviceType: string; // loại thiết bị
  suggestionSector2gDTOList: SuggestionSector2gDTOModel[]; // danh sach sector;
  purposeId: number; // ???
  stationPurpose: number;
  numberSector: number;
  // mục địch đặt trạm
}
