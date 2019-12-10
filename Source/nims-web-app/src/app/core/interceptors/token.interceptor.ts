import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpResponse
} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {tap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {CommonUtils} from '@app/shared/services';
import {HrStorage} from '../services/auth/HrStorage';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(public router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    request = request.clone({
      setHeaders: {
        'Authorization': `Bearer ${this.getAccessToken()}`,
        'userId': this.getUserId(),
        // 'viettel-api-key': `c9f55dab-d44a-43c0-a922-d7133efdb28d`,
      }
    });
    return next.handle(request).pipe(
      tap(event => {
        if (event instanceof HttpResponse) {

        }
      }, error => {
        if (error.status === 401) {
          // Do later: set currentUrl when redirect from SSO
          // HrStorage.setCurrentUrl(this.router.url);
          // this.router.navigate(['/auth-sso']);
          console.log('CommonUtils.logoutAction();');
        }
      })
    );
  }

  getAccessToken() {
    const userToken = HrStorage.getUserToken();
    if (userToken == null) {
      return '';
    } else {
      return userToken.access_token;
    }
  }

  getUserId() {
    const userToken = HrStorage.getUserToken();
    if (userToken == null) {
      return '';
    } else {
      return userToken.userId + '';
    }
  }
}
