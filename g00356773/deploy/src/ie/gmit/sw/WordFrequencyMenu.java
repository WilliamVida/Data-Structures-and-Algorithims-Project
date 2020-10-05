// http://www.fredosaurus.com/notes-java/data/collections/maps/ex-wordfreq.html

package ie.gmit.sw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;

class WordFrequencyMenu {
	private static final int WIDTH = 800;;
	private static final int HEIGHT = 600;;

	public static void main(String[] unused) throws IOException {

		Scanner in = new Scanner(System.in);
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

		File inputFile;
		File ignoreFile;
		String wordCloudName = "";

		int menuOption = 0;

		String[] wrds;
		int[] frequency;

		int[] styleArray = { Font.PLAIN, Font.BOLD, Font.ITALIC };
		String[] typeArray = { Font.SERIF, Font.SANS_SERIF };
		Color[] colourArray = { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.BLACK, Color.CYAN, Color.PINK,
				Color.MAGENTA };

		Random rand = new Random();
		int x, y;
		int randomStyle, randomType, randomColour;

		int wordMin = 0;

		String website;
		String content = null;
		URLConnection connection = null;

		int size = 0;

		WordCounter counter = new WordCounter();
		int n = 0;

		System.out.println("Enter 1 to print word cloud from a file");
		System.out.println("Enter 2 to print word cloud from a website");
		System.out.println("Enter -1 to exit");
		menuOption = in.nextInt();
		while (menuOption != -1) {
			// text file
			if (menuOption == 1) {
				in.nextLine();
				
				// input file
				System.out.println("Name of file containing text to analyze:");
				inputFile = new File(in.nextLine());

				// ignore file
				System.out.println("Name of file containing words to ignore:");
				ignoreFile = new File(in.nextLine());

				n = 0;
				counter.ignore(ignoreFile);
				counter.countWords(inputFile);
				n = counter.getEntryCount();

				wrds = counter.getWords(WordCounter.SortOrder.BY_FREQUENCY);
				frequency = counter.getFrequencies(WordCounter.SortOrder.BY_FREQUENCY);

				System.out.println("What is the minimum word frequency you want to show on the word cloud");
				wordMin = in.nextInt();

				for (int i = 0; i < n; i++) {
					// random
					x = rand.nextInt(WIDTH);
					y = rand.nextInt(HEIGHT);
					randomStyle = rand.nextInt(styleArray.length);
					randomType = rand.nextInt(typeArray.length);
					randomColour = rand.nextInt(colourArray.length);

					// print words that will be in the image
					if (wordMin <= frequency[i])
						System.out.println(frequency[i] + " " + wrds[i]);

					// font size
					size = frequency[i] / 15;

					Font font = new Font(typeArray[randomType], styleArray[randomStyle], size);
					Graphics graphics = image.getGraphics();
					graphics.setColor(colourArray[randomColour]);
					graphics.setFont(font);

					if (frequency[i] >= wordMin)
						graphics.drawString(wrds[i], x, y);
				} // for

				in.nextLine();
				System.out.println("Enter name of the word cloud image file, DO NOT INCLUDE IMAGE TYPE");
				wordCloudName = in.nextLine();
				ImageIO.write(image, "png", new File(wordCloudName + ".png"));
			} // option 1
			// url
			else if (menuOption == 2) {
				in.nextLine();

				// ask for url
				System.out.println("Enter url to analyse in the form https://www.google.com/");
				website = in.nextLine();
				// for testing
				// https://en.wikisource.org/wiki/Adolf_Hitler%27s_Speech_at_the_Berlin_Sportpalast_(30_January_1940)

				// ignore file
				System.out.println("Name of file containing words to ignore:");
				ignoreFile = new File(in.nextLine());

				// get html from website
				// https://stackoverflow.com/questions/31462/how-to-fetch-html-in-java
				connection = new URL(website).openConnection();
				Scanner scanner = new Scanner(connection.getInputStream());
				scanner.useDelimiter("\\Z");
				content = scanner.next();
				scanner.close();

				// print html to file
				PrintWriter writer = new PrintWriter("url.txt", "UTF-8");
				writer.println(content);
				writer.close();
				inputFile = new File("url.txt");

				n = 0;
				counter.ignore(ignoreFile);
				counter.countWords(inputFile);
				n = counter.getEntryCount();

				wrds = counter.getWords(WordCounter.SortOrder.BY_FREQUENCY);
				frequency = counter.getFrequencies(WordCounter.SortOrder.BY_FREQUENCY);

				System.out.println("What is the minimum word frequency you want to show on the word cloud");
				wordMin = in.nextInt();

				for (int i = 0; i < n; i++) {
					// random
					x = rand.nextInt(WIDTH);
					y = rand.nextInt(HEIGHT);
					randomStyle = rand.nextInt(styleArray.length);
					randomType = rand.nextInt(typeArray.length);
					randomColour = rand.nextInt(colourArray.length);

					// print words that will be in the image
					if (wordMin <= frequency[i])
						System.out.println(frequency[i] + " " + wrds[i]);

					// font size
					size = frequency[i] / 15;

					Font font = new Font(typeArray[randomType], styleArray[randomStyle], size);
					Graphics graphics = image.getGraphics();
					graphics.setColor(colourArray[randomColour]);
					graphics.setFont(font);

					if (frequency[i] >= wordMin)
						graphics.drawString(wrds[i], x, y);
				} // for

				in.nextLine();
				System.out.println("Enter name of the word cloud image file, DO NOT INCLUDE IMAGE TYPE");
				wordCloudName = in.nextLine();
				ImageIO.write(image, "png", new File(wordCloudName + ".png"));
			} // option 2

			System.out.println("Enter 1 to print word cloud from a file");
			System.out.println("Enter 2 to print word cloud from a website");
			System.out.println("Enter -1 to exit");
			menuOption = in.nextInt();
		} // while

		System.out.println("End");
		in.close();
	}
}