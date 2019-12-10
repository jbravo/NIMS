import {ComponentFactoryResolver, Directive, Input, OnChanges, SimpleChanges, Type, ViewContainerRef} from '@angular/core';
import {MapSidebar} from '@app/modules/map/map-sidebar';

@Directive({
  selector: '[mapSidebar]'
})
export class MapSidebarDirective implements OnChanges {

  @Input() componentName: string;
  @Input() config: MapSidebar;

  constructor(private viewContainerRef: ViewContainerRef, private componentFactoryResolver: ComponentFactoryResolver) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (!this.componentName) {
      return;
    }
    const factories = Array.from(this.componentFactoryResolver['_factories'].keys());
    const factoryClass = <Type<any>>factories.find((x: any) => x.name === this.componentName);
    const factory = this.componentFactoryResolver.resolveComponentFactory(factoryClass);
    this.viewContainerRef.clear();
    const component = this.viewContainerRef.createComponent(factory);
    if (this.config) {
      if (this.config.keyInputInjector && this.config.keyInputInjector.length > 0) {
        this.config.keyInputInjector.forEach((key, idx) => {
          component.instance[key] = this.config.valueInputInjector[idx];
        });
      }
    }
  }

}
