import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Constants } from '@env/environment';
import { HrStorage } from '../services/auth/HrStorage';
@Injectable()
export class AuthGuard implements CanActivate {

    constructor(
        private router: Router
    ) {}

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
            const userToken = HrStorage.getUserToken();
            const user: any = {};
            user.userId = 1;
            user.employeeCode = '011315';
            HrStorage.setUserToken(user);
          // const tokenExpires = Number(CommonUtils.getTokenExpiresIn());
          // console.log('tokenExpires', new Date(tokenExpires));
          // if (!tokenExpires || tokenExpires < new Date().getTime()
          // if (!userToken) {
          //   this.router.navigate(['/auth-sso']);
          //   return false;
          // }
        return true;
      }

}
