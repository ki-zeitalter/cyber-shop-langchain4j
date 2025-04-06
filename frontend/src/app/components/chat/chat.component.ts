import {Component, CUSTOM_ELEMENTS_SCHEMA, EventEmitter, Input, Output} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import 'deep-chat';


@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChatComponent {
  @Input() isPopoverOpen = false;
  @Output() closePopover = new EventEmitter<void>();


  onClosePopover(): void {
    this.closePopover.emit();
  }
}
