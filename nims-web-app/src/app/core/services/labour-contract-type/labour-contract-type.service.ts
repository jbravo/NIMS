import { Injectable } from '@angular/core';
import { BasicService } from '../basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class LabourContractTypeService extends BasicService {
  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('emp', 'labourContractType', httpClient, helperService);
  }

}
