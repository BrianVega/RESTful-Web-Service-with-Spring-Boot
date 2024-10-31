insert into user_details (id, birth_date, name)
values (1001, current_date(), 'Brian');

insert into user_details (id, birth_date, name)
values (1002, current_date(), 'Oscar');

insert into user_details (id, birth_date, name)
values (1003, current_date(), 'Osvaldo');

insert into post(id, description, user_id)
values (2001, 'I want to learn AWS', 1001),
       (2002, 'I want to learn DevOps', 1002),
       (2003, 'I want to learn Spring', 1003),
       (2004, 'I want to learn GCP', 1001);

