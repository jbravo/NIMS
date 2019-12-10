import {Component, OnInit, EventEmitter, Output} from '@angular/core';
import {APP_CONSTANTS} from '@app/core';

@Component({
  selector: 'paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.css']
})
export class PaginatorComponent implements OnInit {

  @Output()
  public onChange: EventEmitter<any> = new EventEmitter<any>();

  first: number;
  last: number;
  maxPage: number;
  rows: number;
  totalRecords: number;
  rowsPerPageItems: any[];
  pageLinks: any[] = [];
  page: number;

  constructor() {
  }

  ngOnInit() {
    this.maxPage = APP_CONSTANTS.PAGE_DEFAULT.PAGE;
    this.rows = APP_CONSTANTS.PAGE_DEFAULT.ROWS;
    this.rowsPerPageItems = APP_CONSTANTS.ROWS_PER_PAGE_ITEMS;
    this.page = APP_CONSTANTS.PAGE_DEFAULT.PAGE_CHOOSE;
    this.totalRecords = APP_CONSTANTS.PAGE_DEFAULT.TOTAL_RECORDS;
    this.calculatePageLinks();
  }

  // thay doi so luong ban ghi tren 1 trang
  onRppChange(event) {
    if (event && event.value) {
      this.rows = event.value;
      this.calculatePageLinks();
    }
    this.onChangePage();
  }

  // back trang
  changePageToPrev(event) {
    this.page = this.page - 1;
    this.onChangePage();
  }

  // next trang
  changePageToNext(event) {
    this.page = this.page + 1;
    this.onChangePage();
  }

  // chon trang
  onPageLinkClick(event, value) {
    this.page = value;
    this.onChangePage();
  }

  // lay vi tri trang check css
  getPage() {
    return this.page;
  }

  // tinh so luong trang
  calculatePageLinks() {
    const totalPage = Math.ceil(this.totalRecords / this.rows) || 1;
    this.maxPage = totalPage - 1;
    if (this.totalRecords == 0) {
      this.first = 0;
    }
    if (totalPage == 1 && this.totalRecords > 0) {
      this.first = 1;
      this.last = this.totalRecords;
    } else if (totalPage == this.page + 1) {
      this.last = this.totalRecords;
    } else {
      this.first = this.first == 0 ? (this.first + 1) : this.first;
      if (this.first == 1) {
        this.last = this.rows;
      } else {
        this.last = this.first + this.rows;
      }
    }
    this.pageLinks = [];
    for (let i = 1; i <= totalPage; i++) {
      this.pageLinks.push(i);
    }
  }

  // tra ve ket qua cho man hinh duoc khai bao paginator moi khi thay doi
  onChangePage() {
    this.first = this.rows * this.page;
    this.onChange.emit({rows: this.rows, first: this.first});
  }
}
