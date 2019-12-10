import {Injectable} from '@angular/core';
import {TranslationService} from 'angular-l10n';
import {PermissionService} from '@app/shared/services/permission.service';
import {AbstractControl} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class DataCommonService {

  constructor(
    private permissionService: PermissionService,
    private translation: TranslationService
  ) {
  }

  // Lay cac list cua dropdown co dinh
  public getDropDownList(constant: any[]) {
    const resultList = [];
    resultList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
    for (let i = 0; i < constant.length; i++) {
      resultList.push({label: this.translation.translate(constant[i].label), value: constant[i].value});
    }
    return resultList;
  }

    // Lay cac list cua dropdown co dinh ko select
    public getDropDownListNoSelected(constant: any[]) {
      const resultList = [];
      for (let i = 0; i < constant.length; i++) {
        resultList.push({label: this.translation.translate(constant[i].label), value: constant[i].value});
      }
      return resultList;
    }

  // autocomplete cua dia ban
  public filteredLocationFunc(control: AbstractControl, event: any, filteredLocation: any[]) {
    this.permissionService.filterLocation(control.value).subscribe(res => {
      filteredLocation = [];
      const resultList = res.content.listData;
      for (let i = 0; i < resultList.length; i++) {
        const result = resultList[i];
        if (result.locationName.toLowerCase().indexOf(event.query.toLowerCase()) === 0) {
          filteredLocation.push(result);
        }
      }
    });
  }

  // autocomplete cua don vi
  public filteredDeptFunc(control: AbstractControl, event: any, filteredDept: any[]) {
    this.permissionService.filterDept(control.value).subscribe(res => {
      filteredDept = [];
      let resultList = res.content.listData;
      for (let i = 0; i < resultList.length; i++) {
        let result = resultList[i];
        if (result.deptName.toLowerCase().indexOf(event.query.toLowerCase()) === 0) {
          filteredDept.push(result);
        }
      }
    });
  }

  // lay gai tri label cua dropdown theo value
  public getLabelFromValue(value: any, list: any[]) {
    list.forEach(item => {
      if (item.value == value) {
        return item.label;
      }
    });
  }

}
