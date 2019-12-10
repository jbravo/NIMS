import {AfterViewInit, Component, ElementRef, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, ViewChild} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {isFunction} from 'util';
import {AppComponent} from '@app/app.component';

@Component({
  selector: 'input-search-control',
  templateUrl: './input-search-control.component.html',
  styleUrls: ['./input-search-control.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputSearchControlComponent),
      multi: true
    }]
})
export class InputSearchControlComponent implements ControlValueAccessor, OnInit, AfterViewInit, OnChanges {
  @ViewChild('input') input: ElementRef;
  @Input() maxLength;
  @Input() ignoreEnterEvent: boolean;
  @Input() showBtn: boolean;
  @Input() isReadonly: boolean;
  @Input() isKeepFocus: boolean;
  @Input() isRequired: boolean;
  @Input() alwaysEnableBtn: boolean;
  @Input() placeholder;
  @Input() focus;
  @Input() type;
  @Output() onSearch = new EventEmitter();
  @Output() onFocus = new EventEmitter();

  private onChange: Function;
  isDisabled: boolean;
  value = '';
  placeholderStation = this.app.translation.translate('station.label.search.basic');
  placeholderOdf = this.app.translation.translate('odf.Code.input');
  placeholderCable = this.app.translation.translate('cable.cableid.label');
  placeholderPillar = this.app.translation.translate('pillar.pillarCode.input');
  placeholderPool = this.app.translation.translate('pool.poolCode.input');
  placeholderSleeve = this.app.translation.translate('input.infra.sleeves.code');

  constructor(
    private app: AppComponent
  ) {
  }

  search(e) {
    if (!this.ignoreEnterEvent && e.code === 'Enter') {
      this.blurInput();
    }
  }

  ngOnInit() {
    if (this.type === 'station') {
      this.placeholder = this.placeholderStation;
    }
    if (this.type === 'odf') {
      this.placeholder = this.placeholderOdf;
    }
    if (this.type === 'cable') {
      this.placeholder = this.placeholderCable;
    }
    if (this.type === 'pillar') {
      this.placeholder = this.placeholderPillar;
    }
    if (this.type === 'pool') {
      this.placeholder = this.placeholderPool;
    }
    if (this.type === 'sleeve') {
      this.placeholder = this.placeholderSleeve;
    }
  }

  ngAfterViewInit(): void {
    this.setFocus();
  }

  ngOnChanges() {
    this.setFocus();
  }

  setFocus() {
    setTimeout(() => {
      if (!!this.focus && this.input) {
        this.input.nativeElement.focus();
        this.onFocus.emit();
        this.focus = false;
      }
    }, 100);
  }

  blurInput() {
    if (!this.alwaysEnableBtn && this.isDisabled) {
      return;
    }
    this.onSearch.emit(this.value);
    this.input.nativeElement.blur();
    if (this.isKeepFocus) {
      this.input.nativeElement.focus();
      if (this.type === 'station') {
        this.placeholder = this.placeholderStation;
      }
      if (this.type === 'odf') {
        this.placeholder = this.placeholderOdf;
      }
      if (this.type === 'cable') {
        this.placeholder = this.placeholderCable;
      }
      if (this.type === 'pillar') {
        this.placeholder = this.placeholderPillar;
      }
      if (this.type === 'pool') {
        this.placeholder = this.placeholderPool;
      }
      if (this.type === 'sleeve') {
        this.placeholder = this.placeholderSleeve;
      }
    }
  }

  changeValue(e) {
    this.value = e.target.value;
    if (isFunction(this.onChange)) {
      this.onChange(e.target.value);
    }
    if (e.target.value !== '') {
      return;
    }
    if (this.type === 'station') {
      this.placeholder = this.placeholderStation;
    }
    if (this.type === 'odf') {
      this.placeholder = this.placeholderOdf;
    }
    if (this.type === 'cable') {
      this.placeholder = this.placeholderCable;
    }
    if (this.type === 'pillar') {
      this.placeholder = this.placeholderPillar;
    }
    if (this.type === 'pool') {
      this.placeholder = this.placeholderPool;
    }
    if (this.type === 'sleeve') {
      this.placeholder = this.placeholderSleeve;
    }
  }

  writeValue(val) {
    this.value = val || '';
  }

  registerOnChange(fn) {
    this.onChange = fn;
  }

  registerOnTouched(fn) {
  }

  setDisabledState(isDisabled: boolean) {
    this.isDisabled = isDisabled;
  }

  onFocusInputSearch(event) {
    if (event) {
      if (event.target.value === '') {
        this.placeholder = '';
      }
    }
  }

  onBlurInputSearch(event) {
    if (event && event.target.value === '') {
      if (this.type === 'station') {
        this.placeholder = this.placeholderStation;
      }
      if (this.type === 'odf') {
        this.placeholder = this.placeholderOdf;
      }
      if (this.type === 'cable') {
        this.placeholder = this.placeholderCable;
      }
      if (this.type === 'pillar') {
        this.placeholder = this.placeholderPillar;
      }
      if (this.type === 'pool') {
        this.placeholder = this.placeholderPool;
      }
      if (this.type === 'sleeve') {
        this.placeholder = this.placeholderSleeve;
      }
    }
  }


}
