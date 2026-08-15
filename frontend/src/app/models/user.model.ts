export type Role = 'ADMIN' | 'ORGANIZER' | 'ATTENDEE';

export interface AuthResponse {
  token: string;
  userId: number;
  fullName: string;
  email: string;
  role: Role;
}
