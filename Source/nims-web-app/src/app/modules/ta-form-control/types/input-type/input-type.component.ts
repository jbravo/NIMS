import {Component, OnInit} from '@angular/core';
import {FieldType} from '@ngx-formly/core';

@Component({
    selector: 'input-type',
    templateUrl: './input-type.component.html',
    styleUrls: ['./input-type.component.scss']
})
export class InputTypeComponent extends FieldType implements OnInit {

    ngOnInit() {
    }

}
