import { Injectable } from '@angular/core';
import { BasicService } from '@app/core/services/basic.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class CatTerantSerService extends BasicService {

    constructor(public httpClient: HttpClient, public helperService: HelperService) {
        super('nimsCommonService', 'catTenant', httpClient, helperService);
    }
    public findById(id: any): any {
        const url = this.serviceUrl + "/" + id;
        return this.getRequest(url);
    }

    public searchTerant(first, pageSize, filters?, orders?): Observable<any> {
        const url = this.serviceUrl + "/search";
        return this.httpClient.post(url, {
            filters,
            orders
        }, {
                params: {
                    first: first,
                    pageSize: pageSize,
                }
            })
            .pipe(
                tap( // Log the result or error
                    res => {
                        this.helperService.APP_TOAST_MESSAGE.next(res);
                        //console.log("RES ", res);
                    },
                    // res => {},
                    error => {
                        this.helperService.APP_TOAST_MESSAGE.next(error);
                    }
                ),
                catchError(this.handleError)
            );
    }
}
