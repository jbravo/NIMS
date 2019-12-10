import { Component, OnInit } from '@angular/core';
import { AllowanceTypeService } from '@app/core';

@Component({
  selector: 'allowance-type-page',
  templateUrl: './allowance-type-index.component.html'
})
export class AllowanceTypePageComponent implements OnInit {
  resultList: any = {};
  constructor (
      private allowanceTypeService: AllowanceTypeService) {
  }

  ngOnInit() {
  }
}
