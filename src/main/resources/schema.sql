drop table if exists ratings;
drop table if exists books;
drop table if exists authors;

create table authors (
  id int,
  first_name varchar(50),
  last_name varchar(50)
);

create table genres(
  id int not null,
  name varchar(250) not null,
  super_genre varchar(250) not null,
  super_id int not null
);

create table  books(
  id int auto_increment primary key,
  author_id int not null,
  title varchar(250) not null,
  priceOld  varchar(250) default null,
  price varchar(250) default null,
  dt_release datetime not null,
  genres_id int not null
);

alter table books add constraint books_fk_author foreign key (author_id) references authors(id);
alter table books add constraint books_fk_genres foreign key (genres_id) references genres(id);

create table ratings(
  book_id int not null,
  rating_cnt_1 int not null,
  rating_cnt_2 int not null,
  rating_cnt_3 int not null,
  rating_cnt_4 int not null,
  rating_cnt_5 int not null
);

alter table ratings add constraint ratings_fk_books foreign key (book_id) references books(id);
