import {Component, OnInit} from '@angular/core';
import {FieldType} from '@ngx-formly/core';

@Component({
  selector: 'calendar-type',
  templateUrl: './calendar-type.component.html',
  styleUrls: ['./calendar-type.component.scss']
})
export class CalendarTypeComponent extends FieldType implements OnInit {
  datemax: Date;
  dattmin: Date;

  ngOnInit() {
    this.datemax = new Date();
    this.dattmin = new Date();
    this.datemax.setDate(this.datemax.getDate() + 3);
    this.dattmin.setDate(this.dattmin.getDate() - 365);
    this.formControl.valueChanges.subscribe(res => {
      console.log(res);
    });
  }

}

