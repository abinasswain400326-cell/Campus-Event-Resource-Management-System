export interface EventItem {
  id: number;
  title: string;
  description: string;
  startTime: string;
  endTime: string;
  venue: string;
  capacity: number;
  registeredCount: number;
  organizerName: string;
  status: 'DRAFT' | 'PUBLISHED' | 'CANCELLED';
}

export interface EventRequest {
  title: string;
  description: string;
  startTime: string;
  endTime: string;
  venue: string;
  capacity: number;
}
