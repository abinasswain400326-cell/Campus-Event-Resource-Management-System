import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { EventItem, EventRequest } from '../models/event.model';

@Injectable({ providedIn: 'root' })
export class EventService {
  private base = `${environment.apiUrl}/events`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<EventItem[]> {
    return this.http.get<EventItem[]>(this.base);
  }

  getById(id: number): Observable<EventItem> {
    return this.http.get<EventItem>(`${this.base}/${id}`);
  }

  create(payload: EventRequest): Observable<EventItem> {
    return this.http.post<EventItem>(this.base, payload);
  }

  update(id: number, payload: EventRequest): Observable<EventItem> {
    return this.http.put<EventItem>(`${this.base}/${id}`, payload);
  }

  cancel(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }

  rsvp(eventId: number): Observable<any> {
    return this.http.post(`${environment.apiUrl}/events/${eventId}/rsvp`, {});
  }

  cancelRsvp(eventId: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/events/${eventId}/rsvp`);
  }
}
