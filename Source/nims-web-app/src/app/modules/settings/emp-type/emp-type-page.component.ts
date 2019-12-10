import { Component, OnInit } from '@angular/core';
import { EmpTypeService } from '@app/core';

@Component({
  selector: 'emp-type-page',
  templateUrl: './emp-type-page.component.html',
})
export class EmpTypePageComponent implements OnInit {
  resultList: any = {};
  constructor(
    private empTypeService: EmpTypeService) {

    console.log('constructor EmpTypePageComponent');
  }

  ngOnInit() {
  }

}
