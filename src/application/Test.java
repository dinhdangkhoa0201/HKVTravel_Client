package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import entities.Tour;

public class Test {
	public static void main(String[] args) {
		String date = "12/12/2019";
		System.out.println(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		Tour tour = new Tour();
		
	}
}
