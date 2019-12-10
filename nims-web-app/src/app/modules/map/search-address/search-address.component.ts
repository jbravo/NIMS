import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CommonParam} from "@app/core/app-common-param";
import { OpenStreetMapProvider } from 'leaflet-geosearch';

@Component({
  selector: 'search-address',
  templateUrl: './search-address.component.html',
  styleUrls: ['./search-address.component.css']
})
export class SearchAddressComponent implements OnInit {

  formSearch: FormGroup;
  provider =  new OpenStreetMapProvider();
  result: any[] = [];
  constructor(private fb: FormBuilder,
              private commonParam: CommonParam) {
    this.buildForm();
  }

  ngOnInit() {
  }

  search(){
    let component = this;
    component.provider.search({query: this.formSearch.controls['address'].value}).then(function (result) {
      console.log(result);
      component.result = result;
    });
  }

  onClosed(){
   this.commonParam.closePopupLeft();
  }

  buildForm(){
    this.formSearch = this.fb.group({
      address: [null,Validators.required]
    });
  }

  locate(long,lat){
    this.commonParam.locateFunction(long,lat);
  }

}
