package search_engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//Uses Jsoup to crawl through web and creates text files with parsed information.
public class WebCrawler
{

	static Map<Integer,String> doc_info=new HashMap();
	static int doc_counter=0;
	static Inverted_Index index = new Inverted_Index();

	public static String crawl(String link, int maxPages)
	 {

		String html = urlToHTML(link);

		Document doc = Jsoup.parse(html);
		String text = doc.text();
		String title = doc.title();
		// System.out.print(text);
		createFile(title, text, link);

		Elements e = doc.select("a");
		String links = "";
		int pageCount = 0;
		//System.out.println("pages");
		for (Element e2 : e) {
			if (pageCount >= 3) {//-change
				break;
			}
			String href = e2.attr("abs:href");
			if (href.length() > 3) {
				links = links + "\n" + href;
				pageCount++;
			}
		}
		return links;
	}

	public static void createFile(String title, String text, String link) {
		try {
			String[] titlesplit = title.split("\\|");
			String newTitle = "";
			for (String s : titlesplit) {
				newTitle = newTitle + " " + s;
			}
			File f = new File("WebPages//" + newTitle + ".txt");
			doc_info.put(doc_counter++,newTitle);
			index.addDocument(doc_counter-1, text);
			//System.out.println("cff");
			f.createNewFile();
			PrintWriter pw = new PrintWriter(f);
			pw.println(link);
			pw.println(text);

			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String urlToHTML(String link) {
		try {
			URL url = new URL(link);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			Scanner sc = new Scanner(conn.getInputStream());
			StringBuffer sb = new StringBuffer();
			while (sc.hasNext()) {
				sb.append(" " + sc.next());
			}

			String result = sb.toString();
			sc.close();
			return result;
		} catch (IOException e) {
			System.out.println(e);
		}
		return link;
	}

	public static void crawlPages(String links, int maxPages) {

		try {
			File f = new File("CrawledPages.txt");
			f.createNewFile();

			for (String link : links.split("\n"))
			{
				crawlAndWrite(link, f, maxPages);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void crawlAndWrite(String link, File file, int maxPages) {
		// TODO Auto-generated method stub
		try {
			In in = new In(file);
			String linksRead = in.readAll();
			//System.out.println(linksRead);
			if (!linksRead.contains(link))
			{
				//System.out.println("link");
				String crawledContent = crawl(link, maxPages);
				System.out.println(link);

				try (FileWriter fw = new FileWriter(file, true)) {
					fw.write(link + "\n");
				}

				for (String nestedLink : crawledContent.split("\n")) {
					crawlAndWrite(nestedLink, file, maxPages);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void crawlDefault() {
		String links = "https://www.bbc.com/" + "\n" + "https://www.ctvnews.ca/" + "\n" + "https://www.cicnews.com/";
 		crawlPages(links, 3);//---
	}

	public static void crawlCustom(String line)
	 {
		String[] links = line.split(" ");
		String newLine = "";
		for (String link : links) {
			newLine = newLine + link + "\n";
		}
		crawlPages(newLine, 3);//---
	 }

	public static void main(String[] args)
	 {
		crawlDefault();
		Set<Integer> res=index.search("have");
		System.out.println(res);
		String[] query = {"have"};
	    List<Integer> rankedResults = index.rankResults(res,query );
        System.out.println("Ranked documents for 'have': " + rankedResults);
        for(Integer i:rankedResults)
         {
        	System.out.println(doc_info.get(i));
         }

	 }
}