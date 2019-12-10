import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BasicService } from '@app/core/services/basic.service';
import { environment } from '@env/environment';
import { Observable } from 'rxjs';
import { CONFIG } from '@app/core';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class WfMenuMappingService extends BasicService {
  private API_URL: string = environment.serverUrl.political;
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'wfMenuMapping', httpClient, helperService);
  }
  public findNodes(): Observable<any> {
    const url = `${this.API_URL + CONFIG.API_PATH.wfMenuMapping}/find-nodes/`;
    return this.httpClient.get(url).pipe();
  }
}
