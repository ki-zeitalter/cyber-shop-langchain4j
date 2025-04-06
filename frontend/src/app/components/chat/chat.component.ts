import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent {
  @Input() isPopoverOpen = false;
  @Output() closePopover = new EventEmitter<void>();
  
  messageInput = '';
  messages: {text: string, isUser: boolean, timestamp: Date}[] = [
    {
      text: 'Hallo! Wie kann ich Ihnen helfen?',
      isUser: false,
      timestamp: new Date()
    }
  ];

  sendMessage(): void {
    if (this.messageInput.trim()) {
      this.messages.push({
        text: this.messageInput,
        isUser: true,
        timestamp: new Date()
      });
      this.messageInput = '';
      
      // Simuliere Antwort (später durch echte API ersetzen)
      setTimeout(() => {
        this.messages.push({
          text: 'Vielen Dank für Ihre Nachricht. Unser Team wird sich bald bei Ihnen melden.',
          isUser: false,
          timestamp: new Date()
        });
      }, 1000);
    }
  }

  onClosePopover(): void {
    this.closePopover.emit();
  }
}
