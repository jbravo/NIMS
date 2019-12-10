import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {CommonUtils} from '@app/shared/services';
import {HttpClient} from '@angular/common/http';
import {HelperService} from '@app/shared/services/helper.service';
import {BasicService} from '@app/core/services/basic.service';

@Injectable({
  providedIn: 'root'
})
export class WeldSleeveService extends BasicService {
  private _sleeveId: number;
  private _action: string;
  private _sourceCableId: number;
  private _sourceCableNo: number;
  private _destCableId: number;
  private _destCableNo: number;
  private _data;
  private _message: string;
  //index for open tab
  private _index: number;


  get index(): number {
    return this._index;
  }

  set index(index: number) {
    this._index = index;
  }

  get message() {
    return this._message;
  }

  set message(message: string) {
    this._message = message;
  }

  get data() {
    return this._data;
  }

  set data(data: any) {
    this._data = data;
  }

  get sourceCableId() {
    return this._sourceCableId;
  }

  set sourceCableId(sourceCableId: number) {
    this._sourceCableId = sourceCableId;
  }

  get sourceCableNo() {
    return this._sourceCableNo;
  }

  set sourceCableNo(sourceCableNo: number) {
    this._sourceCableNo = sourceCableNo;
  }

  get destCableId() {
    return this._destCableId;
  }

  set destCableId(destCableId: number) {
    this._destCableId = destCableId;
  }

  get destCableNo() {
    return this._destCableNo;
  }

  set destCableNo(destCableNo: number) {
    this._destCableNo = destCableNo;
  }

  get sleeveId() {
    return this._sleeveId;
  }

  private _id: number;

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

  set sleeveId(id: number) {
    this._sleeveId = id;
  }

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'weldSleeve', httpClient, helperService);
  }

  public findBasicWeldSleeve(item: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/search/basic', CommonUtils.convertData(item));
  }

  public checkSleeveIdLaneId(itemCount: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/checkSleeveIdLaneId', CommonUtils.convertData(itemCount));
  }

  // public delete(item: any): Observable<any> {
  //   return this.postRequest(this.serviceUrl + '/delete', CommonUtils.convertData(item));
  // }
  //Tim ma cap den va cap di theo diem dau diem cuoi
  public getCableCode(holderId: number): Observable<any> {
    return this.getRequest(this.serviceUrl + '/findCable/' + holderId);
  }

  //Fill ma mang xong
  public getSleeveById(id: number): Observable<any> {
    return this.getRequest(this.serviceUrl + '/findSleeve/' + id);
  }

  //Save mang moi han mang xong,
  public saveWeldSleeve(item: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/save', CommonUtils.convertData(item));
  }

  //Get thong tin soi cua cap den, cap di
  public getYarnCable(cableTypeId: number, sleeveId: number, cableId: number) {
    return this.getRequest(this.serviceUrl + '/get-list-cable/' + cableTypeId + '/' + sleeveId + '/' + cableId);
  }

  public deleteTest(id: any): Observable<any> {
    return this.getRequest(this.serviceUrl + '/deleteWeld/' + id, CommonUtils.convertData(id));
  }

  public getAllWeldSleeve() {
    return this.getRequest(this.serviceUrl + '/list');
  }

  public delete(item: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/delete', CommonUtils.convertData(item));
  }

  public deleteMultipe(item: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/delete', CommonUtils.convertData(item));
  }

  public deleteByFiveField(item: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/deleteByFiveField', item);
  }

  //Get thong tin moi han
  public getWeldSleeveDetail(item: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/detail', CommonUtils.convertData(item));
  }

  //Lay cable theo Id
  public getCableById(cableId: number) {
    return this.getRequest(this.serviceUrl + '/getCableById/' + cableId);
  }

  //Cap nhat
  public updateWeldSleeve(item: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/update', CommonUtils.convertData(item));
  }

  public export(item: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/excel', CommonUtils.convertData(item), {responseType: 'blob', observe: 'response'});
  }

  public excelChose(item: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/excel-chose', CommonUtils.convertData(item), {responseType: 'blob', observe: 'response'});
  }
}
