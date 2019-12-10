import {Directive, ElementRef, Input, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {CommonUtils} from '@app/shared/services';

@Directive({
  selector: '[hasNotPermission]'
})
export class HasNotPermissionDirective implements OnInit {
  private _value: any;

  constructor(
    private element: ElementRef,
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) {

  }

  @Input()
  set hasNotPermission(value) {
    this._value = value;
    this.updateView(this._value);
  }

  private updateView(value) {
    if (!CommonUtils.havePermission(value[0], value[1])) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }

  ngOnInit(): void {
  }
}
