import { Component, OnInit } from '@angular/core';
import { LabourContractTypeService } from '@app/core';

@Component({
  selector: 'app-labour-contract-type',
  templateUrl: './labour-contract-type.component.html'
})
export class LabourContractTypeComponent implements OnInit {

  constructor(
    private labourContractTypeService: LabourContractTypeService) {
    }

  ngOnInit() {
  }

}
