import {SuggestionSector3gDTOModel} from '@app/modules/suggest-station/model/sector/SuggestionSector3gDTO.model';
import {SuggestionSector4gDTOModel} from '@app/modules/suggest-station/model/sector/SuggestionSector4gDTO.model';

export class SuggestionCallOff4gDTOModel {
  suggestionCallOffId: number;
  cabinetCode: string; // mã trạm*
  cabinetCodeSuggest: string; // mã trạm đề xuất*
  expectedBroadcastDate: string; // ngày dự kiến phát sóng
  cabinetSolutionType: string; // Phân loại trạm theo giải phá phủ sóng
  btsStationType: number; // Phân loại nhà trạm : 1- Indoor/ 2- Outdoor
  bandwidth: number; // băng tần
  deviceTypeId: any; // tên thiết bị ??
  purposeType: number; // Loại trạm: 1 - VP; 2 - LL ; 3 -VP+LL
  transType: number; // Loại truyền dẫn: - 0=Cáp đồng; - 1=Cáp quang; - 2=Viba; - 3=Vsat
  transInterface: number; // Giao diện truyền dẫn: 2G: E1/GE, 3G:FE/GE, 4G: GE/10GE
  transCapacity: number; // Dung lượng truyền dẫn
  cfgSoftware: number; // Cấu hình phần mềm
  cfgHardwave: number; // Cấu hình phần cứng
  cfg: number; // Cấu hình CE
  isBuildingEdge: number; // Triển khai cột ở các mep tòa nhà: 1- Có/ 0- Không
  designer: string; // người thiết kế
  stationPurpose: string; // mục đích đặt trạm
  radioPillarType: number; // loại cột
  radioPillarHeight: number;
  suggestionSector4gDTOList: SuggestionSector4gDTOModel[];
  itemId: number;
}
