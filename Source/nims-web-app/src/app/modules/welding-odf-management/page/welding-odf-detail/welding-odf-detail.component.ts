import { Component, OnInit, HostListener, ElementRef, ViewChild } from '@angular/core';
import { WeldingOdfService } from '../../service/welding-odf.service';
import { TranslationService } from 'angular-l10n';
import { TabLayoutService } from '@app/layouts/tab-layout';

@Component({
  selector: 'welding-odf-detail',
  templateUrl: './welding-odf-detail.component.html',
  styleUrls: ['./welding-odf-detail.component.css']
})
export class WeldingOdfDetailComponent implements OnInit {
  item: any;
  @ViewChild('tabindex') tab: ElementRef;

  constructor(private weldingOdfService: WeldingOdfService, private translation: TranslationService,
    private tabLayoutService: TabLayoutService) {
  }
  ngOnInit() {
    this.item = this.weldingOdfService.item;
  }

  onClosed() {
    this.tabLayoutService.close({
      component: 'WeldingOdfDetailComponent',
      header: '',
      action: 'view',
      tabId: Number.parseFloat(this.item.odfId + '.' + this.item.couplerNo),
    });
    this.tabLayoutService.open({
      component: 'ODFViewComponent',
      header: 'odf.tab.detail',
      tabId: (this.item.odfId * 121),
      breadcrumb: [
        { label: this.translation.translate('odf.tab.list') },
        {
          label: this.translation.translate('odf.tab.detail')
        },
        {
          label: this.translation.translate('odf.info.weldingOdf')
        }
      ],
    });
  }

  updateTab(item) {
    this.weldingOdfService.action = 'edit';
    this.tabLayoutService.open({
      component: 'WeldingOdfUpdateComponent',
      header: 'welding.odf.update.label',
      action: 'update',
      tabId: Number.parseFloat(item.odfId + '.' + item.couplerNo),
      breadcrumb: [
        { label: this.translation.translate('weldingOdf.tab') },
        { label: this.translation.translate('welding.odf.update.label') },
      ]
    });
    this.weldingOdfService.item = this.item;
  }

  // @HostListener('document:keydown', ['$event'])
  // enterToOpenUpdateTab(event: KeyboardEvent): void {
  //   debugger
  //   if (event.code=== 'Enter') {
  //     this.updateTab(this.item);
  //     event.stopPropagation();
  //   }
  // }
}
