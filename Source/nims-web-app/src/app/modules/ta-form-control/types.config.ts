import {ConfigOption} from '@ngx-formly/core';
import {InputTypeComponent} from '@app/modules/ta-form-control/types/input-type/input-type.component';
import {SelectTypeComponent} from '@app/modules/ta-form-control/types/select-type/select-type.component';
import {CalendarTypeComponent} from '@app/modules/ta-form-control/types/calendar-type/calendar-type.component';
import {TextareaTypeComponent} from '@app/modules/ta-form-control/types/textarea-type/textarea-type.component';
import { InputTreeTypeComponent } from './types/input-tree-type/input-tree-type.component';

export const config: ConfigOption = {
  types: [
    {name: 'input', component: InputTypeComponent},
    {name: 'select', component: SelectTypeComponent},
    {name: 'calendar', component: CalendarTypeComponent},
    {name: 'textareaT', component: TextareaTypeComponent},
    {name: 'inputTree', component: InputTreeTypeComponent},
  ],
  validationMessages: [
    {name: 'required', message: 'Bắt buộc nhập'},
  ],
};
