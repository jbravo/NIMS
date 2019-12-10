import { FormControl } from '@angular/forms';
import { Component, OnInit, Input, ViewChildren, AfterViewInit, Output, EventEmitter, OnChanges } from '@angular/core';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OrgSelectorModalComponent } from './org-selector-modal/org-selector-modal.component';
import { DEFAULT_MODAL_OPTIONS } from '@app/core/app-config';
import { OrganizationService } from '@app/core';
import { AppComponent } from '@app/app.component';

@Component({
  selector: 'org-selector',
  templateUrl: './org-selector.component.html',
})
export class OrgSelectorComponent implements OnInit, AfterViewInit, OnChanges {
  @Input()
  public property: FormControl;
  @Input()
  public action: FormControl;
  @Input()
  public disabled: boolean = false;
  @Input()
  public isRequiredField = false;
  @Input()
  public operationKey: string;
  @Input()
  public adResourceKey: string;
  @Input()
  public filterCondition: string;
  @Output()
  public onChange: EventEmitter<any> = new EventEmitter<any>();

  @ViewChildren('displayName')
  public displayName;
  @ViewChildren('buttonChose')
  public buttonChose;

  constructor(
    public app: AppComponent,
    private service: OrganizationService
    , private modalService: NgbModal
  ) {
  }

  /**
   * ngOnInit
   */
  ngOnInit() {
  }
  /**
   * ngAfterViewInit
   */
  ngAfterViewInit() {
    this.onChangeOrgId();
  }
  /**
   * delete
   */
  delete() {
    this.property.setValue('');
    this.onChangeOrgId();
  }
  /**
   * onChange orgId then load org name
   */
  public onChangeOrgId() {
    if (CommonUtils.isNullOrEmpty(this.property.value)) {
      if (this.displayName) {
        this.displayName.first.nativeElement.value = '';
      }
      return;
    }
    // thuc hien lay ten de hien thi
    const params = {
      id:this.property.value
    }
    this.service.findOne(params)
        .subscribe((res) => {
          const data = res.data;
          if (data != null) {
            this.displayName.first.nativeElement.value = data.name;
          }else{
            this.displayName.first.nativeElement.value = '';
          }
        });
  }
  /**
   * ngOnChanges
   */
  ngOnChanges() {
    this.onChangeOrgId();
  }
  /**
   * onFocus
   */
  public onFocus() {
    this.buttonChose.first.nativeElement.focus();
    this.buttonChose.first.nativeElement.click();
  }
  /**
   * onChose
   */
  public onChose() {
    const modalRef = this.modalService.open(OrgSelectorModalComponent, DEFAULT_MODAL_OPTIONS);
    modalRef
      .componentInstance
      .setInitValue({operationKey: this.operationKey, adResourceKey: this.adResourceKey, filterCondition: this.filterCondition});

    modalRef.result.then((node) => {
      if (!node) {
        return;
      }

      // this.property.setValue(node.organizationId);
      this.displayName.first.nativeElement.value = node.name;
      // callback on chose item
      this.onChange.emit(node);
    });
    
    modalRef.componentInstance.message.subscribe(($e) => {
      switch ($e.code) {
        case 'ERROR_1': {
          this.app.errorMessage('message.error.dateError_1');
          break;
        }
        case 'ERROR_2': {
          this.app.errorMessage('message.error.dateError_2');
          break;
        }
        case 'ERROR_3': {
          this.app.errorMessage('message.error.dateError_3');
          break;
        }
        case 'ERROR_4': {
          this.app.errorMessage('message.error.dateError_4');
          break;
        }
        case 'ERROR_5': {
          this.app.errorMessage('message.error.dateError_5');
          break;
        }
        case 'ERROR_6': {
          this.app.errorMessage('message.error.dateError_6');
          break;
        }
        default:{
          break;
        }
      }
    });

  }

}
