import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HelperService {
  public ORGANIZATION = new BehaviorSubject<any>([]);
  public APP_TOAST_MESSAGE = new BehaviorSubject<any>([]);
  public APP_SHOW_PROCESSING = new BehaviorSubject<any>([]);
  constructor() { }
  /**
   * afterSaveOrganization
   * param data
   */
  afterSaveOrganization(data) {
    this.ORGANIZATION.next(data);
  }
  /**
   * createMessage
   * param data
   */
  processReturnMessage(data) {
    this.APP_TOAST_MESSAGE.next(data);
  }
      /**
  * processing
  * param data
  */
 isProcessing(isProcessing: boolean) {
   this.APP_SHOW_PROCESSING.next(isProcessing);
 }
}
