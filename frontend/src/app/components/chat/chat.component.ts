import {AfterViewInit, Component, CUSTOM_ELEMENTS_SCHEMA, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import 'deep-chat';
import {CartService} from '../../services/cart.service';

interface RequestDetails {
  body: any;
  [key: string]: any;
}

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChatComponent implements OnInit, AfterViewInit {
  @Input() isPopoverOpen = false;
  @Output() closePopover = new EventEmitter<void>();
  @Output() expand = new EventEmitter<void>();
  @ViewChild('deepChat', { static: true }) deepChatElement!: ElementRef;

  cartId: number | null = null;

  constructor(private cartService: CartService) {
  }

  onClosePopover(): void {
    this.closePopover.emit();
  }

  onExpand(): void {
    this.expand.emit();
  }

  ngOnInit(): void {
    this.cartService.cart$.subscribe(cart => {
      if(cart){
        this.cartId = cart.id;
      }
    })
  }

  ngAfterViewInit(): void {
    this.initializeDeepChat();
  }

  private initializeDeepChat(): void {
    if (this.deepChatElement && this.deepChatElement.nativeElement) {
      this.deepChatElement.nativeElement.requestInterceptor = (requestDetails: RequestDetails) => {
        console.log(requestDetails); // printed above
        requestDetails.body = {...requestDetails.body, cartId: this.cartId};
        return requestDetails;
      };
    }
  }
}
