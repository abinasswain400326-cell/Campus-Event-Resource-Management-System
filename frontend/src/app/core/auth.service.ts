import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { AuthResponse, Role } from '../models/user.model';

const STORAGE_KEY = 'campus_event_auth';

@Injectable({ providedIn: 'root' })
export class AuthService {
  // Signal so components can reactively show/hide UI based on auth state
  currentUser = signal<AuthResponse | null>(this.loadFromStorage());

  constructor(private http: HttpClient, private router: Router) {}

  private loadFromStorage(): AuthResponse | null {
    const raw = localStorage.getItem(STORAGE_KEY);
    return raw ? JSON.parse(raw) : null;
  }

  register(fullName: string, email: string, password: string): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${environment.apiUrl}/auth/register`, { fullName, email, password })
      .pipe(tap((res) => this.persist(res)));
  }

  login(email: string, password: string): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${environment.apiUrl}/auth/login`, { email, password })
      .pipe(tap((res) => this.persist(res)));
  }

  logout(): void {
    localStorage.removeItem(STORAGE_KEY);
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this.currentUser()?.token ?? null;
  }

  hasRole(...roles: Role[]): boolean {
    const role = this.currentUser()?.role;
    return !!role && roles.includes(role);
  }

  isLoggedIn(): boolean {
    return !!this.currentUser();
  }

  private persist(res: AuthResponse): void {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(res));
    this.currentUser.set(res);
  }
}
