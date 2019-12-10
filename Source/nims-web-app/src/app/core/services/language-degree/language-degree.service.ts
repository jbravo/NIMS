import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { CommonUtils } from '@app/shared/services';
import { BasicService } from '../basic.service';

@Injectable({
  providedIn: 'root'
})
export class LanguageDegreeService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('emp', 'empLanguaeDegree', httpClient, helperService);
  }

    /**
   * find emp language degree id
   */
  public findByEmployeeId(employeeId: number) {
    const emp = CommonUtils.nvl(employeeId);
    const url = `${this.serviceUrl}/find-by-employee/${emp}`;
    return this.getRequest(url);
  }
}
