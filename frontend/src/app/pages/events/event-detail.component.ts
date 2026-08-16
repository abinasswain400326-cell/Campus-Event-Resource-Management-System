import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { EventService } from '../../core/event.service';
import { AuthService } from '../../core/auth.service';
import { EventItem } from '../../models/event.model';

@Component({
  selector: 'app-event-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, DatePipe],
  templateUrl: './event-detail.component.html',
})
export class EventDetailComponent implements OnInit {
  event?: EventItem;
  message = '';
  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventService: EventService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.eventService.getById(id).subscribe((data) => (this.event = data));
  }

  rsvp(): void {
    if (!this.event) return;
    this.eventService.rsvp(this.event.id).subscribe({
      next: () => {
        this.message = "You're registered! See you there.";
        this.error = '';
        this.event!.registeredCount++;
      },
      error: (err) => {
        this.error = err?.error?.message || 'Could not RSVP.';
        this.message = '';
      },
    });
  }

  cancelEvent(): void {
    if (!this.event) return;
    if (!confirm('Cancel this event? This cannot be undone.')) return;
    this.eventService.cancel(this.event.id).subscribe(() => this.router.navigate(['/events']));
  }

  canManage(): boolean {
    return this.auth.hasRole('ADMIN', 'ORGANIZER');
  }
}
