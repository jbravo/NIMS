import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {TranslationModule} from 'angular-l10n';
import {InternationalPhoneModule} from 'ng4-intl-phone';
import {NgxSpinnerModule} from 'ngx-spinner';

import {ConfirmationService, DialogService, DynamicDialogConfig, DynamicDialogRef} from 'primeng/api';
import {InputTextModule} from 'primeng/inputtext';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {ContextMenuModule} from 'primeng/contextmenu';
import {StepsModule} from 'primeng/steps';
import {BreadcrumbModule} from 'primeng/breadcrumb';
import {TabMenuModule} from 'primeng/tabmenu';
import {ToolbarModule} from 'primeng/toolbar';
import {MenuModule} from 'primeng/menu';
import {
  AccordionModule,
  CalendarModule,
  CheckboxModule,
  DataTableModule,
  DropdownModule,
  FileUploadModule,
  InputTextareaModule,
  MultiSelectModule,
  PaginatorModule,
  PanelModule,
  RadioButtonModule,
  SplitButtonModule,
  TabViewModule
} from 'primeng/primeng';
import {AutoCompleteModule} from 'primeng/autocomplete';
import {PickListModule} from 'primeng/picklist';
import {TableModule} from 'primeng/table';
import {TreeModule} from 'primeng/tree';
import {TreeTableModule} from 'primeng/treetable';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {FieldsetModule} from 'primeng/fieldset';
import {OrganizationChartModule} from 'primeng/organizationchart';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import {TooltipModule} from 'primeng/tooltip';
import {DialogModule} from 'primeng/dialog';
import {ToastModule} from 'primeng/toast';
import {KeyFilterModule} from 'primeng/keyfilter';
import {CarouselModule} from 'primeng/carousel';
import {ProgressBarModule} from 'primeng/progressbar';
import {FormValidationModule} from '@app/shared/components/form-validation/form-validation.module';

import {BreadcrumbService} from '@app/shared/services/breadcrumb.service';
import {EventBusService} from '@app/shared/services/event-bus.service';
import {PropertyResolver} from './services/property.resolver';
import {BoldPipe} from './pipes/bold.pipe';
import {DisplayHelperPipe} from './pipes/display-helper.pipe';
import {InputSpecialDirective} from './directive/input-special.directive';
import {InputTrimDirective} from './directive/input-trim.directive';
import {RightClickCopyDirective} from './directive/right-click-copy.directive';
import {HasPermissionDirective} from '@app/shared/directive/has-permission.directive';
import {HasMultiPermissionDirective} from '@app/shared/directive/has-multi-permission.directive';
import {HasNotPermissionDirective} from '@app/shared/directive/has-not-permission.directive';
import {PositiveIntegerDirective} from '@app/shared/directive/positive-integer.directive';
import {DecimalThreeTenthsDirective} from '@app/shared/directive/decimal-three-tenths.directive';
import {NegativeDecimalFiveTenthsDirective} from './directive/negative-decimal-five-tenths.directive';
import {DecimalFiveTenthsDirective} from '@app/shared/directive/decimal-five-tenths.directive';
import {AutoFocusFieldDirective} from '@app/shared/directive/auto-focus-field.directive';

import {ControlMessagesComponent} from './components/control-messages/control-messages.component';
import {SpinnerComponent} from './components/spinner/spinner.component';
import {HavePermissionComponent} from './components/have-permission/have-permission.component';
import {HaveNotPermissionComponent} from './components/have-not-permission/have-not-permission.component';
import {OrgSelectorComponent} from './components/org-selector/org-selector.component';
import {OrgSelectorModalComponent} from './components/org-selector/org-selector-modal/org-selector-modal.component';
import {SelectFilterComponent} from './components/select-filter/select-filter.component';
import {DataPickerComponent} from './components/data-picker/data-picker.component';
import {DataPickerModalComponent} from './components/data-picker/data-picker-modal/data-picker-modal.component';
import {DatePickerComponent} from './components/date-picker/date-picker.component';
// import {OrgTreeComponent} from './components/org-tree/org-tree.component';
// import {LocationPickerComponent} from './components/location-picker/location-picker.component';
// import {FileChoserComponent} from './components/file-choser/file-choser.component';
import {DynamicInputComponent} from './components/dynamic-input/dynamic-input.component';
import {MultiLangInputComponent} from './components/multi-lang-input/multi-lang-input.component';
import {BaseComponent} from './components/base-component/base-component.component';
import {TranferLanguageComponent} from './components/tranfer-language/tranfer-language.component';
import {menuControlComponent} from './components/menu-control-component/menu-control.component';
// import {FileChooserComponent} from './components/file-chooser/file-chooser.component';
// import {WorkProcessBarComponent} from './components/work-process-bar/work-process-bar.component';
// import {LocationComponent} from './components/location/location.component';
import {PaginatorComponent} from './components/paginator/paginator.component';
import {Err404Component} from './components/err404/err404.component';
import {InputSearchControlComponent} from './components/input-search-control/input-search-control.component';
import {AutocompleteSearchControlComponent} from './components/autocomplete-search-control/autocomplete-search-control.component';
import {AutocompleteSearchStationModalComponent} from './components/autocomplete-search-control/autocomplete-search-station-modal/autocomplete-search-station-modal.component';
import {AutocompleteSearchDepartmentModalComponent} from './components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import {AutocompleteSearchLocationModalComponent} from './components/autocomplete-search-control/autocomplete-search-location-modal/autocomplete-search-location-modal.component';
import {AutocompleteSearchLaneModalComponent} from './components/autocomplete-search-control/autocomplete-search-lane-modal/autocomplete-search-lane-modal.component';
import {AutocompleteSearchLaneSleeveModalComponent} from './components/autocomplete-search-control/autocomplete-search-lane-sleeve-modal/autocomplete-search-lane-sleeve-modal.component';
import {AutocompleteSearchPillarModalComponent} from './components/autocomplete-search-control/autocomplete-search-pillar-modal/autocomplete-search-pillar-modal.component';
import {AutocompleteSearchPoolModalComponent} from './components/autocomplete-search-control/autocomplete-search-pool-modal/autocomplete-search-pool-modal.component';
// import {FileSelectorComponent} from '@app/shared/components/file-selector/file-selector.component';
// import {ImportFileControlComponent} from './components/import-file-control/import-file-control.component';
import {UploadControlComponent} from '@app/shared/components/upload-control/upload-control.component';
import {GeometryDirective} from '@app/shared/directive/geometry.directive';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    NgbModule.forRoot(), /* ng-bootstrap */
    TranslationModule,
    NgxSpinnerModule,
    FormValidationModule,
    /**
     * prime
     */
    InputTextModule,
    TableModule,
    PaginatorModule,
    ConfirmDialogModule,
    CalendarModule,
    ScrollPanelModule,
    StepsModule,
    BreadcrumbModule,
    TabMenuModule,
    DropdownModule,
    TabViewModule,
    FieldsetModule,
    ContextMenuModule,
    OrganizationChartModule,
    InternationalPhoneModule,
    OverlayPanelModule,
    TooltipModule,
    KeyFilterModule,
    CarouselModule,
    ProgressBarModule,
    CheckboxModule,
    RadioButtonModule,
    PaginatorModule,
    DataTableModule,
    FileUploadModule,
    DialogModule,
    TabViewModule,
    DropdownModule,
    InputTextareaModule,
    CalendarModule,
    MultiSelectModule,
    PickListModule,
    TreeModule,
    TreeTableModule,
    AutoCompleteModule,
    MessageModule,
    MessagesModule,
    ToolbarModule,
    MenuModule,
    ToastModule,
    AccordionModule,
    SplitButtonModule,
    PanelModule
  ],
  providers: [
    ConfirmationService,
    BreadcrumbService,
    PropertyResolver,
    EventBusService,
    DialogService,
    DynamicDialogConfig,
    DynamicDialogRef
  ],
  declarations: [
    ControlMessagesComponent,
    SpinnerComponent,
    HavePermissionComponent,
    HaveNotPermissionComponent,
    OrgSelectorComponent,
    OrgSelectorModalComponent,
    SelectFilterComponent,
    DataPickerComponent,
    DataPickerModalComponent,
    DatePickerComponent,
    TranferLanguageComponent,
    DisplayHelperPipe,
    DynamicInputComponent,
    InputSpecialDirective,
    InputTrimDirective,
    HasPermissionDirective,
    HasMultiPermissionDirective,
    HasNotPermissionDirective,
    PositiveIntegerDirective,
    DecimalThreeTenthsDirective,
    NegativeDecimalFiveTenthsDirective,
    DecimalFiveTenthsDirective,
    AutoFocusFieldDirective,
    MultiLangInputComponent,
    BaseComponent,
    BoldPipe,
    menuControlComponent,
    PaginatorComponent,
    Err404Component,
    RightClickCopyDirective,
    InputSearchControlComponent,
    AutocompleteSearchControlComponent,
    AutocompleteSearchStationModalComponent,
    AutocompleteSearchDepartmentModalComponent,
    AutocompleteSearchLocationModalComponent,
    AutocompleteSearchPillarModalComponent,
    AutocompleteSearchLaneModalComponent,
    AutocompleteSearchLaneSleeveModalComponent,
    AutocompleteSearchPoolModalComponent,
    UploadControlComponent,
    GeometryDirective
  ],
  exports: [
    /**
     * Shared module
     */
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    TranferLanguageComponent,
    NgbModule,
    FontAwesomeModule,
    TranslationModule,
    NgxSpinnerModule,
    FormValidationModule,
    /**
     * prime
     */
    InputTextModule,
    TableModule,
    PaginatorModule,
    ConfirmDialogModule,
    CalendarModule,
    ScrollPanelModule,
    StepsModule,
    BreadcrumbModule,
    TabMenuModule,
    DropdownModule,
    TabViewModule,
    FieldsetModule,
    ContextMenuModule,
    OrganizationChartModule,
    InternationalPhoneModule,
    TranslationModule,
    OverlayPanelModule,
    TooltipModule,
    KeyFilterModule,
    CarouselModule,
    ProgressBarModule,
    CheckboxModule,
    RadioButtonModule,
    PaginatorModule,
    DataTableModule,
    FileUploadModule,
    DialogModule,
    TabViewModule,
    DropdownModule,
    InputTextareaModule,
    CalendarModule,
    MultiSelectModule,
    PickListModule,
    TreeModule,
    TreeTableModule,
    AutoCompleteModule,
    MessageModule,
    MessagesModule,
    ToolbarModule,
    MenuModule,
    ToastModule,
    AccordionModule,
    SplitButtonModule,
    PanelModule,
    /**
     * Shared Component
     */
    HavePermissionComponent,
    HaveNotPermissionComponent,
    ControlMessagesComponent,
    SpinnerComponent,
    OrgSelectorComponent,
    SelectFilterComponent,
    DataPickerComponent,
    DatePickerComponent,
    DisplayHelperPipe,
    InternationalPhoneModule,
    DynamicInputComponent,
    InputSpecialDirective,
    InputTrimDirective,
    HasPermissionDirective,
    HasMultiPermissionDirective,
    HasNotPermissionDirective,
    PositiveIntegerDirective,
    RightClickCopyDirective,
    DecimalThreeTenthsDirective,
    NegativeDecimalFiveTenthsDirective,
    DecimalFiveTenthsDirective,
    AutoFocusFieldDirective,
    MultiLangInputComponent,
    BaseComponent,
    menuControlComponent,
    PaginatorComponent,
    Err404Component,
    InputSearchControlComponent,
    AutocompleteSearchControlComponent,
    AutocompleteSearchStationModalComponent,
    AutocompleteSearchDepartmentModalComponent,
    AutocompleteSearchLocationModalComponent,
    AutocompleteSearchLaneModalComponent,
    AutocompleteSearchLaneSleeveModalComponent,
    AutocompleteSearchPillarModalComponent,
    AutocompleteSearchPoolModalComponent,
    UploadControlComponent,
    GeometryDirective
  ],
  entryComponents: [
    OrgSelectorModalComponent,
    DataPickerModalComponent,
    AutocompleteSearchStationModalComponent,
    AutocompleteSearchDepartmentModalComponent,
    AutocompleteSearchLocationModalComponent,
    AutocompleteSearchPillarModalComponent,
    AutocompleteSearchLaneModalComponent,
    AutocompleteSearchLaneSleeveModalComponent,
    AutocompleteSearchPoolModalComponent
  ]
})
export class SharedModule {
}
