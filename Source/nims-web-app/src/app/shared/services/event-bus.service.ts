import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { filter } from 'rxjs/operators';

@Injectable()
export class EventBusService {
  private event = new Subject<any>();
  private coordinates = new BehaviorSubject<any>({});
  coordinatesChange = this.coordinates.asObservable();
  private deptDataObj = new BehaviorSubject<any>({});
  deptDataObjChange = this.deptDataObj.asObservable();
  private pillarDataObj = new BehaviorSubject<any>({});
  pillarDataObjChange = this.pillarDataObj.asObservable();
  private poolDataObj = new BehaviorSubject<any>({});
  poolDataObjChange = this.poolDataObj.asObservable();
  private reloadPool = new BehaviorSubject<any>({});
  reloadPoolChange = this.reloadPool.asObservable();
  private reloadCable = new BehaviorSubject<any>({});
  reloadCableChange = this.reloadCable.asObservable();
  private reloadSleeve = new BehaviorSubject<any>({});
  reloadSleeveChange = this.reloadSleeve.asObservable();
  private reloadPillar = new BehaviorSubject<any>({});
  reloadPillarChange = this.reloadPillar.asObservable();
  private reloadStation = new BehaviorSubject<any>({});
  reloadStationChange = this.reloadStation.asObservable();
  private reloadWeldingOdfList = new BehaviorSubject<any>({});
  reloadWeldingOdfsChange = this.reloadWeldingOdfList.asObservable();
  private reloadWeldSleeve = new BehaviorSubject<any>({});
  reloadWeldSleeveChange = this.reloadWeldSleeve.asObservable();
  private reloadOdf = new BehaviorSubject<any>({});
  reloadOdfChange = this.reloadOdf.asObservable();
  private data = new BehaviorSubject<any>({});
  dataChange = this.data.asObservable();
  private odfStation = new BehaviorSubject<any>({});
  odfStationChange = this.odfStation.asObservable();

  private weldingOdfCreate = new BehaviorSubject<any>({});
  weldingOdfCreateChange = this.weldingOdfCreate.asObservable();
  private weldingOdfUpdate = new BehaviorSubject<any>({});
  weldingOdfUpdateChange = this.weldingOdfUpdate.asObservable();

  emit(event: any) {
    this.event.next(event);
    this.coordinates.next(event);
    this.deptDataObj.next(event);
    this.pillarDataObj.next(event);
    this.poolDataObj.next(event);
    if (event.type === 'poolList') {
      this.reloadPool.next(event);
    }
    if (event.type && ('sleeveList' === event.type.type ||
      'poolSleeveList' === event.type.type || 'pillarSleeveList' === event.type.type)) {
      this.reloadSleeve.next(event);
    }
    if (event.type === 'cableList') {
      this.reloadCable.next(event);
    }
    if (event.type === 'pillarList') {
      this.reloadPillar.next(event);
    }
    if (event.type === 'stationList') {
      this.reloadStation.next(event);
    }
    if (event.type === 'weldingOdfList') {
      this.reloadWeldingOdfList.next(event);
    }
    if (event.type === 'weldingSleeveList') {
      this.reloadWeldSleeve.next(event);
    }
    if (event.type === 'odfList') {
      this.reloadOdf.next(event);
    }
  }

  emitDataChange(event: any) {
    if (event.type === 'onTabChangeActive') {
      this.data.next(event);
    }
    if (event.type === 'fromStation') {
      this.odfStation.next(event);
    }
    if (event.type === 'weldingOdfCreate') {
      this.weldingOdfCreate.next(event);
    }
    if (event.type === 'weldingOdfUpdate') {
      this.weldingOdfUpdate.next(event);
    }
  }

  on(eventType: (any)): Observable<any> {
    return this.event.pipe(filter(t => t.type === eventType));
  }

}
