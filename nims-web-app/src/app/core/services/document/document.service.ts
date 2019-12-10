import { CommonUtils } from '@app/shared/services';
import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class DocumentService extends BasicService {
  // defaultUrl = `${this.serviceUrl}`;
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'document', httpClient, helperService);
  }

  public autoCompleteSearch(item: any): Observable<any> {
    const url = `${this.serviceUrl}/autoCompleteSearch`;
    return this.getRequest(url, { params: CommonUtils.buildParams(item) });
  }

  public autoSearchForTransferActivity(item: any): Observable<any> {
    const url = `${this.serviceUrl}/autoSearchForTransferActivity`;
    return this.getRequest(url, { params: CommonUtils.buildParams(item) });
  }

  public autoSearchByOrganization(item: any): Observable<any> {
    const url = `${this.serviceUrl}/autoSearchByOrganization`;
    return this.getRequest(url, { params: CommonUtils.buildParams(item) });
  }
  public getDocument(params: any): Observable<any> {
    const url = `${this.serviceUrl}/getDocument`;
    return this.getRequest(url, { params: CommonUtils.buildParams(params) });
  }
  public searchForm(params: any): Observable<any> {
    const url = `${this.serviceUrl}/search`;
    return this.getRequest(url, { params: CommonUtils.buildParams(params) });
  }

  public save(item: any): Observable<any> {
    const url = `${this.serviceUrl}/saveDocumentWareHouse`;
    return this.postRequest(url, CommonUtils.convertData(item));
  }
  public saveFile(item: any): Observable<any> {
    const url = `${this.serviceUrl}/saveFile`;
    return this.postRequest(url, CommonUtils.convertData(item));
  }
  public upload(item: any): Observable<any> {
    const url = `${this.serviceUrl}/saveDocumentWareHouse`;
    return this.postRequest(url, CommonUtils.convertData(item));
  }

  public downloadFileAtt(item: any): Observable<any> {
    return this.getRequest(this.serviceUrl + '/downloadFileAtt',
      { params: CommonUtils.buildParams(item), responseType: 'blob', observe: 'response' });
  }
  public findByDocumentId(id: any): Observable<any> {
    const url = `${this.serviceUrl}/findById`;
    return this.getRequest(url, { params: CommonUtils.buildParams(id) });
  }
  public findByDocumentAttachMentId(obj: any): Observable<any> {
    const url = `${this.serviceUrl}/findByDocumentAttachMentId`;
    return this.getRequest(url, { params: CommonUtils.buildParams(obj) });
  }

  public exportExcel(item: any): Observable<any> {
    return this.getRequest(this.serviceUrl + '/exportExcel', { params: CommonUtils.buildParams(item), responseType: 'blob', observe: 'response' })
  }
  public doloadFile(documentNumber: any): Observable<any> {
    const url = `${this.serviceUrl}/doloadFile`;
    return this.getRequest(url, { params: CommonUtils.buildParams(documentNumber) });
  }
  public deleteFile(item: any): Observable<any> {
    const url = `${this.serviceUrl}/deleteFile`;
    return this.postRequest(url, CommonUtils.convertData(item));
  }
  public deleteById(id: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/remove', CommonUtils.convertData(id));
  }

  public checkDocument(id: any): Observable<any> {
    return this.postRequest(this.serviceUrl + '/checkDocument', CommonUtils.convertData(id));
  }

  public isExitDocument(documentNumber: any): Observable<any> {
    const url = `${this.serviceUrl}/isexitducumentbydocumentnumber`;
    return this.getRequest(url, { params: CommonUtils.buildParams(documentNumber) });
  }

  public getListCategoryType(item: any): Observable<any> {
    const url = `${this.serviceUrl}/getListCategoryType`;
    return this.getRequest(url, { params: CommonUtils.buildParams(item) });
  }


}
