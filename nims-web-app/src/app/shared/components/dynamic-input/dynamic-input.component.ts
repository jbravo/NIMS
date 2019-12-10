import { SysPropertyDetailBean } from './../../../core/models/sys-property-details.model';
import { FormControl } from '@angular/forms';
import { Component, OnInit, Input, } from '@angular/core';

@Component({
  selector: 'dynamic-input',
  templateUrl: './dynamic-input.component.html',
  styleUrls: ['./dynamic-input.component.css']
})
export class DynamicInputComponent implements OnInit {
  // OnChanges
  @Input()
  public formControlProp: FormControl;
  @Input()
  public property: SysPropertyDetailBean;
  @Input()
  public objectId: string;
  public value: string;
  constructor() {
  }
  ngOnInit() {
    if (this.formControlProp.value) {
      this.value = this.formControlProp.value;
    }

  }
  onChange(newValue: string) {
    this.formControlProp.setValue(newValue);
  }

}
