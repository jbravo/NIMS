export class SuggestionSector3gDTOModel {
  suggestionSector3gId: number;
  sectorCode: string; // tên sector *
  sectorCfg: number; // Cấu hình trạm (*)
  isJointSector: number; // Có ghép sector hay không(*)
  numberSplitterPerSector: number; // Số splitter cần (*)
  jointSectorCode: number; // Mã Sector ghép (*)
  isJointRru: number; // Có ghép RRU hay không? (*)
  numRruPerSector: number; // Số RRU của trạm (*)
  increaseCapacitySectorCode: string; // Sector tăng công suất (*)
  isJointAntena: number; // Có dùng chung anten hay không? (*)
  jointAntenaSectorCode: string; // Mã sector dùng chung anten
  diplexerType: number; // Loại diplexer
  numDiplexerPerSector: number; // Số Diplexer sử dụng.
  antenaHeight: number; // Chiều cao anten so với chân cột (*)
  antenaHeightGround: number; // Chiều cao anten so với mặt đất (*)
  antenaTypeId: number; // Loại anten (*)
  rruHeight: number; // Khoảng cách anten đến đáy RRU (*)
  lng: number; // Kinh độ (*)
  lat: number; // Vĩ độ (*)
  azimuth: number; // Góc azimuth (*)
  tiltMechan: number; // Góc tilt cơ (*)
  tiltElec: number; // Góc tilt điện(*)

}
