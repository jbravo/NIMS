import { CommonUtils } from '@app/shared/services';
import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class AppParamsService extends BasicService {
  defaultUrl = `${this.serviceUrl}`;
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'app-employee', httpClient, helperService);
  }


  public appParams(item: any) {
    return this.getRequest(this.defaultUrl + '/' + CommonUtils.convertData(item));
  }

  public saveAppParam(item: any): Observable<any> {
    // return this.httpClient.post(this.defaultUrl + '/save', CommonUtils.convertData(item));
    return this.postRequest(this.defaultUrl + '/save', CommonUtils.convertData(item));
  }

  public searchAppParam(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/search', CommonUtils.convertData(item));
  }
}

