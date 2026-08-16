import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from '../../core/event.service';

@Component({
  selector: 'app-event-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './event-form.component.html',
})
export class EventFormComponent implements OnInit {
  isEditMode = false;
  eventId?: number;
  errorMessage = '';
  form;

  constructor(
    private fb: FormBuilder,
    private eventService: EventService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      venue: ['', Validators.required],
      capacity: [50, [Validators.required, Validators.min(1)]],
      startTime: ['', Validators.required],
      endTime: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.eventId = Number(idParam);
      this.eventService.getById(this.eventId).subscribe((event) => {
        this.form.patchValue({
          title: event.title,
          description: event.description,
          venue: event.venue,
          capacity: event.capacity,
          startTime: event.startTime.substring(0, 16),
          endTime: event.endTime.substring(0, 16),
        });
      });
    }
  }

  submit(): void {
    if (this.form.invalid) return;
    const payload = this.form.value as any;

    const request$ = this.isEditMode
      ? this.eventService.update(this.eventId!, payload)
      : this.eventService.create(payload);

    request$.subscribe({
      next: (event) => this.router.navigate(['/events', event.id]),
      error: (err) => {
        this.errorMessage = err?.error?.message || 'Could not save event.';
      },
    });
  }
}
