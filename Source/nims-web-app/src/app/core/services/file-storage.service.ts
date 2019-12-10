import {Observable} from 'rxjs/Observable';
import {BasicService} from './basic.service';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HelperService} from '@app/shared/services/helper.service';

@Injectable({
  providedIn: 'root'
})
export class FileStorageService extends BasicService {


  constructor(public httpClient: HttpClient, public helperService: HelperService) {
    super('file', 'fileStorage', httpClient, helperService);
  }

  /**
   * deleteFile
   * param id
   */
  public deleteFile(id: string): Observable<any> {
    const url = `${this.serviceUrl}/delete/${id}`;
    return this.deleteRequest(url);
  }

  /**
   * downloadFile
   * param id
   */
  public downloadFile(id: string): Observable<any> {
    const url = `${this.serviceUrl}/download/${id}`;
    return this.getRequest(url, {responseType: 'blob'});
  }

  /**
   * uploadFile
   * @param fileType
   * @param objectId
   * @param file
   */
  public uploadFile(fileType: number, objectId: number, file: any): Observable<any> {
    const url = `${this.serviceUrl}/${fileType}/append/${objectId}?`;

    let fileData = new FormData();
    fileData.append('file', file);

    return this.postRequest(url, fileData, {
      reportProgress: true,
      responseType: 'text'
    });
  }

  /**
   * uploadFile
   * @param file
   */
  public uploadFile2(file: any): Observable<any> {
    return this.uploadFile(1, 1, file);
  }

  /**
   * uploadFile
   * @param fileType
   * @param objectId
   *
   */
  public uploadOrReplaceFile(fileType: number, objectId: number, file: any): Observable<any> {
    const url = `${this.serviceUrl}/${fileType}/save-or-update/${objectId}?`;

    let fileData = new FormData();
    fileData.append('file', file);

    return this.postRequest(url, fileData, {
      reportProgress: true,
      responseType: 'text'
    });
  }
}
