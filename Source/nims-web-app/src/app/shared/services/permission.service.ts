import {Injectable} from '@angular/core';
import {BasicService} from '@app/core/services/basic.service';
import {HelperService} from '@app/shared/services/helper.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CommonUtils} from '@app/shared/services';

@Injectable({
  providedIn: 'root'
})
export class PermissionService extends BasicService {
  defaultUrl = `${this.serviceUrl}`;

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'transmission', httpClient, helperService);
    // super('political', 'transmission', httpClient, helperService);
  }

  public findCatLocation(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/location', CommonUtils.convertData(item));
  }

  public findCatDeptByPost(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/department', CommonUtils.convertData(item));
  }

  public filterDept(item: string) {
    return this.getRequest(this.defaultUrl + '/department/filter/' + item);
  }

  public filterLocation(item: string) {
    return this.getRequest(this.defaultUrl + '/location/filter/' + item);
  }

  public getInfoFromCatItem(item: any) {
    return this.getRequest(this.defaultUrl + '/item/find/' + item);
  }

  public getPoolTypeList() {
    return this.getRequest(this.defaultUrl + '/poolType/list');
  }

  public findCatLocationById(id: any) {
    return this.getRequest(this.defaultUrl + '/findCatLocationById/' + id);
  }

  public findDepartmentById(id: any) {
    return this.getRequest(this.defaultUrl + '/findDepartmentById/' + id);
  }

  public getCableTypeList() {
    return this.getRequest(this.defaultUrl + '/getAllCableType');
  }

  public findStation(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/stations', CommonUtils.convertData(item));
  }

  public getNumberOfCable(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/cables/index', CommonUtils.convertData(item));
  }

  public getTreeNodeDepartmentList(nodeId?: any): Observable<any> {
    const deptId = nodeId ? nodeId : '';
    const url = `${this.serviceUrl}/treeNodeCatDepartment/` + deptId;
    return this.getRequest(url);
  }

  public getTreeNodeLocationList(data?: any): Observable<any> {
    const url = `${this.serviceUrl}/treeNodeCatLocation`;
    return this.postRequest(url, CommonUtils.convertData(data));
  }

  public getCatOdfTypes() {
    return this.getRequest(this.defaultUrl + '/getCatOdfTypes');
  }
}
