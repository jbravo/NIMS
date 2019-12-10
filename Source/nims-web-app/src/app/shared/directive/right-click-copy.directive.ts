import {Directive, EventEmitter, HostListener, Input, OnDestroy, Output} from '@angular/core';

@Directive({
  selector: '[rightClickCopy]'
})
export class RightClickCopyDirective implements OnDestroy {

  @Input('rightClickCopy') data: string;

  @Output() copied: EventEmitter<any> = new EventEmitter<any>();

  @HostListener('mouseleave', ['$event'])
  onMouseLeave(event: MouseEvent): void {
    document.oncontextmenu = () => {
      return true;
    };
  }

  @HostListener('mouseenter', ['$event'])
  onMouseEnter(event: MouseEvent): void {
    document.oncontextmenu = () => {
      return false;
    };
  }

  @HostListener('mouseup', ['$event'])
  onRightClick(event: MouseEvent): void {
    event.preventDefault();
    if (event.button !== 2) {
      return;
    }
    if (!this.data) {
      return;
    }

    const listener = (e: ClipboardEvent) => {
      const clipboard = e.clipboardData || window['clipboardData'];
      clipboard.setData('text', this.data.toString());
      e.preventDefault();

      this.copied.emit({copied: true, content: this.data});
    };

    document.addEventListener('copy', listener, false);
    document.execCommand('copy');
    document.removeEventListener('copy', listener, false);
  }

  ngOnDestroy(): void {
    this.onMouseLeave(null);
  }

}
