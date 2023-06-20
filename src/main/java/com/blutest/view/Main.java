package com.blutest.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.blutest.controller.MonthlyInstallmentController;

public class Main {

	public static void main(String[] args) {
		String filePath;
		MonthlyInstallmentController installmentController = new MonthlyInstallmentController();
		int input = 1;
		if (args.length > 0) {
			filePath = args[0];
			try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				installmentController.getView("txt", reader);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			installmentController.getView("cmd", null);

		}
	}
}
