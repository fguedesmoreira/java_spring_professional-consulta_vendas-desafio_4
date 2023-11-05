package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleBySellerMinDTO;
import com.devsuperior.dsmeta.dto.SellerSummarySalesMinDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleBySellerMinDTO("
			+ "s.id, s.date, s.amount, s.seller.name) " + "FROM Sale s " + "WHERE s.date between :minDate and :maxDate "
			+ "AND UPPER(s.seller.name) LIKE CONCAT('%', UPPER(:name), '%')")
	Page<SaleBySellerMinDTO> searchSalesBySeller(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

	@Query(value = "SELECT new com.devsuperior.dsmeta.dto.SellerSummarySalesMinDTO(" + "s.seller.name, SUM(s.amount)) "
			+ "FROM Sale s " + "WHERE s.date between :minDate and :maxDate "
			+ "AND UPPER(s.seller.name) LIKE CONCAT('%', UPPER(:name), '%') " + "GROUP BY s.seller.name")
	Page<SellerSummarySalesMinDTO> searchSellerSummarySales(LocalDate minDate, LocalDate maxDate, String name,
			Pageable pageable);

}