import { Component, OnInit } from '@angular/core';
import {FieldType} from '@ngx-formly/core';

@Component({
  selector: 'select-type',
  templateUrl: './select-type.component.html',
  styleUrls: ['./select-type.component.scss']
})
export class SelectTypeComponent extends FieldType implements OnInit {

    ngOnInit() {
    }

}
