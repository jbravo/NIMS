import { DEFAULT_MODAL_OPTIONS } from '@app/core/app-config';
import { Component, OnInit, Input, ViewChildren, AfterViewInit, OnChanges } from '@angular/core';
import { FormControl } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { DataPickerService } from '@app/core';
import { DataPickerModalComponent } from './data-picker-modal/data-picker-modal.component';

@Component({
  selector: 'data-picker',
  templateUrl: './data-picker.component.html',
})
export class DataPickerComponent implements OnInit, AfterViewInit, OnChanges {
  @Input()
  public systemCode: string;
  @Input()
  public property: FormControl;
  @Input()
  public isRequiredField = false;
  @Input()
  public operationKey: string;
  @Input()
  public adResourceKey: string;
  @Input()
  public objectBO: string;
  @Input()
  public codeField: string;
  @Input()
  public nameField: string;
  @Input()
  public orderField: string;
  @Input()
  public selectField: string;
  @Input()
  public filterCondition: string;
  @Input()
  public isDisplayCode: false;
  @ViewChildren('displayName') displayName;
  @ViewChildren('buttonChose') buttonChose;

  constructor(
    private modalService: NgbModal
    , private service: DataPickerService
  ) {
  }

  ngOnInit() {
  }
  ngAfterViewInit() {
    this.onChangeDataId();
    if (this.systemCode) {
      this.service.setSystemCode(this.systemCode);
    }
  }
  /**
   * onChange dataId then load data name
   */
  private onChangeDataId() {
    if (CommonUtils.isNullOrEmpty(this.property.value)) {
      if (this.displayName) {
        this.displayName.first.nativeElement.value = '';
      }
      return;
    }
    // thuc hien lay ten don vi de hien thi
    this.service.findByNationId(CommonUtils.getNationId(), this.property.value, {
            operationKey: CommonUtils.getPermissionCode(this.operationKey)
          , adResourceKey: CommonUtils.getPermissionCode(this.adResourceKey)
          , filterCondition: this.filterCondition
          , objectBO: this.objectBO
          , codeField: this.codeField
          , nameField: this.nameField
          , orderField: this.orderField
          , selectField: this.selectField
        })
        .subscribe((data) => {
          if (data) {
            if (this.isDisplayCode) {
              this.displayName.first.nativeElement.value = data.codeField;
            } else {
              this.displayName.first.nativeElement.value = data.nameField;
            }
          }
        });
  }
  /**
   * onFocus
   */
  public onFocus() {
    this.buttonChose.first.nativeElement.focus();
    this.buttonChose.first.nativeElement.click();
  }
  /**
   * onChose
   * param item
   */
  public onChose() {
    const modalRef = this.modalService.open(DataPickerModalComponent, {
      backdrop: 'static',
    });
    modalRef
      .componentInstance
      .setInitValue({
          operationKey: CommonUtils.getPermissionCode(this.operationKey)
        , adResourceKey: CommonUtils.getPermissionCode(this.adResourceKey)
        , filterCondition: this.filterCondition
        , objectBO: this.objectBO
        , codeField: this.codeField
        , nameField: this.nameField
        , orderField: this.orderField
        , selectField: this.selectField
      });
    modalRef.result.then((item) => {
      if (!item) {
        return;
      }
      this.property.setValue(item.selectField);
      if (this.isDisplayCode) {
        this.displayName.first.nativeElement.value = item.codeField;
      } else {
        this.displayName.first.nativeElement.value = item.nameField;
      }
    });
  }
  /**
   * delete
   */
  public delete() {
    this.property.setValue('');
    this.onChangeDataId();
  }
  /**
   * ngOnChanges
   */
  ngOnChanges() {
    this.onChangeDataId();
  }
}
