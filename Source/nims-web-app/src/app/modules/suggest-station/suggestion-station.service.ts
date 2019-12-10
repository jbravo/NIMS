import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import { environment } from '@env/environment';
import {BasicService} from '@app/core/services/basic.service';
import {HelperService} from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class SuggestionStationService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsOptimalDesignService', 'suggestion', httpClient, helperService);
  }

// hàm tìm kiếm
  getList(suggestStationSearch: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi',
    });

    return this.postRequest(environment.serverUrl.nimsOptimalDesignService + `suggestion/search`, suggestStationSearch, {headers: headers, observe: 'response'});
  }

  getListStationTechType(stationType: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi',
    });

    return this.getRequest(environment.serverUrl.nimsOptimalDesignService + 'catitems/' + stationType, {headers: headers, observe: 'response'});
  }

  // phan loai tram theo cong nghe trien khai
  getListStationTechTypeTech(): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi',
    });

    return this.getRequest(environment.serverUrl.nimsOptimalDesignService + `suggestion/stationtechtypes`
      , {headers: headers, observe: 'response'});
  }

  // them mới trạm đề xuất
  createSuggestion(suggestion: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi'
    });

    return this.postRequest(environment.serverUrl.nimsOptimalDesignService + `suggestion/newsuggestion`,
      suggestion, {headers: headers, observe: 'response'});
  }

  // update trạm đề xuất
  updateSuggestion(suggestion: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi'
    });

    return this.putRequest(environment.serverUrl.nimsOptimalDesignService + `suggestion/newsuggestion`,
      suggestion, {headers: headers, observe: 'response'});
  }

  ///  trạm đề xuất theo id cho view
  getSuggestionById(id: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi'
    });

    return this.getRequest(environment.serverUrl.nimsOptimalDesignService + `suggestion/newsuggestion/`
      + id, {headers: headers, observe: 'response'});
  }

  // Lấy cây đơn vị phòng ban
  getDeptsByParentId(id?): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi'
    });

    return this.postRequest(environment.serverUrl.nimsOptimalDesignService + `search/getTreeDepartmentByDeptId`, {
      deptId: id,
      userName: 'nims_dev'
    }, {
      headers: headers,
      observe: 'response'
    }).pipe(map((item: any) => {
      console.log(item);
      let list = item.body.data;
      if (!list) {
        list = [];
      }
      list = list.map(elm => {
        return {
          'label': elm.deptName,
          'data': elm,
          'expandedIcon': 'fa fa-folder-open',
          'collapsedIcon': 'fa fa-folder',
          'leaf': false
        };
      });
      item.body.data = list;
      return item;
    }));
  }

  getSuggestionCodeByTypeNameAnd(suggestTypeId, stationCode): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi'
    });
    return this.getRequest(environment.serverUrl.nimsOptimalDesignService + `suggestion/suggestcode?suggestType=${suggestTypeId}&stationCode=${stationCode} `, {
      headers: headers,
      observe: 'response'
    });
  }

  setSuggestStatus(object: any): Observable<any> {
    return this.postRequest(environment.serverUrl.nimsOptimalDesignService + `suggestion/suggeststatus/change`, object);
  }

  deleteSuggestion(suggestion: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi'
    });

    return this.postRequest(environment.serverUrl.nimsOptimalDesignService + `suggestion/delete`,
      suggestion, {headers: headers, observe: 'response'});
  }

  getListStationTechTypeArray(stationType: any): Observable<any[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi',
    });

    return this.getRequest(environment.serverUrl.nimsOptimalDesignService + `catitems/`
      + stationType, {headers: headers}).pipe(map((res: any) => {
      return res.data;
    }));
  }
}
