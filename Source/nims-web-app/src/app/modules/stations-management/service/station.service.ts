import { Injectable } from '@angular/core';
import { BasicService } from '@app/core/services/basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';
import { CommonUtils } from '@app/shared/services';

@Injectable({
  providedIn: 'root'
})
export class StationService extends BasicService {
  defaultUrl = `${this.serviceUrl}`;

  private _id: number;
  private _action: string;

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

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'infraStation', httpClient, helperService);
  }

  // public findAllStation() {
  //   console.log(this.defaultUrl);
  //   return this.getRequest(this.defaultUrl + '/');
  // }

  public findBasicStation(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/search/basic', CommonUtils.convertData(item));
  }

  public findAdvanceStation(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/search/advance', CommonUtils.convertData(item));
  }

  public saveStation(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/save', CommonUtils.convertData(item));
  }

  public findStationById(item: number) {
    return this.getRequest(this.defaultUrl + '/find/' + item);
  }

  public delete(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/delete', CommonUtils.convertData(item));
  }

  public deleteMultipe(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/delete-multipe', CommonUtils.convertData(item));
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

  public export(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/excel', CommonUtils.convertData(item), { responseType: 'blob', observe: 'response' });
  }

  public excelChose(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/excel-chose', CommonUtils.convertData(item), { responseType: 'blob', observe: 'response' });
  }

  public importStation(file: any): Observable<any> {
    const url = this.defaultUrl + '/importStation';
    const fileData = new FormData();
    fileData.append('file', file);
    return this.postRequest(url, fileData, {
      reportProgress: true
      // responseType: 'text'
    });
  }

  public editStation(file: any): Observable<any> {
    const url = this.defaultUrl + '/editStation';
    const fileData = new FormData();
    fileData.append('file', file);
    return this.postRequest(url, fileData, {
      reportProgress: true
      // responseType: 'text'
    });
  }

  public downloadFile(item: any): Observable<any> {
    return this.getRequest(this.serviceUrl + '/downloadFile', {
      params: CommonUtils.buildParams({ path: item }),
      responseType: 'blob',
      observe: 'response'
    });
  }

  public attachFile(file: any, stCode, stId, type): Observable<any> {
    const url = this.defaultUrl + '/attachFile';
    const fileData = new FormData();
    fileData.append('file', file);
    fileData.append('stationCode', stCode);
    fileData.append('stId', stId);
    fileData.append('type', type);
    return this.postRequest(url, fileData, {
      reportProgress: true
    });
  }


  public saveDocument(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/saveDoc', CommonUtils.convertData(item));
  }


}
