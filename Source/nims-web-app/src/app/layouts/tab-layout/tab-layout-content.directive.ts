import {ComponentFactoryResolver, Directive, Input, OnChanges, SimpleChanges, Type, ViewContainerRef} from '@angular/core';

@Directive({
  selector: '[appTabLayoutContent]'
})
export class TabLayoutContentDirective implements OnChanges {

  @Input() componentName: string;

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
    this.viewContainerRef.createComponent(factory);
  }

}
