import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WorkProcessService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('emp', 'workProcess', httpClient, helperService);
  }

  public getDraftProcess(employeeId: number): Observable<any> {
    const getDraftProcessUrl = `${this.serviceUrl}/get-draft-process/${employeeId}`;
    return this.getRequest(getDraftProcessUrl);
  }
}
