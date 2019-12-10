import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';
import { CommonUtils } from '@app/shared/services';

@Injectable({
  providedIn: 'root'
})
export class TreeNodesService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'treeNodes', httpClient, helperService);
  }

  public getTreeNodeOrganization(nodeOrganizationId: any): Observable<any> {
    const url = `${this.serviceUrl}/treeNodeOrganization/`;
    return this.getRequest(url, { params: nodeOrganizationId });
  }

  public getTreeNodeCriteria(): Observable<any> {
    const url = `${this.serviceUrl}/treeNodeCriteria`;
    return this.getRequest(url);
  }

  public getTreeNodeOrgController(): Observable<any> {
    const url = `${this.serviceUrl}/treeNodeOrgController`;
    return this.getRequest(url);
  }
}
