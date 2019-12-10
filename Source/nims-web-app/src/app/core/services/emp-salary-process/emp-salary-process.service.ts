import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';


@Injectable({
  providedIn: 'root'
})
export class EmpSalaryProcessService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('emp', 'salaryProcess', httpClient, helperService);
  }

  public getSalaryGrade(): Observable<any> {
    const urlStatus = `${this.serviceUrl}/salarygrade`;
    return this.getRequest(urlStatus);
  }
}
