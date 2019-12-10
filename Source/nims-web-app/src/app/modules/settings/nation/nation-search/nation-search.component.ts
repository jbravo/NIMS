import { CommonUtils } from '@app/shared/services/common-utils.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NationService } from '@app/core';
import { NationFormComponent } from '../nation-form/nation-form.component';
import { DEFAULT_MODAL_OPTIONS } from '@app/core/app-config';
import { ValidationService } from '@app/shared/services';
import { AppComponent } from '@app/app.component';

@Component({
    selector: 'nation-search',
    templateUrl: './nation-search.component.html'
})

export class NationSearchComponent implements OnInit {
  resultList: any = {};
  credentials: any = {};
  formSearch: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private modalService: NgbModal,
              private nationService: NationService,
              private app: AppComponent) {
      this.buildForm();
  }
  ngOnInit() {
    this.processSearch(null);
  }

  get f () {
    return this.formSearch.controls;
  }

  private buildForm(): void {
    this.formSearch = this.formBuilder.group({
      code: ['', Validators.compose([Validators.maxLength(50)])],
      name: ['', Validators.compose([Validators.maxLength(200)])],
      isDefault: [''],
      requirePersionalId: [''],
      description: ['', Validators.compose([Validators.maxLength(1000)])],
      phoneAreaCode: ['', Validators.compose([Validators.maxLength(50)])],
    });
  }

  // Tìm kiếm nation
  processSearch(event) {
    if (!CommonUtils.isValidForm(this.formSearch)) {
      return;
    }
    this.nationService.search(this.formSearch.value, event).subscribe(res => {
      this.resultList = res;
    });
  }

  // Thêm mới hoặc update nation
  prepareInsertOrUpdate(item) {
    const modalRef = this.modalService.open(NationFormComponent, DEFAULT_MODAL_OPTIONS);
    if (item && item.nationId > 0) {
      this.nationService.findOne(item.nationId)
        .subscribe(res => {
          modalRef.componentInstance.setFormValue(res.data);
        });
    }

    modalRef.result.then((result) => {
      if (!result) {
        return;
      }
      if (this.nationService.requestIsSuccess(result)) {
        this.processSearch(null);
      }
    });
  }

  processDelete(item) {
    if (item && item.nationId > 0) {
      this.app.confirmDelete(null, () => {
        // on accepted
        this.nationService.deleteById(item.nationId)
        .subscribe(
          res => {
            if (this.nationService.requestIsSuccess(res)) {
              this.processSearch(null);
            }
          },
          error => {
            this.app.requestIsError();
          },
          () => {
            // No errors, route to new page
          }
        );
      }, () => {// on rejected

      });
    }
  }
}
