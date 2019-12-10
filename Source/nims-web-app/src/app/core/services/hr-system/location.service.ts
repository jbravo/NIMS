import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';
import { CommonUtils } from '@app/shared/services';

@Injectable({
  providedIn: 'root'
})
export class LocationService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('sys', 'location', httpClient, helperService);
  }

  /**
   * Lay danh sach tinh/thanh pho
   */
  public getListProvinces(): Observable<any> {
    const nationId = CommonUtils.getNationId();
    const url = `${this.serviceUrl}/provinces/${nationId}`;
    return this.getRequest(url).pipe();
  }

  /**
   * Lay danh sach quan/huyen
   */
  public getListDistricts(provinceId): Observable<any> {
    const url = `${this.serviceUrl}/districts/${provinceId}`;
    return this.getRequest(url).pipe();
  }

  /**
   * Lay danh sach phuong/xa
   */
  public getListWards(districtId): Observable<any> {
    const url = `${this.serviceUrl}/wards/${districtId}`;
    return this.getRequest(url).pipe();
  }
}
