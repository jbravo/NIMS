import {CommonUtils} from '@app/shared/services';
import {Injectable} from '@angular/core';
import {BasicService} from '../basic.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {HelperService} from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class DocumentAttachmentService extends BasicService {
  defaultUrl = `${this.serviceUrl}`;

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsTransService', 'document-attachment', httpClient, helperService);
  }

  public findDocumentAttByDocTypeFuncIDFuncType(documentType: any, functionId: any, functionType: any): Observable<any> {
    const url = `${this.serviceUrl}/findDocumentAttByDocTypeFuncIDFuncType`;
    var params = {documentType: documentType, functionId: functionId, functionType: functionType};
    return this.getRequest(url, {params: CommonUtils.buildParams(params)});
  }

  public findAllDocumentAttByDocTypeFuncIDFuncType(documentType: any, functionId: any, functionType: any): Observable<any> {
    const url = `${this.serviceUrl}/findAllDocumentAttByDocTypeFuncIDFuncType`;
    var params = {documentType: documentType, functionId: functionId, functionType: functionType};
    return this.getRequest(url, {params: CommonUtils.buildParams(params)});
  }

  public saveDocumentAttachment(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/save', CommonUtils.convertData(item));
  }
  public saveMultiDocumentAttachment(item: any): Observable<any> {
    return this.postRequest(this.defaultUrl + '/saveMultiple', CommonUtils.convertData(item));
  }
  public deleteDocumentById(item:any): Observable<any> {
    const url = `${this.serviceUrl}/deletebyid`;
    
    return this.getRequest(url, {params: CommonUtils.buildParams(item)});
  }
}
