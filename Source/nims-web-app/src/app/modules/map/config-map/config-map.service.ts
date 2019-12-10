import { Injectable } from '@angular/core';
import {BasicService} from "@app/core/services/basic.service";
import {HelperService} from "@app/shared/services/helper.service";
import {HttpClient} from "@angular/common/http";
import {CommonUtils} from "@app/shared/services";
import {Observable} from "rxjs/Rx";

@Injectable({
  providedIn: 'root'
})
export class ConfigMapService extends BasicService {
  defaultUrl = `${this.serviceUrl}`;

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('political', 'configMap', httpClient, helperService);
  }

  public saveConfigCommon(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/saveCommon', CommonUtils.convertData(item));
  }

  public getInfoConfigMap(item:number) {
    return this.getRequest(this.defaultUrl + '/getInfo/' + item);
  }
}
