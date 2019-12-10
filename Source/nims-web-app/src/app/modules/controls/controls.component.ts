import {Component, OnInit} from '@angular/core';
import {SelectItemGroup} from "@app/modules/controls/common/selectitemgroup";
import {SelectItem} from "@app/modules/controls/common/selectitem";
import {CountryService} from "@app/modules/controls/countryservice";
import {CarService} from "@app/modules/controls/carservice";
import {Car} from "@app/modules/controls/common/car";
import {Message, TreeNode} from "primeng/api";
import {NodeService} from "@app/modules/controls/common/nodeservice";
import {MessageService} from "@app/modules/controls/common/messageservice";
import {ConfirmationService} from "@app/modules/controls/common/confirmationservice";


interface City {
  name: string,
  code: string
}

@Component({
  selector: 'controls',
  templateUrl: './controls.component.html',
  styleUrls: ['./controls.component.css']
})
export class ControlsComponent implements OnInit {

  text: string;
  groupedCars: SelectItemGroup[];
  items: SelectItem[];
  disabled: boolean = true;
  cities: City[];

  selectedCity: City;

  cars: SelectItem[];

  country: any;

  countries: any[];
  sourceCars: Car[];

  targetCars: Car[];
  filesTree2: TreeNode[];
  filesTree4: TreeNode[];
  filesTree11: TreeNode[];
  filteredCountriesSingle: any[];

  filteredCountriesMultiple: any[];

  brands: string[] = ['Audi','BMW','Fiat','Ford','Honda','Jaguar','Mercedes','Renault','Volvo','VW'];

  filteredBrands: any[];

  brand: string;

  files: TreeNode[];

  cols: any[];

  msgs: Message[] = [];

  cars1: Car[];

  cars2: Car[];

  selectedCar1: Car;

  selectedCar2: Car;

  display: boolean = false;

  constructor(private countryService: CountryService,
              private carService: CarService,
              private nodeService: NodeService,
              private messageService: MessageService,
              private confirmationService: ConfirmationService) {
  }

  ngOnInit() {

    this.carService.getCarsSmall().then(cars => this.sourceCars = cars);
    this.targetCars = [];
    this.getDataDropDown();

    this.nodeService.getFiles().then(files => this.filesTree2 = files);
    this.nodeService.getFiles().then(files => this.filesTree4 = files);
    this.nodeService.getFiles().then(files => this.filesTree11 = files);
    this.files = [];
    for(let i = 0; i < 50; i++) {
      let node = {
        data:{
          name: 'Item ' + i,
          size: Math.floor(Math.random() * 1000) + 1 + 'kb',
          type: 'Type ' + i
        },
        children: [
          {
            data: {
              name: 'Item ' + i + ' - 0',
              size: Math.floor(Math.random() * 1000) + 1 + 'kb',
              type: 'Type ' + i
            }
          }
        ]
      };

      this.files.push(node);
    }

    this.cols = [
      { field: 'name', header: 'Name' },
      { field: 'size', header: 'Size' },
      { field: 'type', header: 'Type' }
    ];

    this.carService.getCarsMedium().then(cars => this.cars1 = cars);
    this.carService.getCarsMedium().then(cars => this.cars2 = cars);

  }

  toggleDisabled() {
    this.disabled = !this.disabled;
  }

  getDataDropDown() {
    this.items = [];
    for (let i = 0; i < 10000; i++) {
      this.items.push({label: 'Item ' + i, value: 'Item ' + i});
    }

    this.cities = [
      {name: 'New York', code: 'NY'},
      {name: 'Rome', code: 'RM'},
      {name: 'London', code: 'LDN'},
      {name: 'Istanbul', code: 'IST'},
      {name: 'Paris', code: 'PRS'}
    ];

    this.cars = [
      {label: 'Audi', value: 'Audi'},
      {label: 'BMW', value: 'BMW'},
      {label: 'Fiat', value: 'Fiat'},
      {label: 'Ford', value: 'Ford'},
      {label: 'Honda', value: 'Honda'},
      {label: 'Jaguar', value: 'Jaguar'},
      {label: 'Mercedes', value: 'Mercedes'},
      {label: 'Renault', value: 'Renault'},
      {label: 'VW', value: 'VW'},
      {label: 'Volvo', value: 'Volvo'}
    ];

    this.groupedCars = [
      {
        label: 'Germany', value: 'germany.png',
        items: [
          {label: 'Audi', value: 'Audi'},
          {label: 'BMW', value: 'BMW'},
          {label: 'Mercedes', value: 'Mercedes'},
          {label: 'Murcia', value: 'Murcia'}
        ]
      },
      {
        label: 'USA', value: 'usa.png',
        items: [
          {label: 'Cadillac', value: 'Cadillac'},
          {label: 'Ford', value: 'Ford'},
          {label: 'GMC', value: 'GMC'}
        ]
      },
      {
        label: 'Japan', value: 'japan.png',
        items: [
          {label: 'Honda', value: 'Honda'},
          {label: 'Mazda', value: 'Mazda'},
          {label: 'Toyota', value: 'Toyota'}
        ]
      }
    ];
  }
  filterCountrySingle(event) {
    let query = event.query;
    this.countryService.getCountries().then(countries => {
      this.filteredCountriesSingle = this.filterCountry(query, countries);
    });
  }

  filterCountryMultiple(event) {
    let query = event.query;
    this.countryService.getCountries().then(countries => {
      this.filteredCountriesMultiple = this.filterCountry(query, countries);
    });
  }

  filterCountry(query, countries: any[]):any[] {
    //in a real application, make a request to a remote url with the query and return filtered results, for demo we filter at client side
    let filtered : any[] = [];
    for(let i = 0; i < countries.length; i++) {
      let country = countries[i];
      if(country.name.toLowerCase().indexOf(query.toLowerCase()) == 0) {
        filtered.push(country);
      }
    }
    return filtered;
  }

  filterBrands(event) {
    this.filteredBrands = [];
    for(let i = 0; i < this.brands.length; i++) {
      let brand = this.brands[i];
      if(brand.toLowerCase().indexOf(event.query.toLowerCase()) == 0) {
        this.filteredBrands.push(brand);
      }
    }
  }

  showSuccess() {
    this.msgs = [];
    this.msgs.push({severity:'success', summary:'Success Message', detail:'Order submitted'});
  }

  showInfo() {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:'Info Message', detail:'PrimeNG rocks'});
  }

  showWarn() {
    this.msgs = [];
    this.msgs.push({severity:'warn', summary:'Warn Message', detail:'There are unsaved changes'});
  }

  showError() {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:'Error Message', detail:'Validation failed'});
  }

  showMultiple() {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:'Message 1', detail:'PrimeNG rocks'});
    this.msgs.push({severity:'info', summary:'Message 2', detail:'PrimeUI rocks'});
    this.msgs.push({severity:'info', summary:'Message 3', detail:'PrimeFaces rocks'});
  }

  showViaService() {
    this.messageService.add({severity:'success', summary:'Service Message', detail:'Via MessageService'});
  }

  clear() {
    this.msgs = [];
  }


  confirm1() {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to proceed?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.msgs = [{severity:'info', summary:'Confirmed', detail:'You have accepted'}];
      },
      reject: () => {
        this.msgs = [{severity:'info', summary:'Rejected', detail:'You have rejected'}];
      }
    });
  }

  confirm2() {
    this.confirmationService.confirm({
      message: 'Do you want to delete this record?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      accept: () => {
        this.msgs = [{severity:'info', summary:'Confirmed', detail:'Record deleted'}];
      },
      reject: () => {
        this.msgs = [{severity:'info', summary:'Rejected', detail:'You have rejected'}];
      }
    });
  }

  showDialog() {
    this.display = true;
  }

}
