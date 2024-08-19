insert into booking_status(name, pretty_name)
values ('NEW', 'New'),
       ('APPROVED', 'Approved'),
       ('CANCELED', 'Canceled'),
       ('DECLINED', 'Declined');

insert into authority(authority)
values ('ADMIN'), ('USER');