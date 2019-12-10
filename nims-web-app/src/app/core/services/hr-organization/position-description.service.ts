import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class PositionDescriptionService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('org', 'positionDes', httpClient, helperService);
  }

  public getRequirementDetail(): Observable<any> {
    const urlStatus = `${this.serviceUrl}/list-requirement-detail`;
    return this.getRequest(urlStatus);
  }

  public getPositionDescriptionDetail(positionId: number): Observable<any> {
    const urlService = `${this.serviceUrl}/details/${positionId}`;
    return this.getRequest(urlService);
  }
}
