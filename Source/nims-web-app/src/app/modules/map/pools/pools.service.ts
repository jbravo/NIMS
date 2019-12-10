import {Injectable} from '@angular/core';
import {BasicService} from "@app/core/services/basic.service";
import {HttpClient} from "@angular/common/http";
import {HelperService} from "@app/shared/services/helper.service";
import {CommonUtils} from "@app/shared/services";

@Injectable({
  providedIn: 'root'
})
export class PoolsService extends BasicService {
  defaultUrl

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    // this.defaultUrl = "http://localhost:8082/nims/geo/infraPools";
    super('nimsGeoService', 'infraPools', httpClient, helperService);
  }


  public findByBbox(data: any): any {
    const url = this.serviceUrl + "/find-by-bbox";
    return this.postRequest(url, CommonUtils.convertData(data));
  }
}
