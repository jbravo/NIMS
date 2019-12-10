import {Injectable} from "@angular/core";
import {BasicService} from "@app/core/services/basic.service";
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {HelperService} from '@app/shared/services/helper.service';
import {CommonUtils} from "@app/shared/services";
@Injectable({
  providedIn: 'root'
})
export class ElasticSearch extends BasicService {
  defaultUrl = "http://10.240.202.40:8200/test_synch/_search?terminate_after=5";

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsGeoService', 'infraCables', httpClient, helperService);
  }

  public searchObject(item: any) {
    return this.postRequest(this.defaultUrl , item);
  }
}
