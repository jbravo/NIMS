import {Injectable} from "@angular/core";
import {BasicService} from "../basic.service";
import {HttpClient} from "@angular/common/http";
import {HelperService} from "@app/shared/services/helper.service";
import {CommonUtils} from "@app/shared/services";
import { environment } from '@env/environment';

@Injectable({
  providedIn: 'root'
})
export class CfgMapService extends BasicService {
  defaultUrl

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'transmission', httpClient, helperService);
  }

  public getAllOwner(): any {
    // const url = this.serviceUrl + "/getAllOwner";
    const url = this.serviceUrl + '/getAllOwner';
    return this.postRequest(url);
  }



}
