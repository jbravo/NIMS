import {Injectable} from '@angular/core';

/**
 * Created by VTN-PTPM-NV68 on 8/22/2019.
 */

@Injectable({
  providedIn: 'root'
})
export class CommonParam {
  isShowHide: boolean = false;
  observers: CommonParamObserver[];

  onChangeShowInfo(isShow: boolean) {
    this.isShowHide = isShow;
  }

  attach(observer: CommonParamObserver) {
    if (!this.observers) {
      this.observers = [];
    }
    this.observers.push(observer);
  }

  onChange(isShow: boolean) {
    this.isShowHide = isShow;
    for (let i = 0; i < this.observers.length; i++) {
      let observer: CommonParamObserver = this.observers[i];
      observer.onChange(isShow);
    }
  }

  showProperties(type: string, data: any) {
    for (let i = 0; i < this.observers.length; i++) {
      let observer: CommonParamObserver = this.observers[i];
      observer.showProperties(type, data);
    }
  }

  // Them moi tram/cot/cap/tuyen cap
  addItem(evt: string) {
    for (let i = 0; i < this.observers.length; i++) {
      let observer: CommonParamObserver = this.observers[i];
      observer.addItem(evt);
    }
  }

  locateFunction(longitude, latitude) {
    for (let i = 0; i < this.observers.length; i++) {
      let observer: CommonParamObserver = this.observers[i];
      observer.locateFunction(longitude, latitude);
    }
  }

  closePopupLeft() {
    for (let i = 0; i < this.observers.length; i++) {
      let observer: CommonParamObserver = this.observers[i];
      observer.closePopupLeft();
    }
  }

  viewLayer(listObject: any[]) {
    for (let i = 0; i < this.observers.length; i++) {
      let observer: CommonParamObserver = this.observers[i];
      observer.viewLayer(listObject);
    }
  }

  hideContextMenu(isShow: boolean) {
    for (let i = 0; i < this.observers.length; i++) {
      let observer: CommonParamObserver = this.observers[i];
      observer.hideContextMenu(isShow);
    }
  }

  print() {
    console.log(this.isShowHide);
  }


  focusItem(data:any){
    for (let i = 0; i < this.observers.length; i++) {
      let observer: CommonParamObserver = this.observers[i];
      observer.focusItem(data);
    }
  }
}

export interface CommonParamObserver {
  onChange(isShow: boolean);

  addItem(evt: string);

  showProperties(type: string, data: any);

  locateFunction(longitude, latitude);

  closePopupLeft();

  viewLayer(listObject: any[]);
  focusItem(data:any);

  hideContextMenu(isShow: boolean);


}
