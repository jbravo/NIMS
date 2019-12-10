import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {TranslationService} from 'angular-l10n';

@Injectable({
  providedIn: 'root'
})

export class ToastService {

  constructor(
    private toastrService: ToastrService,
    private translation: TranslationService
  ) {
    this.toastrService.toastrConfig.closeButton = true;
    this.toastrService.toastrConfig.positionClass = 'toast-bottom-right';
    this.toastrService.toastrConfig.easing = 'ease-in';
    this.toastrService.toastrConfig.timeOut = 3000;
  }

  openSuccessToast(message?: string, title?: string) {
    this.toastrService.success(message ? message : this.translation.translate('common.label.copied'), title ? title : 'Thành công');
  }

  openErrorToast(message: string, title?: string) {
    this.toastrService.error(message, title ? title : 'Có lỗi xảy ra');
  }

  openWarningToast(message?: string, title?: string) {
    this.toastrService.warning(message ? message : 'Thao tác của bạn có thể gây lỗi', title ? title : 'Cảnh báo');
  }

  openInfoToast(message?: string, title?: string) {
    this.toastrService.info(message, title ? title : 'Thông báo');
  }

  setToastrConfig(timeOut) {
    this.toastrService.toastrConfig.timeOut = timeOut;
  }

}
