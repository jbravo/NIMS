import { BaseComponent } from './../../../../shared/components/base-component/base-component.component';
import { Component, OnInit } from '@angular/core';
import { SysPropertyFormComponent } from '../sys-property-form/sys-property-form.component';
import { FormGroup, FormBuilder, Validators, ValidatorFn } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { DEFAULT_MODAL_OPTIONS } from '@app/core/app-config';
import { AppComponent } from '@app/app.component';
import { ValidationService } from '@app/shared/services';
import { SysPropertyService } from '../sys-property.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SysPropertyDetailBean } from '@app/core/models/sys-property-details.model';
@Component({
  selector: 'app-sys-property-index',
  templateUrl: './sys-property-index.component.html'
})
export class SysPropertyIndexComponent extends BaseComponent implements OnInit {
  /* Variables */
  resultList: any = {};
  menus = [];
  formSearch: FormGroup;
  selectedProperty: any = {};
  formConfig: any = {
    code: [''],
    name: ['', [Validators.minLength(4)]],
    startDate: [''],
    endDate: [''],
    menuId: [''],
  };
  /* Constructor */
  constructor(public actr: ActivatedRoute
    , public formBuilder: FormBuilder
    , public modalService: NgbModal
    , private sysPropertyService: SysPropertyService
    , public app: AppComponent) {
    super(actr, formBuilder);
    this.formSearch = this.buildForm({}, this.formConfig);
    this.loadReference();
  }
  ngOnInit() {
  }
  get f() {
    return this.formSearch.controls;
  }

  /**
   * Load cac list du lieu lien quan
   */
  private loadReference(): void {
    this.sysPropertyService.findMenus().subscribe(res => {
      this.menus = res.data;
    });
  }
  /**
  * thuc hien tim kiem
  */
  processSearch(event) {
    this.app.isProcessing(true);
    if (CommonUtils.isValidForm(this.formSearch)) {
      this.sysPropertyService.search(this.formSearch.value, event).subscribe(res => {
        this.resultList = res;
        for (const item of this.resultList) {
          item.startDate = new Date(item.startDate);
          item.endDate = new Date(item.endDate);
        }
        this.app.isProcessing(false);
      });
    }
  }

  /**
  * prepareUpdate
  * param item
  */
  prepareSaveOrUpdate(item) {
    const modalRef = this.modalService.open(SysPropertyFormComponent, DEFAULT_MODAL_OPTIONS);
    if (item && item.propertyId > 0) {
      this.sysPropertyService.findOne(item.propertyId)
        .subscribe(res => {
          modalRef.componentInstance.setFormValue(res.data);
        });
    }

    modalRef.result.then((result) => {
      if (!result) {
        return;
      }
      if (this.sysPropertyService.requestIsSuccess(result)) {
        this.processSearch(null);
      }
    });
  }
  /**
  * prepareDelete
  * param item
  */
  processDelete(item) {
    if (item && item.propertyId > 0) {
      this.app.confirmDelete(null, () => {// on accepted
        this.sysPropertyService.deleteById(item.propertyId)
          .subscribe(res => {
            if (this.sysPropertyService.requestIsSuccess(res)) {
              this.processSearch(null);
            }
          });
      }, () => {// on rejected

      });
    }
  }
  /**
   * showProperty
   *
   */
  showProperty(item: any): void {
    if (item && item.propertyId > 0) {
      this.sysPropertyService.findOne(item.propertyId)
        .subscribe(res => {
            this.selectedProperty = res.data;
          }
        );
    }
  }

}
