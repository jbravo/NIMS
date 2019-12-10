import {Directive, ElementRef, HostListener} from '@angular/core';

@Directive({
  selector: '[positiveInteger]'
})
export class PositiveIntegerDirective {

  // Allow decimal numbers and negative values
  private regex: RegExp = new RegExp(/^-?[0-9]+(\[0-9]*)?$/g);
  // Allow key codes for special events. Reflect :
  // Backspace, tab, end, home
  private specialKeys: Array<string> = ['Backspace', 'Tab', 'End', 'Home', 'Delete', 'Del', 'Ctrl', 'ArrowLeft', 'ArrowRight', 'Left', 'Right', 'a', 'c', 'v', 'x'];

  constructor(
    private el: ElementRef
  ) {
  }

  @HostListener('keydown', ['$event'])
  onKeyDown(event: KeyboardEvent) {
    // Allow Backspace, tab, end, and home keys
    const key = ['a', 'c', 'v', 'x'];
    if (key.includes(event.key)) {
      if (event.ctrlKey === true) {
      } else {
        event.preventDefault();
      }
    } else {
    }
    if (this.specialKeys.indexOf(event.key) !== -1) {
      return;
    }
    const current = this.el.nativeElement.value;
    const next = current.concat(event.key);
    if (next && !String(next).match(this.regex)) {
      event.preventDefault();
    }
  }
  @HostListener('paste', ['$event'])
  onPaste(event: ClipboardEvent) {
    event.preventDefault();
    const pastedInput: string = event.clipboardData
      .getData('text/plain')
      .replace(/[^0-9]/g, ''); // get a digit-only string
    document.execCommand('insertText', false, pastedInput);
  }

}
