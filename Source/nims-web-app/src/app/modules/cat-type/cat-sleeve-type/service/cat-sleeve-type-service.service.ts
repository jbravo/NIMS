import { Injectable } from '@angular/core';
import { BasicService } from '@app/core/services/basic.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HelperService } from '@app/shared/services/helper.service';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/internal/operators/tap';
import { catchError } from 'rxjs/operators';
import { CommonUtils } from '@app/shared/services';

@Injectable({
  providedIn: 'root'
})
export class CatSleeveTypeServiceService extends BasicService {

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

  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('nimsCommonService', 'sleeveType', httpClient, helperService);
  }

  public findAll(): any {
    const url = this.serviceUrl + "/getAll";
    return this.getRequest(url);
  }

  public findById(id: any): any {
    const url = this.serviceUrl + "/" + id;
    return this.getRequest(url);
  }
  public getTotalRecord(filters?): Observable<any> {
    const url = this.serviceUrl + "/count";
    return this.httpClient.post(url, {
      filters
    })
      .pipe(
        tap( // Log the result or error
          res => {
            this.helperService.APP_TOAST_MESSAGE.next(res);
            console.log("RES ", res);
          },
          // res => {},
          error => {
            this.helperService.APP_TOAST_MESSAGE.next(error);
          }
        ),
        catchError(this.handleError)
      );
  }

  public searchSleeve(first, pageSize, filters?, orders?): Observable<any> {
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
            console.log("RES ", res);
          },
          // res => {},
          error => {
            this.helperService.APP_TOAST_MESSAGE.next(error);
          }
        ),
        catchError(this.handleError)
      );
  }
  public saveOrUpdateSleeve(item): Observable<any> {
    const url = this.serviceUrl + "/saveOrUpdate";
    console.log(url);
    return this.httpClient.post(url, CommonUtils.convertData(item))
      .pipe(
        tap( // Log the result or error
          res => {
            this.helperService.APP_TOAST_MESSAGE.next(res);
            console.log("RES ", res);
          },
          // res => {},
          error => {
            this.helperService.APP_TOAST_MESSAGE.next(error);
            console.log("RES ", error);
          }
        ),
        catchError(this.handleError)
      );
  }
}
