--liquibase formatted sql
--changeset somebody:fill-table-book_file runInTransaction:true failOnError:true
insert into book_file_type(id, name, description) values(1, '.pdf',  'Portable Data Format files');
insert into book_file_type(id, name, description) values(2, '.epub', 'Electronic-publication format files');
insert into book_file_type(id, name, description) values(3, '.fb2',  'Feed Book format files');
insert into book_file(id, hash, type_id, path, book_id) values(1, '04aca1566641440a8ffedbaa60c4719c', 1, '/book-pqa-533.pdf', 194);
insert into book_file(id, hash, type_id, path, book_id) values(2, '1a8d9452ff304ee8b28f377396878ac2', 2, '/book-pqa-533.epub', 194);
insert into book_file(id, hash, type_id, path, book_id) values(3, '8b88978e552e47de8bd40b9a2bd472b4', 3, '/book-pqa-533.fb2', 194);
--rollback rollback;