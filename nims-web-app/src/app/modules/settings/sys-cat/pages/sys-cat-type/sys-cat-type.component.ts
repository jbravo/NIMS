import { SysCatTypeAddComponent } from './../sys-cat-type-add/sys-cat-type-add.component';
import { AppComponent } from '@app/app.component';
import { SysCatSearchComponent } from './../sys-cat-search/sys-cat-search.component';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Component, OnInit, Input, Pipe, PipeTransform } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SysCatTypeService, NationService } from '@app/core';
import { DEFAULT_MODAL_OPTIONS } from '@app/core/app-config';
import { SysCatTypeTranferComponent } from '../sys-cat-type-tranfer/sys-cat-type-tranfer.component';

@Component({
  selector: 'app-sys-cat-type',
  templateUrl: './sys-cat-type.component.html'
})
export class SysCatTypeComponent implements OnInit {

  @Input()
  sysCatSearchComp: SysCatSearchComponent;
  resultList = [];
  formSave: FormGroup;
  scTypeFillter = '';
  scTypeId: number;

  constructor(
    private formBuilder: FormBuilder,
    private modalService: NgbModal,
    private sysCatTypeService: SysCatTypeService,
    private nationService: NationService,
    private app: AppComponent
  ) {
  }

  ngOnInit() {
    this.processSearch();
  }

  processSearch(): void {
    this.nationService.findSysCatTypes().subscribe(res => {
      this.resultList = res.data;
    });
  }

  prepareSaveOrUpdate(item): void {
    if (item && item.sysCatTypeId > 0) {
      this.sysCatTypeService.findOne(item.sysCatTypeId)
        .subscribe(res => {
          this.activeModelSave(res.data);
      });
    } else {
      this.activeModelSave();
    }
  }

  onSelectSysCatType(item): void {
    this.scTypeId = item.sysCatTypeId;
    this.sysCatSearchComp.formSearch.removeControl('sysCatTypeId');
    this.sysCatSearchComp.formSearch.addControl('sysCatTypeId', new FormControl(item.sysCatTypeId));
    this.sysCatSearchComp.processSearch(null);
  }

  prepareTransfer(item): void {
    if (item && item.sysCatTypeId > 0) {
      this.sysCatTypeService.findOne(item.sysCatTypeId)
        .subscribe(res => {
          const modalRef = this.modalService.open(SysCatTypeTranferComponent, DEFAULT_MODAL_OPTIONS);
          modalRef.componentInstance.setFormValue(res.data);
          modalRef.result.then((result) => {
            if (!result) {
              return;
            }
            if (this.sysCatTypeService.requestIsSuccess(result)) {
              this.processSearch();
            }
          });
        });
    }
  }

  private activeModelSave(data?: any) {
    const modalRef = this.modalService.open(SysCatTypeAddComponent, DEFAULT_MODAL_OPTIONS);
    if (data) {
      modalRef.componentInstance.setFormValue(data);
    }
    modalRef.result.then((result) => {
      if (!result) {
        return;
      }
      if (this.sysCatTypeService.requestIsSuccess(result)) {
        this.processSearch();
      }
    });
  }
}

@Pipe({ name: 'filter' })
export class FilterPipe implements PipeTransform {
  transform(items: any[], searchText: string): any[] {
    if (!items) { return []; }
    if (!searchText) { return items; }
    searchText = searchText.toLowerCase();
    return items.filter( it => {
      return it.name.toLowerCase().includes(searchText);
    });
   }
}
