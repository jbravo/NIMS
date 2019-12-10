import {CommonUtils} from '@app/shared/services';
import {FormControl} from '@angular/forms';
import {Component, OnInit, Input, OnChanges, ElementRef, ViewChild} from '@angular/core';
import {HelperService} from '@app/shared/services/helper.service';
import {AppComponent} from '@app/app.component';

@Component({
  selector: 'date-picker',
  templateUrl: './date-picker.component.html',
})
export class DatePickerComponent implements OnInit, OnChanges {

  @Input()
  public property: FormControl;
  @Input()
  public dateFormat: string;
  @Input()
  public onChange: Function;
  @Input()
  public yearRange: string;
  @Input()
  public showIcon = true;
  @Input()
  public placeholder: string;

  @Input()
  public errorMsg: string;

  @Input()
  public showTime = false;


  @Input()
  public disabled: boolean = false;

  public dateValue: Date;

  currentYear = new Date().getFullYear();

  yearMinMax = 100;

  constructor(
    private helperService: HelperService, private app: AppComponent
  ) {
  }

  private initDefaultYear() {
    this.yearRange = (this.currentYear - this.yearMinMax) + ':' + (this.currentYear + this.yearMinMax);
  }

  ngOnInit() {
    if (this.property.value) {
      this.dateValue = new Date(this.property.value);
    } else {
      this.dateValue = null;
    }

    if (!this.yearRange) {
      this.initDefaultYear();
    }

    if (!this.dateFormat) {
      this.dateFormat = 'dd/mm/yy';
      if (!this.placeholder) {
        this.placeholder = 'dd/mm/yyyy';
      }
    }
  }

  onInput() {
    if (this.dateValue) {
      this.property.setValue(this.dateValue.getTime());
    } else {
      this.property.setValue(null);
    }
    if (this.onChange) {
      this.onChange();
    }
  }

  onBlur(event) {
    let tempTime = new Date('01/01/' + (this.currentYear - this.yearMinMax)).getTime();
    if (!this.dateValue && event.currentTarget.value !== '') {
      if (this.errorMsg) {
        this.app.errorMessage(this.errorMsg);
      } else {
        this.app.errorMessage('validate.dateInvalid');
      }
      //      this.helperService.APP_TOAST_MESSAGE.next({type: 'ERROR', code: 'dateInvalid', message: null});
    } else if (this.dateValue && this.dateValue.getTime() < tempTime) {
      if (this.errorMsg) {
        this.app.errorMessage(this.errorMsg);
      } else {
        this.app.errorMessage('validate.dateInvalid');
      }
      //      this.helperService.APP_TOAST_MESSAGE.next({type: 'ERROR', code: 'dateInvalid', message: null});
      this.dateValue = null;
      this.onInput();
    }
  }

  /**
   * set Value
   */
  setValue(value: number) {
    if (CommonUtils.nvl(value) > 0) {
      this.dateValue = new Date(value);
    } else {
      this.dateValue = null;
    }
    this.onInput();
  }

  /**
   * ngOnChanges
   */
  ngOnChanges() {
    this.ngOnInit();
  }

}
