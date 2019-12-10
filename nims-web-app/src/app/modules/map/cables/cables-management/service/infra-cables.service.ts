import {Injectable} from '@angular/core';
import {HelperService} from "@app/shared/services/helper.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Rx";
import {CommonUtils} from "@app/shared/services";
import {BasicService} from "@app/core/services/basic.service";

@Injectable({
  providedIn: 'root'
})
export class InfraCablesService extends BasicService{

  defaultUrl = `${this.serviceUrl}`;

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'infraCables', httpClient, helperService);
  }

  public findCableById(item: any) {
    console.log(item);
    return this.getRequest(this.defaultUrl + '/find/cables/' + item);
  }

  public saveCables(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/save', CommonUtils.convertData(item));
  }

}
