import {Component, OnInit} from '@angular/core';
// import {ICON_AVARTAR, UserToken} from "@app/core";
import {ContentLayoutComponent} from '@app/layouts/content-layout/content-layout.component';
import {MenuItem} from 'primeng/api';
import {TranslationService} from 'angular-l10n';
import {CommonParam} from '@app/core/app-common-param';
import {HrStorage} from '@app/core/services/auth/HrStorage';

@Component({
  selector: 'app-topbar',
  templateUrl: './app.topbar.component.html'
})
export class AppTopBarComponent implements OnInit {

  items: MenuItem[];
  languages: any[] = [
    {img: 'assets/img/nims/lang_vn.jpg', language: 'vi', label: this.trans.translate('language.vi.label')},
    {img: 'assets/img/nims/lang-en.jpg', language: 'en', label: this.trans.translate('language.en.label')}
  ];
  language;
  hrStorage = HrStorage;
  constructor(
    public app: ContentLayoutComponent,
    private trans: TranslationService,
    private commonParam: CommonParam
  ) {
    this.language = this.languages[0];
  }

  ngOnInit() {
    this.items = [
      {
        label: 'Thêm mới nhà trạm', icon: 'assets/img/nims/tool_add.png', command: (event) => {
          document.body.style.cursor = 'url("../assets/img/nims/tool_add.png"), default';
          document.getElementById('map').style.cursor = 'url("../assets/img/nims/tool_add.png"), default';
          this.addItem('station');
        }
      }
    ];
  }

  addItem(item: string) {
    this.commonParam.addItem(item);
  }

  onSelected(val) {
    this.language = val;
    this.app.selectLanguage(val.language);
    if (val == 1 && document.getElementById('textLeft') != null) {
      document.getElementById('textLeft').innerHTML = 'Hiển thị';
      document.getElementById('textRight').innerHTML = 'Bản ghi';
    } else if (val == 2 && document.getElementById('textLeft') != null) {
      document.getElementById('textLeft').innerHTML = 'Show';
      document.getElementById('textRight').innerHTML = 'Record';
    }
  }

  //
  // logout() {
  //   CommonUtils.logoutAction();
  // }
}
