import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TestUiComponent} from '@app/modules/ta-form-control/test-ui/test-ui.component';

const routes: Routes = [
    {path: 'test-ui', component: TestUiComponent}
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TaFormControlRoutingModule {
}
