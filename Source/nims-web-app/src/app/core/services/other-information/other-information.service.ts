import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommonUtils } from '@app/shared/services';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class OtherInformationService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('emp', 'otherInformation', httpClient, helperService);
  }

  /**
   * saveOrUpdateOther
   */
  public saveOrUpdateOther(item: any): Observable<any> {
    const url = `${this.serviceUrl}/create-other-info`;
    return this.postRequest(url, CommonUtils.convertData(item));
  }
}
