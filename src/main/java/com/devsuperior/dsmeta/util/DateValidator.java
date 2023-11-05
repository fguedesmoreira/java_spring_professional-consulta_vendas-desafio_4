package com.devsuperior.dsmeta.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator {

	private String dateFormat;

	public DateValidator(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public boolean isValid(String dateStr) {
		DateFormat sdf = new SimpleDateFormat(this.dateFormat);
		sdf.setLenient(false);
		try {
			sdf.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	public String localDateToString(LocalDate localDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.dateFormat);
		return localDate.format(formatter);
	}
	
	public LocalDate stringToLocalDate(String dateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.dateFormat);
		return LocalDate.parse(dateStr, formatter);
	}

}