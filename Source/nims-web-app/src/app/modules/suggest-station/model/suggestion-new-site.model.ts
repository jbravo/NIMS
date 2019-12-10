import {RadioSuggestionInforModel} from '@app/modules/suggest-station/model/radioSuggestionInfor.model';

export class SuggestionNewSiteModel {
  stationCodeApproved?: string;
  suggestNewSiteId: number;
  stationCode: string;
  lng: number;
  lat: number;
  locationId: number;
  address: string;
  auditType: number;
  note: string;
  createTime: Date;
  updateTime: Date;
  rowStatus: string;
  checkView: any;
  suggestionRadioDTO: RadioSuggestionInforModel;
}
