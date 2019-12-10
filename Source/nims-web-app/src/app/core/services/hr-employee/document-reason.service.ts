import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DocumentReasonService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('emp', 'documentReason', httpClient, helperService);
  }
  public getLstDocumentReasonByDocumentType(documentTypeId: number): Observable<any> {
    const listDocumentReasonUrl = `${this.serviceUrl}/by-document-type/${documentTypeId}`;
    return this.getRequest(listDocumentReasonUrl);
  }
}
