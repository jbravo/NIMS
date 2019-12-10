import {SuggestionSector3gDTOModel} from '@app/modules/suggest-station/model/sector/SuggestionSector3gDTO.model';
export class SuggestionCallOff3gDTOModel {
  suggestionCallOffId: number;
  cabinetCode: string; // mã trạm*
  cabinetCodeSuggest: string; // mã trạm đề xuất*
  expectedBroadcastDate: string; // ngày dự kiến phát sóng
  btsStationType: number; // Phân loại trạm theo giải phá phủ sóng
  bandwidth: number; // băng tần
  deviceTypeId: any; // loại thiết bị (tập trung.phân tân)* ???
  itemId: any; // ???
  purposeType: number;  // Loại trạm(VP/LL)
  radioPillarType: number; // loại cột*
  radioPillarHeight: number; // độ cao cột*
  transType: number; // loại truyền dẫn*
  transInterface: number; // giao diện truyền dẫn*
  transCapacity: number; // dung lượng truyền dẫn*
  cfg: number; // cấu hình ce*
  isBuildingEdge: number; // triển khai cột ở các mép toàn nhà
  designer: string; // người thiết kế
  stationPurpose: string; // mục đích đặt trạm ???
  suggestionSector3gDTOList: SuggestionSector3gDTOModel[];
  purtationType: number; // phân loại nhà trạm indoor/outdoor ???
  numberSector: number;
}
