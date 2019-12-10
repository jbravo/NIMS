import { catchError } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BasicService } from './basic.service';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class DataPickerService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('emp', 'dataPicker', httpClient, helperService);
  }
  /**
   * action load org tree
   */
  public actionInitAjax(nationId: number, params: any): Observable<any> {
    const url = `${this.serviceUrl}/${nationId}/action-init-ajax`;
    return this.postRequest(url, params);
  }
  /**
   * findOne
   * param id
   */
  public findByNationId(nationId: number, id: number, params: any): Observable<any> {
    const url = `${this.serviceUrl}/${nationId}/${id}`;
    return this.postRequest(url, params);
  }
}
