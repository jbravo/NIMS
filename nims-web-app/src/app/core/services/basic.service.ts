import { CommonUtils } from '@app/shared/services/common-utils.service';
import { Injectable } from '@angular/core';
import { CONFIG } from '../app-config';
import { environment } from '@env/environment';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { HelperService } from '@app/shared/services/helper.service';

// @Injectable({
//   providedIn: 'root'
// })
export class BasicService {
  public serviceUrl: string;
  public module: string;
  public systemCode: string;
  credentials: any = {};

  /**
   * init service from system code and module
   * config value of app-config.ts
   * param systemCode
   * param module
   */
  constructor(
    systemCode: string,
    module: string,
    public httpClient: HttpClient,
    public helperService: HelperService,
    ) {

    this.systemCode = systemCode;
    this.module = module;
    const API_URL = environment.serverUrl[this.systemCode];
    const API_PATH = CONFIG.API_PATH[this.module];
    if (!API_URL) {
      console.error(`Missing config system service config in src/environments/environment.ts => system: ${this.systemCode}`);
      return;
    }
    if (!API_PATH) {
      console.error(`Missing config system service config in src/app/app-config.ts => module: ${this.module}`);
      return;
    }
    this.serviceUrl = API_URL + API_PATH;
    console.log(`Serivce created ${this.serviceUrl}`);
  }
  /**
   * set SystemCode
   * param systemCode
   */
  public setSystemCode(systemCode: string) {
    this.systemCode = systemCode;
    const API_URL = environment.serverUrl[this.systemCode];
    const API_PATH = CONFIG.API_PATH[this.module];
    if (!API_URL) {
      console.error(`Missing config system service config in src/environments/environment.ts => system: ${this.systemCode}`);
      return;
    }
    if (!API_PATH) {
      console.error(`Missing config system service config in src/app/app-config.ts => module: ${this.module}`);
      return;
    }
    this.serviceUrl = API_URL + API_PATH;
    console.log(`Serivce created ${this.serviceUrl}`);
  }


  public search(data: any, event: any): Observable<any> {
    if (!event) {
      this.credentials = Object.assign({}, data);
    }

    const searchData = CommonUtils.convertData(this.credentials);
    if (event) {
      searchData._search = event;
    }
    const buildParams = CommonUtils.buildParams(searchData);
    const url = `${this.serviceUrl}/search?`;
    return this.getRequest(url, {params: buildParams});
  }
  /*******************************/

  /**
   * request is success
   */
  public requestIsSuccess(data: any): boolean {
    let isSuccess = false;
    if (data && data.type === 'SUCCESS') {
      isSuccess = true;
    }
    return isSuccess;
  }

  /*****************************************
   * NhatNC - IIST
   */
  /**
   * handleError
   */
  public handleError(error: any) {
    const errorMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    return throwError(error);
  }
  /**
   * make get request
   */
  public getRequest(url: string, options?: any): Observable<any> {
    return this.httpClient.get(url, options)
      .pipe(
        tap( // Log the result or error
          // res => this.helperService.APP_TOAST_MESSAGE.next(res),
          res => {},
          error => {
            this.helperService.APP_TOAST_MESSAGE.next(error);
          }
        ),
        catchError(this.handleError)
      );
  }
  /**5
   * make post request
   */
  public postRequest(url: string, data?: any, options?: any): Observable<any> {
    return this.httpClient.post(url, data, options)
      .pipe(
        tap( // Log the result or error
          // res => this.helperService.APP_TOAST_MESSAGE.next(res),
          res => {},
          error => {
            this.helperService.APP_TOAST_MESSAGE.next(error);
          }
        ),
        catchError(this.handleError)
      );
  }
  /**
   * make get request
   */
  public deleteRequest(url: string): Observable<any> {
    return this.httpClient.delete(url)
      .pipe(
        tap( // Log the result or error
          res => this.helperService.APP_TOAST_MESSAGE.next(res),
          error => {
            this.helperService.APP_TOAST_MESSAGE.next(error);
          }
        ),
        catchError(this.handleError)
      );
  }
  /**
   * search using @RequestBody
   */
  public filter(data: any): Observable<any> {
    const url = `${this.serviceUrl}/search`;
    return this.postRequest(url, data);
  }
  /**
   * findOne
   * param id
   */
  public findOne(id: any): Observable<any> {
    const url = `${this.serviceUrl}/findById`;
    return this.getRequest(url, { params: CommonUtils.buildParams(id)});
  }

  /**
   * saveOrUpdate
   */
  public saveOrUpdate(item: any): Observable<any> {
    const url = `${this.serviceUrl}`;
    return this.postRequest(url, CommonUtils.convertData(item));
  }
  /**
   * deleteById
   * param id
   * Using Post
   */
  public deleteById(id: number): Observable<any> {
    const url = `${this.serviceUrl}/remove`;
    return this.postRequest(url, id);
  }

  /**
   * make post request
   */
  public putRequest(url: string, data?: any, options?: any): Observable<any> {
    return this.httpClient.put(url, data, options)
      .pipe(
        tap( // Log the result or error
          // res => this.helperService.APP_TOAST_MESSAGE.next(res),
          res => {},
          error => {
            this.helperService.APP_TOAST_MESSAGE.next(error);
          }
        ),
        catchError(this.handleError)
      );
  }
}
