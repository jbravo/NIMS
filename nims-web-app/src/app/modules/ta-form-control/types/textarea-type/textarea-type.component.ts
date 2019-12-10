import { Component, OnInit } from '@angular/core';
import {FieldType} from '@ngx-formly/core';

@Component({
  selector: 'textarea-type',
  templateUrl: './textarea-type.component.html',
  styleUrls: ['./textarea-type.component.scss']
})
export class TextareaTypeComponent extends FieldType implements OnInit {
  ngOnInit() {
  }

}
