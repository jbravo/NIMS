import {Directive, ElementRef, HostListener} from '@angular/core';

@Directive({
  selector: '[negativeDecimalFiveTenths]'
})
export class NegativeDecimalFiveTenthsDirective {

  // private regex: RegExp = new RegExp(/^\d*{0,3}\.?\d{0,3}$/g);
  private regex: RegExp = new RegExp(/^\d{0,14}(\.\d{0,3})?$/g);
  private specialKeys: Array<string> = ['Backspace', 'Tab', 'End', 'Home', 'Delete', 'Del', 'Ctrl', 'ArrowLeft', 'ArrowRight', 'Left', 'Right', 'a', 'c', 'v', 'x'];

  constructor(
    private el: ElementRef
  ) {
  }

  @HostListener('keydown', ['$event'])
  onKeyDown(event: KeyboardEvent) {
    const keyAddCopy = ['a', 'c', 'v', 'x'];
    if (keyAddCopy.includes(event.key)) {
      if (event.ctrlKey === true) {
      } else {
        event.preventDefault();
      }
    }
    // Allow Backspace, tab, end, and home keys
    if (this.specialKeys.indexOf(event.key) !== -1) {
      return;
    }
    const current: string = this.el.nativeElement.value;
    const position = this.el.nativeElement.selectionStart;
    const next: string = [current.slice(0, position), event.key === 'Decimal' ? '.' : event.key, current.slice(position)].join('');
    if (next && !String(next).match(this.regex)) {
      event.preventDefault();
    }
  }

  @HostListener('paste', ['$event'])
  onPaste(event: ClipboardEvent) {
    event.preventDefault();
    const pastedInput: string = event.clipboardData
      .getData('text/plain')
      .replace(/[^0-9.]/g, ''); // get a digit-only string
    document.execCommand('insertText', false, pastedInput);
  }

}
