import { CommonUtils } from '@app/shared/services';
import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class SysCatService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('sys', 'sysCat', httpClient, helperService);
  }

  public getAllBySysCatTypeCode(code: string): Observable<any> {
    const url = `${this.serviceUrl}/by-nation/${CommonUtils.getNationId()}/by-sys-cat-types/${code}`;
    return this.getRequest(url);
  }
}
