import { SysPropertyService } from './../../modules/settings/sys-property/sys-property.service';
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PropertyResolver implements Resolve<any> {
  constructor(private apiService: SysPropertyService) {}

  resolve(route: ActivatedRouteSnapshot, rstate: RouterStateSnapshot): Observable<any>  {
    // URL: rstate.url
    // TODO: get MenuId by URL
    return this.apiService.findPropertyDetailsByMenuId(17, 1740);
  }
}
