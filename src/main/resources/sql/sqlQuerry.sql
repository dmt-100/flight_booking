ALTER TABLE tickets
	DROP CONSTRAINT tickets_book_ref_fkey;

ALTER TABLE tickets
	ADD CONSTRAINT tickets_book_ref_fkey
	FOREIGN KEY (book_ref)
	REFERENCES bookings(book_ref)
	ON DELETE CASCADE;

ALTER TABLE ticket_flights
	DROP CONSTRAINT ticket_flights_ticket_no_fkey;

ALTER TABLE ticket_flights
	ADD CONSTRAINT ticket_flights_ticket_no_fkey
	FOREIGN KEY (ticket_no)
	REFERENCES tickets(ticket_no)
	ON DELETE CASCADE;

ALTER TABLE boarding_passes
	DROP CONSTRAINT boarding_passes_ticket_no_fkey;

ALTER TABLE boarding_passes
	ADD CONSTRAINT boarding_passes_ticket_no_fkey
	FOREIGN KEY (ticket_no)
	REFERENCES tickets(ticket_no)
	ON DELETE CASCADE;