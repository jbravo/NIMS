import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import { CommonUtils } from '@app/shared/services';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class OrganizationService extends BasicService {

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'sysOrganizations', httpClient, helperService);
  }
  /**
   * find org relation id
   */
  public findOrgRelationId(orgId: number) {
    const org = CommonUtils.nvl(orgId);
    const url = `${this.serviceUrl}/${org}/org-relation`;
    return this.getRequest(url);
  }

  /**
   * find org business by organizationId
   * find org business id
   */
  public findOrgBusiness(orgId: number) {
    const org = CommonUtils.nvl(orgId);
    const url = `${this.serviceUrl}/${org}/org-business`;
    return this.getRequest(url);
  }

  /**
   * find org document by organizationId
   */
  public findOrgDocument(orgId: number) {
    const org = CommonUtils.nvl(orgId);
    const url = `${this.serviceUrl}/${org}/org-document`;
    return this.getRequest(url);
  }

  /**
   * find org boundary by organizationId
   */
  public findOrgBoundary(orgId: number) {
    const org = CommonUtils.nvl(orgId);
    const url = `${this.serviceUrl}/${org}/org-boundary`;
    return this.getRequest(url);
  }

  /**
   * saveOrUpdateOtherInfo
   */
  public saveOrUpdateOtherInfo(item: any, organizationId: number): Observable<any> {
    const formdata = CommonUtils.convertFormFile(item);
    const url = `${this.serviceUrl}/${organizationId}/other-info`;
    return this.postRequest(url, formdata);
  }
}
