import {ConfirmationService, MessageService} from 'primeng/api';
import {Component} from '@angular/core';
import {Language, LocaleService, TranslationService} from 'angular-l10n';
import {HelperService} from './shared/services/helper.service';
import {SysGridViewService} from '@app/shared/services/sys-grid-view.service';

@Component({
  selector: 'app-root',
  styleUrls: ['./app.component.css'],
  templateUrl: './app.component.html',
  providers: [MessageService]
})
export class AppComponent {

  blocked = false;
  @Language() lang: string;

  constructor(
    public locale: LocaleService,
    public translation: TranslationService,
    public helperService: HelperService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private sysGridViewService: SysGridViewService,
  ) {
    this.helperService.APP_TOAST_MESSAGE.subscribe(data => {
      this.processReturnMessage(data);
    });
  }

  /**
   * confirmMessage
   */
  confirmMessage(messageCode: string, accept: Function, reject: Function) {
    const message = this.translation.translate(messageCode || 'common.message.confirm.save');
    return this.confirmationService.confirm({
      message: message,
      header: this.translation.translate('common.button.confirm'),
      icon: 'pi pi-exclamation-triangle',
      accept: accept,
      reject: reject
    });
  }

  /**
   * confirmDelete
   */
  confirmDelete(messageCode: string, accept: Function, reject: Function) {
    const message = this.translation.translate(messageCode || 'common.message.confirm.delete');
    return this.confirmationService.confirm({
      message: message,
      header: this.translation.translate('common.button.confirm'),
      icon: 'pi pi-info-circle',
      accept: accept,
      reject: reject
    });
  }

  /**
   * successMessage
   * param errorType
   * param errorCode
   */
  successMessage(code: string, message?: string, args?: any) {
    this.toastMessage('success', code, message, args);
  }

  /**
   * errorMessage
   * param errorType
   * param errorCode
   */
  errorMessage(code: string, message?: string, args?: any) {
    this.toastMessage('error', code, message, args);
  }

  /**
   * warningMessage
   * param errorType
   * param errorCode
   */
  warningMessage(code: string, message?: string, args?: any) {
    this.toastMessage('warn', code, message, args);
  }

  /**
   * errorFormMessage
   * param errorType
   * param errorCode
   */
  errorFormMessage(code: string, message?: string, args?: any, key?: any) {
    this.formMessage('error', code, message, args, key);
  }

  /**
   * toastMessage
   * param severity
   * param errorType
   * param errorCode
   */
  public toastMessage(severity: string, code: string, message?: string, args?: any) {
    let detail;
    if (!message) {
      detail = this.translation.translate(`${code}`, args);
    } else {
      detail = this.translation.translate(`${code}.${message}`, args);
    }
    severity = severity === 'warning' ? 'warn' : severity;
    const summary = this.translation.translate(`app.messageSummary`);
    this.messageService.add({key: 'process', severity: severity.toLowerCase(), summary: summary, detail: detail});
  }

  formMessage(severity: string, code: string, message?: string, args?: any, key?: any) {
    let detail;
    if (!message) {
      detail = this.translation.translate(`${code}`, args);
    } else {
      detail = this.translation.translate(`${code}.${message}`, args);
    }
    severity = severity === 'warning' ? 'warn' : severity;

    // const summary = this.translation.translate(`app.messageSummary`);
    const msg = {severity: severity.toLowerCase(), detail: detail};
    if (key) {
      msg['key'] = key;
    }
    this.messageService.add(msg);
  }

  messageProcess(status, message) {
    let severity;
    if (status == '200') {
      severity = 'success';
    }
    if (status == '404' || status == '500' || status == '400') {
      severity = 'error';
    }
    // let detail = this.translation.translate(message);
    this.messageService.add({key: 'process', severity: severity.toLowerCase(), detail: message});
  }

  /**
   * process return message
   * param serviceResponse
   */
  public processReturnMessage(serviceResponse: any) {
    if (!serviceResponse) {
      return;
    }
    if (serviceResponse.status === 500 || serviceResponse.status === 0) {
      this.errorMessage('message.error.have.error');
      return;
    }
    if (serviceResponse.code) {
      this.toastMessage(serviceResponse.type, serviceResponse.code, serviceResponse.message, serviceResponse.args);
      return;
    }
  }

  /**
   * request is success
   */
  public requestIsError(): void {
    this.toastMessage('error', 'message.error.have.error');
  }

  public isProcessing(isProcessing: boolean) {
    this.blocked = isProcessing;
  }

  public storage() {
    const storage = {
      userToken: {
        access_token: 'tulv_token_test',
        employeeCode: '010123',
        fullName: 'tulv',
        redirect: 'true',
        tokenExpiresIn: '1569238833',
        userId: '200',
        userMenuList: [],
        userPermissionList: [
          {
            operationCode: 'CREATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'STATION'
          },
          {
            operationCode: 'UPDATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'STATION'
          },
          {
            operationCode: 'VIEW',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'STATION'
          },
          {
            operationCode: 'DELETE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'STATION'
          },
          {
            operationCode: 'IMPORT',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'STATION'
          },
          {
            operationCode: 'CREATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'ODF'
          },
          {
            operationCode: 'UPDATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'ODF'
          },
          {
            operationCode: 'VIEW',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'ODF'
          },
          {
            operationCode: 'DELETE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'ODF'
          },
          {
            operationCode: 'IMPORT',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'ODF'
          },
          {
            operationCode: 'CREATE_WELD_CONNECTION',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'ODF'
          }
          ,
          {
            operationCode: 'CREATE_WELD_CONNECTION',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'ODF'
          }
          ,
          {
            operationCode: 'UPDATE_WELD_CONNECTION',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'ODF'
          }
          ,
          {
            operationCode: 'DELETE_WELD_CONNECTION',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'ODF'
          },
          {
            operationCode: 'VIEW_WELD_CONNECTION',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'ODF'
          },
          {
            operationCode: 'CREATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'PILLAR'
          },
          {
            operationCode: 'UPDATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'PILLAR'
          },
          {
            operationCode: 'VIEW',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'PILLAR'
          },
          {
            operationCode: 'DELETE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'PILLAR'
          },
          {
            operationCode: 'IMPORT',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'PILLAR'
          },
          {
            operationCode: 'CREATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'POOL'
          },
          {
            operationCode: 'UPDATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'POOL'
          },
          {
            operationCode: 'VIEW',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'POOL'
          },
          {
            operationCode: 'DELETE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'POOL'
          },
          {
            operationCode: 'IMPORT',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'POOL'
          },
          {
            operationCode: 'CREATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'SLEEVE'
          },
          {
            operationCode: 'UPDATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'SLEEVE'
          },
          {
            operationCode: 'VIEW',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'SLEEVE'
          },
          {
            operationCode: 'DELETE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'SLEEVE'
          },
          {
            operationCode: 'IMPORT',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'SLEEVE'
          },
          {
            operationCode: 'CREATE_CONNECTION',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'SLEEVE'
          },
          {
            operationCode: 'UPDATE_CONNECTION',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'SLEEVE'
          },
          {
            operationCode: 'DELETE_CONNECTION',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'SLEEVE'
          },
          {
            operationCode: 'VIEW_CONNECTION',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'SLEEVE'
          }, {
            operationCode: 'CREATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'CABLES_IN_STATION'
          },
          {
            operationCode: 'UPDATE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'CABLES_IN_STATION'
          },
          {
            operationCode: 'VIEW',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'CABLES_IN_STATION'
          },
          {
            operationCode: 'DELETE',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'CABLES_IN_STATION'
          },
          {
            operationCode: 'IMPORT',
            defaultDomain: '421',
            grantedDomain: '421',
            resourceCode: 'CABLES_IN_STATION'
          },
        ],
        sysGridView: [],
        deptIds: [],
        locationIds: []
      }
    };
    this.sysGridViewService.getGridView({userId: storage.userToken.userId}).subscribe(res => {
      storage.userToken.sysGridView = res.data;
    }, error => {
      // TO DO
    }, () => {
      localStorage.setItem('_HrStorage', JSON.stringify(storage));
      this.sysGridViewService.getDeptByUserId().subscribe(res => {
      });
    });
  }

}
