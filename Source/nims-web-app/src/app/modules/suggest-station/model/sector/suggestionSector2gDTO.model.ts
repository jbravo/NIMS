export class SuggestionSector2gDTOModel {
  suggestionSector2gId: number;
  sectorCode: string; // tên sector
  sectorCfg: number; // cấu hình sector
  isJointSector: number; // có ghép sector hay không
  numberSplitterPerSector: number; // Số splitter cần/sector (*)
  jointSectorCode: string; // Mã Sector ghép (*)
  isJointRru: number; // Có ghép RRU hay không? (*)
  numRruPerSector: number; // Số RRU/sector (*)
  increaseCapacitySectorCode: string; // Sector tăng công suất (*)
  isJointAntena: number; // Có dùng chung anten hay không? (*)
  jointAntenaSectorCode: string; // Mã sector dùng chung anten
  diplexerType: number; // Loại diplexer
  numDiplexerPerSector: number; // Số Diplexer sử dụng/sector
  antenaHeight: number; // Chiều cao anten so với chân cột (*)
  antenaHeightGround: number; // Chiều cao anten so với mặt đất (*)
  antenaTypeId: number; // Loại anten (*)
  rruHeight: number; // Khoảng cách anten đến đáy RRU (*)
  lng: number; // kinh độ *
  lat: number; // vĩ độ *
  azimuth: number; // góc azimuth *
  tiltMechan: number; // Góc tilt cơ (*)
  tiltElec: number; // Góc tilt điện (*)
}
