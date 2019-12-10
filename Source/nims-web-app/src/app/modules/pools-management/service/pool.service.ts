import { Injectable } from '@angular/core';
import { BasicService } from '@app/core/services/basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';
import { CommonUtils } from '@app/shared/services';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { TranslationService } from 'angular-l10n';

@Injectable({
  providedIn: 'root'
})
export class PoolService extends BasicService {
  defaultUrl = `${this.serviceUrl}`;

  private _id: number;
  private _action: string;
  private _poolCode: string;
  private _poolObj: any;

  get id() {
    return this._id;
  }

  set id(id: number) {
    this._id = id;
  }

  get action() {
    return this._action;
  }

  set action(action: string) {
    this._action = action;
  }

  get poolCode() {
    return this._poolCode;
  }

  set poolCode(poolCode: string) {
    this._poolCode = poolCode;
  }

  get poolObj() {
    return this._poolObj;
  }

  set poolObj(poolObj: any) {
    this._poolObj = poolObj;
  }


  constructor(public httpClient: HttpClient, public helperService: HelperService,
    private tabLayoutService: TabLayoutService, private translation: TranslationService, ) {
    super('nimsTransService', 'infraPool', httpClient, helperService);
  }

  public findBasicPool(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/search/basic', CommonUtils.convertData(item));
  }

  public findAdvancePool(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/search/advance', CommonUtils.convertData(item));
  }

  public savePool(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/save', CommonUtils.convertData(item));
  }

  public findById(item: number) {
    return this.getRequest(this.defaultUrl + '/find/' + item);
  }

  public findByPoolCode(item: any) {
    return this.getRequest(this.defaultUrl + '/find/poolCode/' + item);
  }

  public getNumberGenerate(item: number) {
    return this.getRequest(this.defaultUrl + '/numberAuto/?path=' + item);
  }

  public checkNumber(path: any, number: number) {
    return this.getRequest(this.defaultUrl + '/checkNumber/?path=' + path + '&&number=' + number);
  }

  public delete(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/delete', CommonUtils.convertData(item));
  }

  public deleteHangConfirm(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/deleteHangConfirm', CommonUtils.convertData(item));
  }

  public excelChoose(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/excel-choose',
      CommonUtils.convertData(item), { responseType: 'blob', observe: 'response' });
  }

  public excel(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/excel', CommonUtils.convertData(item), { responseType: 'blob', observe: 'response' });
  }

  // Lay danh sach tuyen cap vat qua be
  public getListLaneCodeHang(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/getLaneCodeHang', CommonUtils.convertData(item));
  }

  formatLongLat(data) {

    if (data === undefined || data === null || data === 0) {
      data = '0.00000';
    } else {
      data = data + '';
      if (this.countInstances(data, '.') < 1) {
        data += '.00000';
      } else {
        const d = data.substring(data.indexOf('.') + 1);
        for (let i = 0; i < 5 - d.length; i++) {
          data += '0';
        }
      }
    }
    return data;
  }

  countInstances(string, word) {
    return string.split(word).length - 1;
  }

  checkLongLat(str, limit) {
    str = str + '';
    const regex = '/[^-.0-9]/g';
    if (str.match(regex)) {
      return 1;
    }
    // const regex = new RegExp('^[0-9]+$');
    // if (regex.test(e)) {
    // }
    if ((this.countInstances(str, '.') !== 1 || this.countInstances(str, '-') > 1)
      || (this.countInstances(str, '-') === 1 && str[0] !== '-')
      || (this.countInstances(str, '-') === 1 && str[0] === '-' && str[1] === '.')) {
      return 1;
    }
    const listNb = str.split('.');
    if (listNb[0] === '') {
      return 1;
    }
    if (Math.abs(Number(listNb[0])) > limit) {
      return 2;
    }
    if (listNb[1].length < 5) {
      return 2;
    } else {
      return listNb[0] + '.' + listNb[1].substring(0, 5);
    }
  }

  formartNumberPool(data: string, addZeroEnd) {
    if (addZeroEnd) {
      if (data.length === 1) {
        data = data + '000';
      } else if (data.length === 2) {
        data = data + '00';
      } else if (data.length === 3) {
        data = data + '0';
      } else if (data.length > 4) {
        data = '';
      }
    } else {
      if (data.length === 1) {
        data = '000' + data;
      } else if (data.length === 2) {
        data = '00' + data;
      } else if (data.length === 3) {
        data = '0' + data;
      }
    }
    if (data === '0000') {
      data = '';
    }
    return data;
  }

  openPoolListTab() {
    this.tabLayoutService.open({
      component: 'PoolListComponent',
      header: 'pool.header',
      breadcrumb: [
        { label: this.translation.translate('pool.header') }
      ]
    });
  }

  public downloadFileTemplate(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/downloadTeamplate', CommonUtils.convertData(item), {
      responseType: 'blob',
      observe: 'response'
    });
  }

  public downloadFileTemplateEdit(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/downloadTeamplateEdit', CommonUtils.convertData(item), {
      responseType: 'blob',
      observe: 'response'
    });
  }

  public importPools(file: any): Observable<any> {
    const url = this.defaultUrl + '/importPool';
    const fileData = new FormData();
    fileData.append('file', file);
    return this.postRequest(url, fileData, {
      reportProgress: true
      // responseType: 'text'
    });
  }

  public importEditPools(file: any): Observable<any> {
    const url = this.defaultUrl + '/importEditPool';
    const fileData = new FormData();
    fileData.append('file', file);
    return this.postRequest(url, fileData, {
      reportProgress: true
      // responseType: 'text'
    });
  }
}
