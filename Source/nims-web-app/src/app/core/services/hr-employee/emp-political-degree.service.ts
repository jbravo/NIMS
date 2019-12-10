import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { CommonUtils } from '@app/shared/services';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EmpPoliticalDegreeService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('emp', 'empPoliticalDegree', httpClient, helperService);
  }

    /**
   * find emp bank account id
   */
  public findByEmployeeId(employeeId: number) {
    const emp = CommonUtils.nvl(employeeId);
    const url = `${this.serviceUrl}/${emp}/emp-political-degree`;
    return this.getRequest(url);
  }

   /**
   * actionSaveListEmpBankAccount
   */
  public saveListEmpPoliticalDegree(data: any, employeeId: number) {
    const emp = CommonUtils.nvl(employeeId);
    const url = `${this.serviceUrl}/${emp}/update-emp-political-degree`;
    return this.httpClient.post(url, CommonUtils.convertData(data))
    .pipe(
      tap( // Log the result or error
        res => this.helperService.APP_TOAST_MESSAGE.next(res),
        error => {
          this.helperService.APP_TOAST_MESSAGE.next(error);
        }
      ),
      catchError(this.handleError)
    );
  }
}
