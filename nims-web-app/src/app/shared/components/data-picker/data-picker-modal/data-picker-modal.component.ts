import { Component, OnInit, Input } from '@angular/core';
import { DataPickerService } from '@app/core';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-data-picker-modal',
  templateUrl: './data-picker-modal.component.html',
})
export class DataPickerModalComponent implements OnInit {
  @Input()
  params: any;
  form: FormGroup;
  resultList: any = {};
  l10nCodeField: string;
  l10nNameField: string;
  titleChose: string;
  placeholder: string;

  constructor(
      private service: DataPickerService
    , private formBuilder: FormBuilder
    , public activeModal: NgbActiveModal) {
      this.buildForm();
    }

  ngOnInit() {

  }
  /**
   * set init value
   */
  setInitValue(params: {
        operationKey: ''
      , adResourceKey: ''
      , filterCondition: ''
      , objectBO: ''
      , codeField: ''
      , nameField: ''
      , orderField: ''
      , selectField: ''
    }) {
    this.params = params;
    this.titleChose = params.objectBO + '.dataPickerTitle';
    this.placeholder = params.objectBO + '.dataPickerPlaceholder';
    this.l10nCodeField = params.objectBO + '.' + params.codeField;
    this.l10nNameField = params.objectBO + '.' + params.nameField;
    this.processSearch(null);
  }
  /**
   * buildForm
   */
  private buildForm(): void {
    this.form = this.formBuilder.group({
      codeInput: [''],
      nationId: [CommonUtils.getNationId()],
    });
  }
  /**
   * action init ajax
   */
  private actionInitAjax() {
    this.service.actionInitAjax(CommonUtils.getNationId(), this.params)
        .subscribe((res) => {
        });
  }
  /**
   * processSearch
   * @ param event
   */
  public processSearch(event) {
    if (CommonUtils.isValidForm(this.form)) {
      const params = this.form.value;
      Object.keys(this.params).forEach(key => {
        params[key] = this.params[key] || '';
      });
      this.service.search(params, event).subscribe(res => {
        this.resultList = res;
      });
    }
  }
  /**
se
   * @ param item
   */
  public chose(item) {
    this.activeModal.close(item);
  }
}
