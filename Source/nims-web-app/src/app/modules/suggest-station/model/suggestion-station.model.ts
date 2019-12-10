import {SuggestionNewSiteModel} from '@app/modules/suggest-station/model/suggestion-new-site.model';
import {SuggestionCallOff2gDTOModel} from '@app/modules/suggest-station/model/Call-off/SuggestionCallOff2gDTO.model';
import {SuggestionCallOff3gDTOModel} from '@app/modules/suggest-station/model/Call-off/SuggestionCallOff3gDTO.model';
import {SuggestionCallOff4gDTOModel} from '@app/modules/suggest-station/model/Call-off/SuggestionCallOff4gDTO.model';
import {RadioSuggestionInforModel} from '@app/modules/suggest-station/model/radioSuggestionInfor.model';

export class SuggestionStationModel {
  suggestId: number;
  suggestType: number;
  suggestCode: string;
  deptId: number;
  suggestStatus: number;
  userName: string;
  createTime: string;
  beforeDate: Date;
  afterDate: Date;
  updateTime: Date;
  rowStatus: number;
  suggestionNewSiteDTO: SuggestionNewSiteModel;
  suggestionCallOff2gDTOList: SuggestionCallOff2gDTOModel[];
  suggestionCallOff3gDTOList: SuggestionCallOff3gDTOModel[];
  suggestionCallOff4gDTOList: SuggestionCallOff4gDTOModel[];

}
