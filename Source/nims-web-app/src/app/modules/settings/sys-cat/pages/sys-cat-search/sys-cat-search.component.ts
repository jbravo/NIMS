import { CommonUtils } from '@app/shared/services/common-utils.service';
import { AppComponent } from '@app/app.component';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SysCatService, SysCatTypeService } from '@app/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SysCatAddComponent } from '../sys-cat-add/sys-cat-add.component';
import { DEFAULT_MODAL_OPTIONS } from '@app/core/app-config';
import { ValidationService } from '@app/shared/services';

@Component({
  selector: 'sys-cat-search',
  templateUrl: './sys-cat-search.component.html'
})
export class SysCatSearchComponent implements OnInit {
  resultList: any = {};
  formSearch: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private modalService: NgbModal,
    private sysCatService: SysCatService,
    private sysCatTypeService: SysCatTypeService,
    private app: AppComponent
  ) {
    this.buildForm();
  }

  ngOnInit() {
  }
  get f () {
    return this.formSearch.controls;
  }
  private buildForm(): void {
    this.formSearch = this.formBuilder.group({
      code: ['', Validators.compose([Validators.maxLength(50)])],
      name: ['', Validators.compose([Validators.maxLength(500)])],
    });
  }
  /**
   * thuc hien tim kiem
   */
  processSearch(event): void {
    if (!CommonUtils.isValidForm(this.formSearch)) {
      return;
    }
    this.sysCatService.search(this.formSearch.value, event).subscribe(res => {
      this.resultList = res;
    });
  }

  /**
   * prepare insert/update
   */
  prepareSaveOrUpdate(item): void {
    if (item && item.sysCatId > 0) {
      this.sysCatService.findOne(item.sysCatId)
        .subscribe(res => {
          this.sysCatTypeService.findOne(res.data.sysCatTypeId)
            .subscribe(resSCType => {
              res.data.sysCatTypeName = resSCType.data.name;
              this.activeModel(res.data);
          });
        });
    } else {
      if (!this.formSearch.get('sysCatTypeId')) {
        this.app.warningMessage('sysCat.chooseSysCatType', '');
        return;
      }
      this.sysCatTypeService.findOne(this.formSearch.get('sysCatTypeId').value)
        .subscribe(resSCType => {
          const data = {
            sysCatTypeId  : resSCType.data.sysCatTypeId,
            sysCatTypeName: resSCType.data.name
          };
          this.activeModel(data);
      });
    }
  }

  private activeModel(data) {
    const modalRef = this.modalService.open(SysCatAddComponent, DEFAULT_MODAL_OPTIONS);
    modalRef.componentInstance.setFormValue(data);
    modalRef.result.then((result) => {
      if (!result) {
        return;
      }
      if (this.sysCatService.requestIsSuccess(result)) {
        this.processSearch(null);
      }
    });
  }

  /**
   * prepareDelete
   * param item
   */
  processDelete(item): void {
    if (item && item.sysCatId > 0) {
      this.app.confirmDelete(null, () => {// on accepted
        this.sysCatService.deleteById(item.sysCatId)
        .subscribe(res => {
          if (this.sysCatService.requestIsSuccess(res)) {
            this.processSearch(null);
          }
        });
      }, () => {// on rejected

      });
    }
  }
}
