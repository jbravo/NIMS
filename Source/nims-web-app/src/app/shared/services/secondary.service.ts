import { Injectable } from '@angular/core';
import { BasicService } from '@app/core/services/basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from './helper.service';
import { Observable } from 'rxjs';
import { CommonUtils } from './common-utils.service';

@Injectable({
  providedIn: 'root'
})
export class SecondaryService extends BasicService{

  defaultUrl = `${this.serviceUrl}`;
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'transmission', httpClient, helperService);
  }
  public filterDept(item: string) {
    console.log(this.defaultUrl + '/department/filter/' +item);
    return this.getRequest(this.defaultUrl + '/department/filter/' +item);
  }
}
