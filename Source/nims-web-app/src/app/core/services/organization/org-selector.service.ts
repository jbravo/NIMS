import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';
import { CommonUtils } from '@app/shared/services';

@Injectable({
  providedIn: 'root'
})
export class OrgSelectorService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'sysOrganizations', httpClient, helperService);
  }
  /**
   * action search data table
   */
  public search(params: any): Observable<any> {
    const url = `${this.serviceUrl}/getListOrganizationForPopup`;
    return this.getRequest(url, { params: CommonUtils.buildParams(params)});
  }

  public searchOrganizationId(params: any): Observable<any> {
    const url = `${this.serviceUrl}/getSubListOrganizationForPopup`;
    return this.getRequest(url, { params: CommonUtils.buildParams(params)});
  }
}


