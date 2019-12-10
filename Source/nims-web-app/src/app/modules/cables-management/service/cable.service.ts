import { Injectable } from '@angular/core';
import { BasicService } from '@app/core/services/basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';
import { CommonUtils } from '@app/shared/services';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { TranslationService } from 'angular-l10n';

@Injectable({
  providedIn: 'root'
})
export class CableService extends BasicService {
  defaultUrl = `${this.serviceUrl}`;

  private _id: number;
  private _action: string;

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
  constructor(
    public httpClient: HttpClient,
    public helperService: HelperService,
    private tabLayoutService: TabLayoutService,
    private translation: TranslationService
  ) {
    super('nimsTransService', 'infraCable', httpClient, helperService);
  }

  public findBasicCable(item: any): Observable<any> {
    return this.postRequest(
      this.defaultUrl + '/search/basic',
      CommonUtils.convertData(item)
    );
  }

  public findAdvanceCable(item: any): Observable<any> {
    return this.postRequest(
      this.defaultUrl + '/search/advance',
      CommonUtils.convertData(item)
    );
  }

  public saveCable(item: any): Observable<any> {
    return this.postRequest(
      this.defaultUrl + '/save',
      CommonUtils.convertData(item)
    );
  }

  public findCableById(item: number) {
    return this.getRequest(this.defaultUrl + '/find/' + item);
  }
  public findListCableType() {
    return this.getRequest(this.serviceUrl + '/findlistcabletype/');
  }
  public findListODFFist(stationId) {
    return this.getRequest(this.serviceUrl + '/findlistodffirst/', {
      params: CommonUtils.buildParams(stationId)
    });
  }
  public findListODFEnd(stationId) {
    return this.getRequest(this.serviceUrl + '/findlistodfend/', {
      params: CommonUtils.buildParams(stationId)
    });
  }
  public getDataSearchAdvance(item: any): Observable<any> {
    return this.postRequest(
      this.defaultUrl + '/search/dataSearchAdvance',
      CommonUtils.convertData(item)
    );
  }
  public getCableIndex(item: any): Observable<any> {
    return this.getRequest(this.defaultUrl + '/cableIndex', {
      params: CommonUtils.buildParams(item)
    });
  }
  public isExitCode(item: any): Observable<any> {
    return this.postRequest(
      this.serviceUrl + '/isexitcode',
      CommonUtils.convertData(item)
    );
  }
  public checkWeldODFByCable(item: any): Observable<any> {
    return this.postRequest(
      this.serviceUrl + '/checkweldODFbycable',
      CommonUtils.convertData(item)
    );
  }
  public delete(item: any): Observable<any> {
    return this.postRequest(
      this.defaultUrl + '/delete',
      CommonUtils.convertData(item)
    );
  }
  public deleteMultipe(item: any): Observable<any> {
    return this.postRequest(
      this.defaultUrl + '/delete-multipe',
      CommonUtils.convertData(item)
    );
  }

  public downloadFileTemplate(item: any): Observable<any> {
    return this.postRequest(
      this.defaultUrl + '/downloadTeamplate',
      CommonUtils.convertData(item),
      {
        responseType: 'blob',
        observe: 'response'
      }
    );
  }

  public downloadFileTemplateEdit(item: any): Observable<any> {
    return this.postRequest(
      this.defaultUrl + '/downloadTeamplateEdit',
      CommonUtils.convertData(item),
      {
        responseType: 'blob',
        observe: 'response'
      }
    );
  }

  public export(item: any): Observable<any> {
    return this.postRequest(
      this.defaultUrl + '/excel',
      CommonUtils.convertData(item),
      { responseType: 'blob', observe: 'response' }
    );
  }

  public excelChose(item: any): Observable<any> {
    return this.postRequest(
      this.defaultUrl + '/excel-chose',
      CommonUtils.convertData(item),
      { responseType: 'blob', observe: 'response' }
    );
  }

  public importCableInStation(file: any): Observable<any> {
    const url = this.defaultUrl + '/importCableInStation';
    const fileData = new FormData();
    fileData.append('file', file);
    return this.postRequest(url, fileData, {
      reportProgress: true
      // responseType: 'text'
    });
  }

  public editCableInStation(file: any): Observable<any> {
    const url = this.defaultUrl + '/editCableInStation';
    const fileData = new FormData();
    fileData.append('file', file);
    return this.postRequest(url, fileData, {
      reportProgress: true
      // responseType: 'text'
    });
  }

  public downloadFile(item: any): Observable<any> {
    return this.getRequest(this.serviceUrl + '/downloadFile', {
      params: CommonUtils.buildParams({ path: item }),
      responseType: 'blob',
      observe: 'response'
    });
  }

  openCableListTab() {
    this.tabLayoutService.open({
      component: 'CableListComponent',
      header: 'cable.manage.label',
      breadcrumb: [{ label: this.translation.translate('cable.manage.label') }]
    });
  }

  formatFloatLeng(data) {

    if (data === undefined || data === null || data === 0) {
      data = '0.000';
    }
    {
      data = data + '';
      if (this.countInstances(data, '.') < 1) {
        data += '.000';
      } else {
        const d = data.substring(data.indexOf('.') + 1);
        for (let i = 0; i < 3 - d.length; i++) {
          data += '0';
        }
      }
    }
    return data;
  }

  countInstances(string, word) {
    return string.split(word).length - 1;
  }
}
