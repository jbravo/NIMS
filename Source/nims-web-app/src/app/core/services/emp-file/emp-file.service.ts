import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class EmpFileService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('emp', 'empFile', httpClient, helperService);
  }
  public listEmpFile(): Observable<any> {
    const urlStatus = `${this.serviceUrl}/listEmpFile`;
    return this.getRequest(urlStatus);
  }

}
