import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})

export class DocumentTypeService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('org', 'documentType', httpClient, helperService);
  }
  public getFindAll(): Observable<any> {
    const listDocumentTypeUrl = `${this.serviceUrl}/findAll`;
    return this.getRequest(listDocumentTypeUrl);
  }
}
