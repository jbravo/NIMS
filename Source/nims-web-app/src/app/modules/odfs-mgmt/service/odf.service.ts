import { Injectable } from '@angular/core';
import { BasicService } from '@app/core/services/basic.service';
import { HttpClient } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';
import { CommonUtils } from '@app/shared/services';

@Injectable({
    providedIn: 'root'
})
export class OdfService extends BasicService {
    defaultUrl = `${this.serviceUrl}`;

    private _id: number;
    private _action: string;

    private _listId: number;
    private _listAction: string;

    private _saveOrEdit: string;

    // ================================
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

    // ================================
    get listId() {
        return this._listId;
    }

    set listId(listId: number) {
        this._listId = listId;
    }

    get listAction() {
        return this._listAction;
    }

    set listAction(listAction: string) {
        this._listAction = listAction;
    }

    // ================================
    get saveOrEdit() {
        return this._saveOrEdit;
    }

    set saveOrEdit(saveOrEdit: string) {
        this._saveOrEdit = saveOrEdit;
    }


    constructor(public httpClient: HttpClient, public helperService: HelperService) {
        super('nimsTransService', 'infraOdf', httpClient, helperService);
    }

    // public findAllOdf() {
    //     console.log(this.defaultUrl);
    //     return this.getRequest(this.defaultUrl + '/');
    // }

    // public findBasicOdf(item: any): Observable<any> {
    //     console.log(item);
    //     return this.postRequest(this.defaultUrl + '/search/basic', CommonUtils.convertData(item));
    // }

    public findAdvanceOdf(item: any): Observable<any> {
        console.log(item);
        return this.postRequest(this.defaultUrl + '/search/advance', CommonUtils.convertData(item));
    }

    public saveOdf(item: any): Observable<any> {
        return this.postRequest(this.defaultUrl + '/manage/modify', CommonUtils.convertData(item));
    }

    public findOdfById(item: number) {
        return this.getRequest(this.defaultUrl + '/search/getOdfById/' + item);
    }

    public getDataSearchAdvance(): Observable<any> {
        return this.getRequest(this.defaultUrl + '/search/dataSearchAdvance');
    }

    public getMaxOdfByStationId(item: number): Observable<any> {
        return this.getRequest(this.defaultUrl + '/search/getMaxOdfIndexByStationId/' + item);
    }

    public delete(item: any): Observable<any> {
        return this.postRequest(this.defaultUrl + '/delete', CommonUtils.convertData(item));
    }

    public getOdfsByStationId(item: any): Observable<any> {
        return this.postRequest(this.defaultUrl + '/search/getOdfsByStationId', CommonUtils.convertData(item));
    }

    public excelChoose(item: any): Observable<any> {
        return this.postRequest(this.defaultUrl + '/excel-choose',
            CommonUtils.convertData(item), { responseType: 'blob', observe: 'response' });
    }

    public export(item: any): Observable<any> {
        return this.postRequest(this.defaultUrl + '/excel', CommonUtils.convertData(item), { responseType: 'blob', observe: 'response' });
    }

    public getOdfLaneByOdfId(item: number): Observable<any> {
        return this.getRequest(this.defaultUrl + '/search/getOdfLaneById/' + item);
    }

    public downloadFileTemplate(item: any): Observable<any> {
        return this.postRequest(this.defaultUrl + '/downloadTeamplate', CommonUtils.convertData(item), {
            responseType: 'blob',
            observe: 'response'
        });
    }

    public downloadFileTemplateEdit(item: any): Observable<any> {
        return this.postRequest(this.defaultUrl + '/downloadTeamplateEdit', CommonUtils.convertData(item), {
            responseType: 'blob',
            observe: 'response'
        });
    }

    public importOdf(file: any): Observable<any> {
        const url = this.defaultUrl + `/importOdf`;
        const fileData = new FormData();
        fileData.append('file', file);
        return this.postRequest(url, fileData, {
            reportProgress: true
        });
    }

    public importCoupleToLineOdf(file: any): Observable<any> {
        const url = this.defaultUrl + `/importCoupleToLineOdf`;
        const fileData = new FormData();
        fileData.append('file', file);
        return this.postRequest(url, fileData, {
            reportProgress: true
        });
    }

    public importCoupleToCoupleOdf(file: any): Observable<any> {
        const url = this.defaultUrl + `/importCoupleToCoupleOdf`;
        const fileData = new FormData();
        fileData.append('file', file);
        return this.postRequest(url, fileData, {
            reportProgress: true
        });
    }

    public importUpdateOdf(file: any): Observable<any> {
        const url = this.defaultUrl + `/importUpdateOdf`;
        const fileData = new FormData();
        fileData.append('file', file);
        return this.postRequest(url, fileData, {
            reportProgress: true
        });
    }

    public importUpdateCoupleToLineOdf(file: any): Observable<any> {
        const url = this.defaultUrl + `/importUpdateCoupleToLineOdf`;
        const fileData = new FormData();
        fileData.append('file', file);
        return this.postRequest(url, fileData, {
            reportProgress: true
        });
    }

    public importUpdateCoupleToCoupleOdf(file: any): Observable<any> {
        const url = this.defaultUrl + `/importUpdateCoupleToCoupleOdf`;
        const fileData = new FormData();
        fileData.append('file', file);
        return this.postRequest(url, fileData, {
            reportProgress: true
        });
    }

    public downloadFile(item: any): Observable<any> {
        return this.getRequest(this.serviceUrl + '/downloadFile', {
            params: CommonUtils.buildParams({ path: item }),
            responseType: 'blob',
            observe: 'response'
        });
    }

    public searchOdfLane(item: any): Observable<any> {
        return this.postRequest(this.defaultUrl + '/search/OdfLane', CommonUtils.convertData(item));
    }

}
