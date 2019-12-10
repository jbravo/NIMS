import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class LineOrgService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('org', 'lineOrg', httpClient, helperService);
  }
  public getListLineCareer(): Observable<any> {
    const listLineCareerUrl = `${this.serviceUrl}/`;
    return this.getRequest(listLineCareerUrl);
  }
}
