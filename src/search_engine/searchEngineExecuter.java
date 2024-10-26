package search_engine;

import java.util.Scanner;

public class searchEngineExecuter {

	public static void main(String[] args) {
		executionMenu();
	}

	public static void executionMenu() {
		Scanner sc = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		boolean menu = true;
		while (menu) {
			System.out.println(
					"\n\n-----------------------------------------\nSearch Engine\n-----------------------------------------");
			String[] teamMembers = { "Prajwal Banakar", "Amit Kumar", "Siddh Patel", "Sadia Anzum", "Vikrant Singh" };

			System.out.println("Team Members:");
			System.out.println("--------------");

			for (String member : teamMembers) {
				System.out.println("* " + member);
			}
			System.out.println("-----------------------------------------");
			System.out.println("Press 1 to search for a keyword");
			System.out.println("Press 2 to crawl web pages");
			System.out.println("Press 0 to exit");
			System.out.println("-----------------------------------------");
			System.out.print("Enter your option: ");
			String ans = sc.nextLine();

			switch (ans) {
			case "1":
				System.out.print("Enter the keyword to search: ");
				Search.searchPhrase(sc2.nextLine(), 10);
				break;
			case "2":
				crawlingMenu();
				menu = false;
				break;
			case "0":
				System.out.println("Thank You! Have a nice day!");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Input. Please try again");
			}
		}
		sc.close();
		sc2.close();

	}

	public static void crawlingMenu() {
		Scanner sc = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		boolean menu = true;
		while (menu) {
			System.out.println(
					"\n\n-----------------------------------------\nWeb Crawling\n-----------------------------------------");
			System.out.println("Press 1 to crawl websites");
			System.out.println("Press 2 to crawl default web page");
			System.out.println("Press 0 to exit");
			System.out.println("-----------------------------------------");
			System.out.print("Enter your option: ");
			String ans = sc.nextLine();

			switch (ans) {
			case "1":
				System.out.println("Enter websites to crawl separated by a space\n");
				WebCrawler.crawlCustom(sc2.nextLine());
				break;
			case "2":
				System.out.println("Executing crawl on default links");
				WebCrawler.crawlDefault();
				break;
			case "0":
				System.out.println("Thank You! Have a nice day!");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Input. Please try again");
			}
			menu = false;
		}
		System.out.println("Thank You! Have a nice day!");
		sc.close();
		sc2.close();
	}

}
