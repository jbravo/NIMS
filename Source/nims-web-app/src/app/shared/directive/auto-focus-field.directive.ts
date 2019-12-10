import {AfterViewInit, Directive, ElementRef} from '@angular/core';

@Directive({
  selector: '[autoFocusField]'
})
export class AutoFocusFieldDirective implements AfterViewInit {

  constructor(private el: ElementRef) {
  }

  ngAfterViewInit() {
    setTimeout(args => {
      this.el.nativeElement.focus();
    }, 300);
  }

}
