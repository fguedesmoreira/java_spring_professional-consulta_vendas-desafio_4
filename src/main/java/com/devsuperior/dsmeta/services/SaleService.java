package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.dto.SaleBySellerMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerSummarySalesMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.exceptions.DateConvertException;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.devsuperior.dsmeta.util.DateValidator;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<SaleBySellerMinDTO> searchSalesBySeller(String minDate, String maxDate, String name,
			Pageable pageable) {

		LocalDate[] interval = this.prepareSearchPeriod(minDate, maxDate);

		Page<SaleBySellerMinDTO> result = repository.searchSalesBySeller(interval[0], interval[1], name, pageable);

		return result;

	}

	@Transactional(readOnly = true)
	public Page<SellerSummarySalesMinDTO> searchSellerSummarySales(String minDate, String maxDate, String name,
			Pageable pageable) {

		LocalDate[] interval = this.prepareSearchPeriod(minDate, maxDate);

		Page<SellerSummarySalesMinDTO> result = repository.searchSellerSummarySales(interval[0], interval[1], name,
				pageable);

		return result;

	}

	private LocalDate[] prepareSearchPeriod(String minDate, String maxDate) {

		LocalDate[] interval = new LocalDate[2];

		DateValidator dateValidator = new DateValidator("yyyy-MM-dd");

		if (maxDate == null) {
			interval[1] = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			if (dateValidator.isValid(maxDate)) {
				interval[1] = dateValidator.stringToLocalDate(maxDate);
			} else {
				throw new DateConvertException("O parâmetro maxDate deve ser uma data válida, informada no formato YYYY-MM-DD.");
			}

		}

		if (minDate == null) {
			interval[0] = interval[1].minusYears(1L);
		} else {
			if (dateValidator.isValid(minDate)) {
				interval[0] = dateValidator.stringToLocalDate(minDate);
			} else {
				throw new DateConvertException("O parâmetro minDate deve ser uma data válida, informada no formato YYYY-MM-DD.");
			}

		}

		return interval;

	}

}