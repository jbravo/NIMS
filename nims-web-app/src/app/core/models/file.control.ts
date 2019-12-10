import { FormControl } from '@angular/forms';
class FileAttachment {
  id: string;
  fileName: string;
  length: number;
  chunkSize: number;
  uploadDate: number;
}
export class FileControl extends FormControl {
  public fileAttachment: Array<FileAttachment>;
  public setFileAttachment(fileAttachment: any) {
    this.fileAttachment = fileAttachment;
  }
  public getFileAttachment(): Array<FileAttachment> {
    return this.fileAttachment;
  }
}
