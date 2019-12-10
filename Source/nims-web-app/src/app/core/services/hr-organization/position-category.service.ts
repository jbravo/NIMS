import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class PositionCategoryService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('org', 'position', httpClient, helperService);
  }

  public getListStatus(): Observable<any> {
    const urlStatus = `${this.serviceUrl}/status`;
    return this.getRequest(urlStatus);
  }

  public getListLevelCareer(): Observable<any> {
    const listLevelCareerUrl = `${this.serviceUrl}/career-levels`;
    return this.getRequest(listLevelCareerUrl);
  }

  public getPositionDetail(positionId: number): Observable<any> {
    const listView = `${this.serviceUrl}/view-details/${positionId}`;
    return this.getRequest(listView);
  }
}
