// import { CommonUtils } from '@app/shared/services';
import {Injectable} from "@angular/core";
import {BasicService} from "../basic.service";
import {HttpClient} from "@angular/common/http";
// import { Observable } from 'rxjs';
import {HelperService} from "@app/shared/services/helper.service";
import {CommonUtils} from "@app/shared/services";


@Injectable({
  providedIn: 'root'
})
export class MapInfraPillarService extends BasicService {
  defaultUrl

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    // this.defaultUrl = "http://localhost:8082/nims/geo/infraPillars";
    super('nimsGeoService', 'infraPillar', httpClient, helperService);
  }

  public findByBbox(data: any): any {
    const url = this.serviceUrl + "/findByBbox";
    return this.postRequest(url, CommonUtils.convertData(data));
  }

  public update(data: any): any{
    const url = this.serviceUrl + "/update";
    return this.postRequest(url, CommonUtils.convertData(data));
  }

  public findByData(data: any): any {
    const url = this.serviceUrl + "/findByData";
    return this.postRequest(url, CommonUtils.convertData(data));
  }

}
