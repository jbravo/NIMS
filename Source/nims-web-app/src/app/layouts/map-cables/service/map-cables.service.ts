import {Injectable} from '@angular/core';
import {HelperService} from "@app/shared/services/helper.service";
import {HttpClient} from "@angular/common/http";
import {BasicService} from "@app/core/services/basic.service";
import {Observable} from "rxjs/Rx";
import {CommonUtils} from "@app/shared/services";

@Injectable({
  providedIn: 'root'
})
export class MapCablesService extends BasicService {

  defaultUrl = `${this.serviceUrl}`;

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'infraCables', httpClient, helperService);
  }

  public findCableById(item: any) {
    console.log(item);
    return this.getRequest( this.defaultUrl + 'find/' + item);
  }

  public saveCables(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/save', CommonUtils.convertData(item));
  }
}
