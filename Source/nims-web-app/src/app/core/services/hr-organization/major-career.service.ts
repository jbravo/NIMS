import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class MajorCareerService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('org', 'majorCareer', httpClient, helperService);
  }

  public getListMajorCareer(lineOrgId: number): Observable<any> {
    const listMajorCareerUrl = `${this.serviceUrl}/by-line-org/${lineOrgId}`;
    return this.getRequest(listMajorCareerUrl);
  }
}
