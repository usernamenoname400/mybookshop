package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.dto.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Integer> {

  Optional<Book> findBySlug(String slug);

  Optional<Book> findById(Integer id);

  List<Book> findTop3ByOrderByTitle();

  Page<Book> findByAuthor_Id(Integer authorId, Pageable nextPage);

  Page<Book> findByGenre_Id(Integer genresId, Pageable nextpage);

  @Query(value = "from Book as b where b.genre.id in ?1")
  Page<Book> findInRootGenres(List<Integer> genres, Pageable nextPage);

  // NEW BOOK REPOSITORY METHODS

  //@Query("select count(Book) from Book where Book.author.id = ?1")
  //Long getCountBooksByAuthor(Integer authorId);

  Long countByAuthor_Id(Integer authorId);

  List<Book> findBooksByAuthorFirstNameContaining(String authorFirstname);

  List<Book> findBooksByPriceBetween(Integer min, Integer max);

  List<Book> findBooksByPriceIs(Integer price);

  @Query("from Book where isBestseller = 1")
  List<Book> getBestSellers();

  @Query(value = "select * from book where discount = (select max(discount) from book)", nativeQuery = true)
  List<Book> getBooksWithMaxDiscount();

  List<Book> getBookByPubDateBetween(Date minDate, Date maxDate);

  List<Book> findBookByTitleContaining(String bookTitle);

  Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);

  Page<Book> findBookByPubDateBetween(Date from, Date to, Pageable nextPage);

  @Query("select b from Book as b join b.rating as r order by r.rating5 desc")
  Page<Book> findAllByOrderByRatingDesc(Pageable nextPage);

  @Query("select b from Book as b join b.tagList as t where t.id = ?1")
  Page<Book> findBooksByTag(Integer tagid, Pageable nextPage);

  List<Book> findBooksBySlugIn(String[] slugs);
}
