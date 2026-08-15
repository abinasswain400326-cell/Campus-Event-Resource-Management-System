import { Routes } from '@angular/router';
import { authGuard, roleGuard } from './core/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'events', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'register',
    loadComponent: () => import('./pages/register/register.component').then((m) => m.RegisterComponent),
  },
  {
    path: 'events',
    loadComponent: () => import('./pages/events/event-list.component').then((m) => m.EventListComponent),
  },
  {
    path: 'events/new',
    loadComponent: () => import('./pages/events/event-form.component').then((m) => m.EventFormComponent),
    canActivate: [authGuard, roleGuard(['ADMIN', 'ORGANIZER'])],
  },
  {
    path: 'events/:id/edit',
    loadComponent: () => import('./pages/events/event-form.component').then((m) => m.EventFormComponent),
    canActivate: [authGuard, roleGuard(['ADMIN', 'ORGANIZER'])],
  },
  {
    path: 'events/:id',
    loadComponent: () => import('./pages/events/event-detail.component').then((m) => m.EventDetailComponent),
  },
  { path: '**', redirectTo: 'events' },
];
