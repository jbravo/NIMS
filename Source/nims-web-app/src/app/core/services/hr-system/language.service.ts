import { CommonUtils } from '@app/shared/services';
import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class LanguageService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('sys', 'language', httpClient, helperService);
  }
  /**
   * findInfo
   * @param item any
   */
  public findInfo(item: any): Observable<any> {
    const url = `${this.serviceUrl}/info`;
    return this.postRequest(url, CommonUtils.convertData(item));
  }
  /**
   * saveInfo
   */
  public saveInfo(item: any): Observable<any> {
    const url = `${this.serviceUrl}/save_info`;
    return this.postRequest(url, CommonUtils.convertData(item));
  }

}
