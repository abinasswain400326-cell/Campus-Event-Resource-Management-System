-- Optional seed data. Run this after the app has started once (so Hibernate has
-- created the tables via ddl-auto=update).

-- Sample bookable resources
INSERT INTO resources (name, type, capacity) VALUES
  ('Main Auditorium', 'VENUE', 500),
  ('Seminar Hall B', 'VENUE', 120),
  ('Open Air Theatre', 'VENUE', 800),
  ('Projector Cart 1', 'EQUIPMENT', NULL),
  ('PA Sound System', 'EQUIPMENT', NULL);

-- Note: to create an ADMIN user, register normally via /api/auth/register
-- (which always creates an ATTENDEE), then manually promote them:
-- UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';
