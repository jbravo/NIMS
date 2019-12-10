import {Injectable} from '@angular/core';
import {DialogService, DynamicDialogRef} from 'primeng/api';
import {ConfirmDialogComponent} from './shared-components/confirm-dialog/confirm-dialog.component';
import {getSuggestStatusByValue} from './suggest.constant';

@Injectable({
  providedIn: 'root'
})
export class SuggestDialogService {

  constructor(public dialogService: DialogService) {
  }

  getDialog(suggestStation: any): DynamicDialogRef {
    console.log(suggestStation.suggestStatus);
    switch (suggestStation.suggestStatus) {
      case 3: {
        return this.genConfirmDialog(suggestStation.suggestStatus, 'Phê duyệt (Sau khảo sát)', suggestStation);
      }
      case 0:
      case 18:
      case 13: {
        return this.genConfirmDialog(suggestStation.suggestStatus, 'Phê duyệt', suggestStation);
      }
      case 1:
      case 10:
      case 16: {
        return this.genConfirmDialog(suggestStation.suggestStatus, 'Cập nhật', suggestStation);
      }
      case 4: {
        return this.genConfirmDialog(suggestStation.suggestStatus, 'Thẩm định thông tin vô tuyến', suggestStation);
      }
      case 6:
      case 12: {
        return this.genConfirmDialog(suggestStation.suggestStatus, 'Trình kí VOffice', suggestStation);
      }
    }
  }

  genConfirmDialog(valueStatus, header, data) {
    const ref = this.dialogService.open(ConfirmDialogComponent, {
      data: {
        title: getSuggestStatusByValue(valueStatus).label,
        suggestStation: data
      },
      header: header,
      styleClass: 'dialogXL'
    });
    return ref;
  }
}
