import {Injectable} from '@angular/core';
import {BasicService} from '@app/core/services/basic.service';
import {HttpClient} from '@angular/common/http';
import {HelperService} from '@app/shared/services/helper.service';
import {Observable} from 'rxjs';
import {CommonUtils} from '@app/shared/services';

@Injectable({
  providedIn: 'root'
})
export class PillarService extends BasicService {
  private _id: number;
  private _action: string;
  private _pillarCode: string;
  private _sleeveList: boolean = false;

  set SleeveList(sleeveList: boolean){
    this._sleeveList = sleeveList;
  }
  get SleeveList(){
    return this._sleeveList;
  }
  get id() {
    return this._id;
  }

  private _selectedDeptData: any;
  set selectedDeptData(selectedDeptData) {
    this._selectedDeptData = selectedDeptData;
  }

  get selectedDeptData() {
    return this._selectedDeptData;
  }

  private _item: any;

  set id(id: number) {
    this._id = id;
  }

  set item(item) {
    this._item = item;
  }

  get item() {
    return this._item;
  }

  get action() {
    return this._action;
  }

  set action(action: string) {
    this._action = action;
  }

  get pillarCode() {
    return this._pillarCode;
  }

  set pillarCode(pillarCode: string) {
    this._pillarCode = pillarCode;
  }

  defaultUrl = `${this.serviceUrl}`;

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'pillar', httpClient, helperService);
  }

  // public findBasicPillar(item: any): Observable<any> {
  //   return this.postRequest(this.defaultUrl + '/search/basic', CommonUtils.convertData(item));
  // }

  public findAdvancePillar(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/search/advance', CommonUtils.convertData(item));
  }

  public savePillar(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/save', CommonUtils.convertData(item));
  }

  public findPillarById(item: number) {
    return this.getRequest(this.defaultUrl + '/find/' + item);
  }

  public getPillarTypeCodeList(): Observable<any> {
    return this.postRequest(this.defaultUrl + '/list/pillar');
  }

  // public getOwnerName(): Observable<any> {
  //   return this.postRequest(this.defaultUrl + '/list/owner');
  // }

  public getLaneCodeList(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/laneCode/list', CommonUtils.convertData(item));
  }

  public getPillarIndex(item: String): Observable<any> {
    return this.getRequest(this.defaultUrl + '/pillarIndex/' + item);
  }

  public delete(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/delete', CommonUtils.convertData(item));
  }

  public isExitCode(item: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/isexitcode', CommonUtils.convertData(item));
  }

  public getAutocompleteLaneCodeList(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/list/laneCode', CommonUtils.convertData(item));
  }

  public getPillarList(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/search/pillarList', CommonUtils.convertData(item));
  }

// Lay danh sach tuyen cap vat qua cot
  public getListLaneCodeHangPillar(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/get/laneCode/pillar', CommonUtils.convertData(item));
  }

  // public getLaneSleeveList(item: any): Observable<any> {
  //   return this.postRequest(this.defaultUrl + '/list/sleeve/laneCode', CommonUtils.convertData(item));
  // }

  public export(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/excel', CommonUtils.convertData(item), {responseType: 'blob', observe: 'response'});
  }

  public deleteHangConfirm(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/deleteHangConfirm', CommonUtils.convertData(item));
  }

  public excelChose(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/excel-chose', CommonUtils.convertData(item), {responseType: 'blob', observe: 'response'});
  }

// ----------------KienNT----------------
  public downloadFileTemplate(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/downloadTemplate', CommonUtils.convertData(item), {
      responseType: 'blob',
      observe: 'response'
    });
  }

  public downloadFileTemplateEdit(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/downloadTemplateEdit', CommonUtils.convertData(item), {
      responseType: 'blob',
      observe: 'response'
    });
  }

  public downloadFile(item: any): Observable<any> {
    return this.getRequest(this.serviceUrl + '/downloadFile', {
      params: CommonUtils.buildParams({path: item}),
      responseType: 'blob',
      observe: 'response'
    });
  }

  public importPillar(file: any): Observable<any> {
    const url = this.defaultUrl + '/importPillar';
    const fileData = new FormData();
    fileData.append('file', file);
    return this.postRequest(url, fileData, {
      reportProgress: true
      // responseType: 'text'
    });
  }

  public editPillar(file: any): Observable<any> {
    const url = this.defaultUrl + '/editPillar';
    const fileData = new FormData();
    fileData.append('file', file);
    return this.postRequest(url, fileData, {
      reportProgress: true
      // responseType: 'text'
    });
  }

  // ----------------KienNT----------------
}
