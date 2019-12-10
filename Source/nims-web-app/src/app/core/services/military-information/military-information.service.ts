import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';
import { CommonUtils } from '@app/shared/services';

@Injectable({
  providedIn: 'root'
})
export class MilitaryInformationService extends BasicService  {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('emp', 'employeeInfo', httpClient, helperService);
  }

  /**
   * updateMilitary
   */
  public updateMilitaryInfo(item: any): Observable<any> {
    const url = `${this.serviceUrl}/update-military-info`;
    return this.postRequest(url, CommonUtils.convertData(item));
    
  }
}
