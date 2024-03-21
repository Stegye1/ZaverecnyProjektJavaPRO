package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long>, JpaSpecificationExecutor<InvoiceEntity> {
    // JpaSpecificationExecutor není repozitář, pouze přidává přetížení pro metody, které mají
    //ostatní repozitáře. V repozitáři je tedy nutné stále implementovat nějaký repozitář jako je JpaRepository.


    @Query("SELECT SUM(actual.price) AS currentYearEURSum," +
            "SUM(everything.price) AS allTimeEURSum," +
            "COUNT(*) AS invoicesCount FROM invoice everything " +
            "LEFT JOIN invoice actual " +
            "ON everything.id = actual.id " +
            "AND YEAR(actual.issued) = 2024 " +
            "WHERE actual.currency=0 OR everything.currency=0")
    List<Object[]> statisticsEUR();

    @Query("SELECT SUM(actual.price) AS currentYearCZKSum," +
            "SUM(everything.price) AS allTimeCZKSum," +
            "COUNT(*) AS invoicesCount FROM invoice everything " +
            "LEFT JOIN invoice actual " +
            "ON everything.id = actual.id " +
            "AND YEAR(actual.issued) = 2024 " +
            "WHERE actual.currency=1 OR everything.currency=1")
    List<Object[]> statisticsCZK();
}
//   ukázka filtrace pomocí SQL příkazů:
//    @Query(value = "SELECT m FROM movie m WHERE" +
//            "    (:#{#filter.getFromYear()} is null OR m.year >= :#{#filter.getFromYear()}) " +
//            "AND (:#{#filter.getToYear()} is null OR m.year <= :#{#filter.getToYear()})  " +
//            "AND (:#{#filter.getActorID()} = -1 OR :#{#filter.getActorID()} IN (SELECT a.id FROM m.actors a)) " +
//            "AND (:#{#filter.getGenre()} = '' OR :#{#filter.getGenre()} IN (SELECT g FROM m.genres g))"
//    )
//    List<MovieEntity> getFilteredMovies(MovieFilter filter, Pageable pageable);


