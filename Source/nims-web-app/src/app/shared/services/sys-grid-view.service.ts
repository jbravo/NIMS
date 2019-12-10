import {Injectable} from '@angular/core';
import {BasicService} from '@app/core/services/basic.service';
import {HelperService} from '@app/shared/services/helper.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CommonUtils} from '@app/shared/services';

@Injectable({
  providedIn: 'root'
})
export class SysGridViewService extends BasicService {
  defaultUrl = `${this.serviceUrl}`;

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'sysGridView', httpClient, helperService);
  }

  public getGridView(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/getGridView', CommonUtils.convertData(item));
  }

  public getDeptByUserId(): Observable<any> {
    return this.getRequest(this.defaultUrl + '/getUserInfor');
  }

  public saveGridView(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/setGridView', CommonUtils.convertData(item));
  }
}
