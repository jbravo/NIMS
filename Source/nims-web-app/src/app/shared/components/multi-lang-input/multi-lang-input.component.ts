import { LanguageBean } from './../../../core/models/language.model';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { SysPropertyDetailBean } from '@app/core/models/sys-property-details.model';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { LanguageService } from '@app/core/services/hr-system/language.service';

@Component({
  selector: 'multi-lang-input',
  templateUrl: './multi-lang-input.component.html',
  styles: []
})
export class MultiLangInputComponent implements OnInit {
  @Input()
  public property: SysPropertyDetailBean;
  @Input()
  public objectId: string;
  @Output()
  public onClose = new EventEmitter<boolean>();
  langs: any = [];
  langForm: FormGroup;
  objLang: LanguageBean;
  constructor(private languageService: LanguageService,
              private formBuilder: FormBuilder) {
    this.langForm = this.formBuilder.group({});
  }

  ngOnInit() {
    this.objLang = {
      columnName: this.property.columnName,
      tableName: this.property.tableName,
      objectId: parseInt(this.objectId)
    };
    this.languageService.findInfo(this.objLang).subscribe(res => {
      this.langs = res.data;
    }, (err) => {}
    , () => {
      for (const lang of this.langs) {
        this.langForm.addControl(lang.languageCode, new FormControl(''));
      }
    });
  }
  public save(): void {
    for (const lang of this.langs) {
      lang.value = this.langForm.get(lang.languageCode).value;
    }
    this.languageService.saveInfo(this.langs).subscribe(res => {
      if (this.languageService.requestIsSuccess(res)) {
        this.onClose.emit(true);
      }
    });
  }
  public close(): void {
    this.onClose.emit(true);
  }
}
