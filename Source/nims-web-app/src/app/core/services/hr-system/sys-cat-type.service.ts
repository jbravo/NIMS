import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { BasicService } from './../basic.service';
import { Injectable } from '@angular/core';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class SysCatTypeService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('sys', 'sysCatType', httpClient, helperService);
  }

  public findTransferNation(): Observable<any> {
    const url = `${this.serviceUrl}/nations`;
    return this.getRequest(url);
  }

  public transferNation(data): Observable<any> {
    const url = `${this.serviceUrl}/transfer`;
    return this.postRequest(url, data);
  }

  public getListSysCat(code: string): Observable<any> {
    const url = `${this.serviceUrl}/${code}/sys-cats`;
    return this.getRequest(url);
  }
}
