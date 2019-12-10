import {FormControl} from '@angular/forms';
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChange,
  SimpleChanges,
  ViewChild
} from '@angular/core';
import {SelectItem} from 'primeng/api';
import {TranslationService} from 'angular-l10n';
import {Dropdown} from 'primeng/primeng';

@Component({
  selector: 'select-filter',
  templateUrl: './select-filter.component.html',
})
export class SelectFilterComponent implements OnChanges, OnInit {

  @Input()
  public property: FormControl;
  @Input()
  public isMultiLanguage: boolean;
  @Input()
  public options: any;
  @Input()
  public optionLabel: string;
  @Input()
  public optionValue: string;
  @Input()
  public placeHolder: string;
  @Input()
  public disabled = false;
  @Input()
  public filter = true;
  @Input()
  public styleClass: string;

  @ViewChild('dd') dropDown: Dropdown;

  @Input()
  set focus(isFocus: boolean) {
    if (isFocus) {
      this.dropDown.focus();
    }
  }

  selectedValue: any;
  checkShowClear = false;
  listData: SelectItem[];
  @Output()
  public onChange: EventEmitter<any> = new EventEmitter<any>();

  constructor(
    public translation: TranslationService
  ) {
    this.listData = [];
  }

  ngOnChanges(changes: SimpleChanges) {
    const options: SimpleChange = changes.options;
    // const property: SimpleChange = changes.property;
    if (options && options.currentValue) {
      this.initDropdown(options.currentValue);
    }
    this.selectedValue = this.property.value;
    this.checkShowClear = (this.selectedValue === null || this.selectedValue === '') ? false : true;
  }

  ngOnInit() {
    this.initDropdown(this.options);
    this.selectedValue = this.property.value;
    this.property.valueChanges.subscribe(
      (value) => {
        this.selectedValue = value;
        this.checkShowClear = (this.selectedValue === null || this.selectedValue === '') ? false : true;
      }
    );
  }

  initDropdown(data?: any) {
    if (data) {
      this.listData = [];
      for (const item of data) {
        let label = item[this.optionLabel];
        if (this.isMultiLanguage) {
          label = this.translation.translate(label);
        }
        this.listData.push({label: label, value: item[this.optionValue]});
      }
    }
  }

  selectedChange() {
    this.property.setValue(this.selectedValue);
    this.onChange.emit(this.selectedValue);
  }
}
