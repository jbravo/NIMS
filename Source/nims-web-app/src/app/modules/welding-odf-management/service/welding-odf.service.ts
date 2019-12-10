import { Injectable } from '@angular/core';
import { BasicService } from '@app/core/services/basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';
import { CommonUtils } from '@app/shared/services';
import { Translation, TranslationService } from 'angular-l10n';

@Injectable({
  providedIn: 'root'
})
export class WeldingOdfService extends BasicService {
  defaultUrl = `${this.serviceUrl}`;
  private _id: number;
  private _coupler: number;
  private _item: any;
  private _action: string;
  private _odfList: any = [];
  _listData: any[];

  get id() {
    return this._id;
  }

  set id(id: number) {
    this._id = id;
  }

  get coupler() {
    return this._coupler;
  }

  set coupler(coupler: number) {
    this._coupler = coupler;
  }

  get item() {
    return this._item;
  }

  set item(item: any) {
    this._item = item;
  }

  get action() {
    return this._action;
  }

  set action(action: string) {
    this._action = action;
  }

  get odfList() {
    return this._odfList;
  }

  set odfList(odfList: any) {
    this._odfList = odfList;
  }

  get listData() {
    return this._listData;
  }

  set listData(listData: any) {
    this._listData = listData;
  }

  constructor(public httpClient: HttpClient, public helperService: HelperService, private translation: TranslationService) {
    super('nimsTransService', 'weldingOdf', httpClient, helperService);
  }

  public getAllWeldingOdfs(id: number) {
    return this.postRequest(this.defaultUrl + '/listBy' + id);
  }

  public getCableCodes(id: number) {
    return this.postRequest(this.defaultUrl + '/cableCodesBy' + id);
  }

  public getLines(id: number) {
    return this.postRequest(this.defaultUrl + '/linesBy' + id);
  }

  public getOdfCode(id: number) {
    return this.postRequest(this.defaultUrl + '/' + id);
  }

  public getDestOdfCodes(id: any) {
    return this.postRequest(this.defaultUrl + '/destOdfCodesBy' + id);
  }

  public getCouplers(id: any) {
    return this.postRequest(this.defaultUrl + '/couplersBy' + id);
  }

  public getSourceCouplers(id: any) {
    return this.postRequest(this.defaultUrl + '/jointCouplersBy' + id);
  }

  public getDestCouplers(id: any) {
    return this.postRequest(this.defaultUrl + '/jointDestCouplersBy' + id);
  }

  public saveUpdateWeldingOdf(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/saveEdit', CommonUtils.convertData(item));
  }

  public saveCreateWeldingOdf(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/saveNew', CommonUtils.convertData(item));
  }

  public delete(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/delete', CommonUtils.convertData(item));
  }

  public exportExcel(item: Map<String, Object>): Observable<any> {
    const convertMap = {};
    item.forEach((val: Object, key: string) => {
      convertMap[key] = val;
    });
    return this.postRequest(this.defaultUrl + '/exportExcel', convertMap, { responseType: 'blob', observe: 'response' });
  }
}
