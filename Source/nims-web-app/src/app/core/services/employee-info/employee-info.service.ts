import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { CommonUtils } from '@app/shared/services';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EmployeeInfoService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('emp', 'employeeInfo', httpClient, helperService);
  }

  public updateEmployeeInfo(data: any): Observable<any> {
    const url = `${this.serviceUrl}/update-personal-information`;
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
