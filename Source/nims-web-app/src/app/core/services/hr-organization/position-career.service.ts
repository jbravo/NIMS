import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class PositionCareerService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('org', 'positionCareer', httpClient, helperService);
  }

  /**
  * action load line-org, major career tree
  */
  public actionInitAjax(nationId: number, params: any): Observable<any> {
    const url = `${this.serviceUrl}/${nationId}/line-orgs`;
    return this.postRequest(url, params);
  }

}
