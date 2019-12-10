export class RadioSuggestionInforModel {
  suggestId: number;
  stationTechType: number; // phân loại theo công ngheẹ trạm*
  stationSolutionType: number; // phân loại trạm theo công nghê giải pháp phủ sóng*
  radioLocationType: number; // Phân loại địa hình theo vô tuyến (*)
  radioPillarType: number; // Loại cột *
  radioPillarHeight: number; // độ cao cột *
  hasConcavePoint: number; // có xử lý lõm hay không
  heightWithConcavePoint: number; // Độ cao tương đối của vị trí đặt trạm so với vùng lõm (m)
  concavePointPopulation2g: number; // số dấn lõm phủ được 2g
  concavePointPopulation3gVoice: number; // Số dân lõm phủ được_3G thoại
  concavePointPopulation3gData: number; // Số dân lõm phủ được_ 3G data
  concavePointPopulation4g: number; // số dấn lõm phủ được 2g
  specialArea: number; // Khu vực đặc biệt hay không
  specialAreaType: number; // Loại khu vực đặc biệt (*)
  specialAreaPopulation2g: number; // số dân lõm lượng hóa tại khu vực đặc biệt 2g
  specialAreaPopulation3gVoice: number; // số dân lõm lượng hóa tại khu vực đặc biệt 3g thoại
  specialAreaPopulation3gData: number; // số dân lõm lượng hóa tại khu vực đặc biệt 3g dữ liệu
  specialAreaPopulation4g: number; // số dân lõm lượng hóa tại khu vực đặc biệt 4g
  avgDbm2g: string; // Mức thu trung bình Outdoor/Indoor (dBm) _2G
  avgDbm3g: string; // Mức thu trung bình Outdoor/Indoor (dBm) _3G
  avgDbm4g: string; // Mức thu trung bình Outdoor/Indoor (dBm) _4G
  avgDbmCompetitor2g: number; // Mức thu trung bình của đối thủ mạnh nhất (dBm)_2G
  avgDbmCompetitor3g: number; // Mức thu trung bình của đối thủ mạnh nhất (dBm)_3G
  avgDbmCompetitor4g: number; // Mức thu trung bình của đối thủ mạnh nhất (dBm)_4G
  compareWithCompetitor2g: number; // So sánh với sóng đối thủ_2G
  compareWithCompetitor3g: number; // So sánh với sóng đối thủ_3G
  compareWithCompetitor4g: number; // So sánh với sóng đối thủ_4G
  suggestReason: string; // Nguyên nhân đề xuất
  concaveCode: number; // mã vùng lõm ???
}
