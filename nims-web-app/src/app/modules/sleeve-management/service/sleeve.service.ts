import {Injectable} from '@angular/core';
import {BasicService} from '@app/core/services/basic.service';
import {HttpClient} from '@angular/common/http';
import {HelperService} from '@app/shared/services/helper.service';
import {Observable} from 'rxjs';
import {CommonUtils} from '@app/shared/services';

@Injectable({
  providedIn: 'root'
})
export class SleeveService extends BasicService {

  defaultUrl = `${this.serviceUrl}`;

  private _id: number;
  private _action: string;
  private _item: any;
  private _listData: any;
  private _holderId: any;
  private _activeWeld: boolean = false;


  get activeWeld(): any {
    return this._activeWeld;
  }

  set activeWeld(activeWeld: any) {
    this._activeWeld = activeWeld;
  }

// create by vanba
  private _pillarCode: string = null;

  set pillarCode(pillarCode) {
    this._pillarCode = pillarCode;
  }

  get pillarCode() {
    return this._pillarCode;
  }

  //
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

  set item(item) {
    this._item = item;
  }

  get item() {
    return this._item;
  }

  set listData(_listData) {
    this._listData = _listData;
  }

  get listData() {
    return this._listData;
  }

  get holderId() {
    return this._holderId;
  }

  set holderId(id: number) {
    this._holderId = id;
  }

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'infraSleeve', httpClient, helperService);
  }

  public findBasicSleeve(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/search/basic', CommonUtils.convertData(item));
  }

  public listSleeve(): Observable<any> {
    return this.postRequest(this.defaultUrl + '/list/sleeve');
  }

  public findAdvanceSleeve(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/search/advance', CommonUtils.convertData(item));
  }

  public saveSleeve(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/save', CommonUtils.convertData(item));
  }

  public findSleeveById(item: number) {
    return this.getRequest(this.defaultUrl + '/find/' + item);
  }

  public findViewSleeveById(item: number) {
    return this.getRequest(this.defaultUrl + '/find/view/sleeve/' + item);
  }

  public getSleeveTypeCodeList(): Observable<any> {
    return this.postRequest(this.defaultUrl + '/list/sleeve-type');
  }

  public getDataSearchAdvance(): Observable<any> {
    return this.getRequest(this.defaultUrl + '/dataAdvance');
  }

  public delete(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/delete', CommonUtils.convertData(item));
  }

  public deleteMultipe(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/delete-multipe', CommonUtils.convertData(item));
  }

  public getVendorNameList(): Observable<any> {
    return this.postRequest(this.defaultUrl + '/list/vendor');
  }

  public getListLaneByCode(item?: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/list/laneCode');
  }

  public export(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/excel', CommonUtils.convertData(item), {responseType: 'blob', observe: 'response'});
  }

  public excelChose(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/excel-chose', CommonUtils.convertData(item), {responseType: 'blob', observe: 'response'});
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

  public downloadFile(item: any): Observable<any> {
    return this.getRequest(this.serviceUrl + '/downloadFile', {
      params: CommonUtils.buildParams({path: item}),
      responseType: 'blob',
      observe: 'response'
    });
  }

  public importSleeve(file: any): Observable<any> {
    const url = this.defaultUrl + '/importSleeve';
    const fileData = new FormData();
    fileData.append('file', file);
    return this.postRequest(url, fileData, {
      reportProgress: true
      // responseType: 'text'
    });
  }

  public editSleeve(file: any): Observable<any> {
    const url = this.defaultUrl + '/editSleeve';
    const fileData = new FormData();
    fileData.append('file', file);
    return this.postRequest(url, fileData, {
      reportProgress: true
      // responseType: 'text'
    });
  }
}
