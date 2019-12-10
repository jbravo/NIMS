import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
    providedIn: 'root'
})
export class NationService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
      super('sys', 'nation', httpClient, helperService);
  }


  public getListNation(): Observable<any> {
    const url = `${this.serviceUrl}`;
    return this.getRequest(url);
  }

  public findSysCatTypes(): Observable<any> {
    const url = `${this.serviceUrl}/1740/sys_cat_types`;
    return this.getRequest(url);
  }


  /**
   * Lay danh sach nation Order theo isDefault & name
   * getNationList
   */
  public getNationList(): Observable<any> {
    const url = `${this.serviceUrl}/nationList`;
    return this.getRequest(url);
  }


  /**
   * Lay default Nation & hien thi tren combobox;
   * getDefaultNation
   */
  public getDefaultNation(): Observable<any> {
    const url = `${this.serviceUrl}/getDefaultNation`;
    return this.getRequest(url);
  }
}
