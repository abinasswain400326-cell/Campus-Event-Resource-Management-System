import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { EventService } from '../../core/event.service';
import { AuthService } from '../../core/auth.service';
import { EventItem } from '../../models/event.model';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [CommonModule, RouterLink, DatePipe],
  templateUrl: './event-list.component.html',
})
export class EventListComponent implements OnInit {
  events: EventItem[] = [];
  loading = true;

  constructor(private eventService: EventService, public auth: AuthService) {}

  ngOnInit(): void {
    this.eventService.getAll().subscribe({
      next: (data) => {
        this.events = data;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }
}
