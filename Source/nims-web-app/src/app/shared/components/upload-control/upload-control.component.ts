import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {FormControl} from '@angular/forms';
import {CommonUtils} from '@app/shared/services';
import {AppComponent} from '@app/app.component';
import {FileStorageService} from '@app/core/services/file-storage.service';
import {TranslationService} from 'angular-l10n';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'upload-control',
  templateUrl: './upload-control.component.html',
  styleUrls: ['./upload-control.component.css']
})
export class UploadControlComponent implements OnInit {
  @ViewChild('inputFile') inputFile: ElementRef;
  @Input() property: FormControl;
  @Input() multiple = false;
  @Input() accept: string;
  @Input() validMaxSize = 200; // MB
  @Input() fileId: string;
  @Input() isButton = false;
  @Output() onFileChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() onReset = new EventEmitter();
  file: File;
  files: any[];
  fileName: string;

  constructor(
    private app: AppComponent,
    private fileStorage: FileStorageService,
    private translation: TranslationService,
    private messageService: MessageService
  ) {
  }

  set _file(file) {
    if (file) {
      this.file = file;
    }
  }

  setEmptyFile() {
    if (this.inputFile.nativeElement) {
      this.inputFile.nativeElement.value = '';
    }

    this.ngOnInit();
  }

  ngOnInit() {
    this.file = null;
  }

  public onChange(event) {
    this.files = event.target.files;
    if (this.files && this.files.length > 0) {
      const files = [];
      for (let i = 0; i < this.files.length; i++) {
        const file = this.files[i];
        if (this.isValidFile(file)) {
          files.push(file);
        }
      }
      if (files.length > 0) {
        this.files = files;
        this.file = this.files[0];
        // this.onFileChange.emit(this.file);
        this.onChangeFile();
      } else {
        this.inputFile.nativeElement.value = '';
      }

    }
  }

  public isValidFile(files): boolean {
    if (!files) {
      return true;
    }
    if (this.validMaxSize > 0) {
      if (CommonUtils.tctGetFileSize(files) > this.validMaxSize) {
        this.messageService.add({
          severity: 'error',
          summary: '',
          detail: this.translation.translate('controls.maxFileSize', {max: this.validMaxSize})
        });
        return false;
      }
    }
    if (this.accept) {
      const fileName = files.name;
      const temp = fileName.split('.');
      const ext = temp[temp.length - 1].toLowerCase();
      const ruleFile = ',' + this.accept;
      if (ruleFile.toLowerCase().indexOf(ext) === -1) {
        this.messageService.add({
          key : 'errorFile',
          severity: 'warn',
          summary: '',
          detail: this.translation.translate('station.import.error.file.type')
        })
        ;
        return false;
      }
    }
    return true;
  }

  public onChangeFile() {
    this.onFileChange.emit(this.file);
    if ((this.files && this.files.length > 0)) {
      this.fileName = '';
    } else {
      this.fileName = '';
    }
  }

  public delete() {
    this.inputFile.nativeElement.value = '';
    this.ngOnInit();
  }

  public onFocus() {
    this.inputFile.nativeElement.focus();
  }


}


