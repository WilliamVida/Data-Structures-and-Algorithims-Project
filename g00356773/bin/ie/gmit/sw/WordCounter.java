// http://www.fredosaurus.com/notes-java/data/collections/maps/ex-wordfreq.html

package ie.gmit.sw;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Computes word frequency in source file; ignores words in ignore file. Uses
 * generic Sets, Maps, ArrayLists, regular expressions, Scanner.
 * 
 * @author Fred Swartz
 * @version 2007-05-06
 */
public class WordCounter {

	private static final Comparator<Map.Entry<String, Int>> SORT_BY_FREQUENCY = new ComparatorFrequency();

	public enum SortOrder {
		BY_FREQUENCY;
	}

	// =================================================================== fields
	Set<String> _ignoreWords; // Words to ignore.
	Map<String, Int> _wordFrequency; // Words -> frequency
	int _totalWords; // Total source words.

	// ============================================================== constructor
	/** Constructor */
	public WordCounter() {
		_ignoreWords = new HashSet<String>();
		_wordFrequency = new HashMap<String, Int>();
		_totalWords = 0;
	}

	// =================================================================== ignore
	/**
	 * Reads file of words to ignore. Ignore words are added to a Set. The
	 * IOException is passed to caller because we certinaly don't know what the user
	 * interface issue is.
	 * 
	 * @param ignoreFile
	 *            File of words to ignore.
	 */
	public void ignore(File ignoreFile) throws IOException {
		Scanner ignoreScanner = new Scanner(ignoreFile);
		ignoreScanner.useDelimiter("[^A-Za-z]+");

		while (ignoreScanner.hasNext())
			_ignoreWords.add(ignoreScanner.next());
		ignoreScanner.close();
	}

	// =================================================================== ignore
	/**
	 * Takes String of words to ignore. Ignore words are added to a Set.
	 * 
	 * @param ignore
	 *            String of words to ignore.
	 */
	public void ignore(String ignoreStr) {
		Scanner ignoreScanner = new Scanner(ignoreStr);
		ignoreScanner.useDelimiter("[^A-Za-z]+");

		while (ignoreScanner.hasNext())
			_ignoreWords.add(ignoreScanner.next());
		ignoreScanner.close();
	}

	// =============================================================== countWords
	/**
	 * Record the frequency of words in the source file. May be called more than
	 * once. IOException is passed to caller, who might know what to do with it.
	 * 
	 * @param File
	 *            of words to process.
	 */
	public void countWords(File sourceFile) throws IOException {
		Scanner wordScanner = new Scanner(sourceFile);
		wordScanner.useDelimiter("[^A-Za-z]+");

		while (wordScanner.hasNext()) {
			String word = wordScanner.next().toLowerCase();
			_totalWords++;

			if (!_ignoreWords.contains(word)) {
				Int count = _wordFrequency.get(word);
				if (count == null)
					_wordFrequency.put(word, new Int(1));
				else
					count.value++;
			}
		} // while
		wordScanner.close();
	}

	// =============================================================== countWords
	/**
	 * Record the frequency of words in a String. May be called more than once.
	 * 
	 * @param String
	 *            of words to process.
	 */
	public void countWords(String source) {
		Scanner wordScanner = new Scanner(source);
		wordScanner.useDelimiter("[^A-Za-z]+");

		while (wordScanner.hasNext()) {
			String word = wordScanner.next().toLowerCase();
			_totalWords++;

			if (!_ignoreWords.contains(word)) {
				Int count = _wordFrequency.get(word);
				if (count == null)
					_wordFrequency.put(word, new Int(1));
				else
					count.value++;
			}
		} // while
		wordScanner.close();
	}

	// ============================================================= getWordCount
	/**
	 * Returns number of words in all source file(s).
	 * 
	 * @return Total number of words proccessed in all source files.
	 */
	public int getWordCount() {
		return _totalWords;
	}

	// ============================================================ getEntryCount
	/**
	 * Returns the number of unique, non-ignored words, in the source file(s). This
	 * number should be used to for the size of the arrays that are passed to
	 * getWordFrequency.
	 * 
	 * @return Number of unique non-ignored source words.
	 */
	public int getEntryCount() {
		return _wordFrequency.size();
	}

	// ================================================================= getWords
	/**
	 * Return array of unique words, in the order specified.
	 * 
	 * @return An array of the words in the currently selected order.
	 */
	public String[] getWords(SortOrder sortBy) {
		String[] result = new String[_wordFrequency.size()];
		ArrayList<Map.Entry<String, Int>> entries = new ArrayList<Map.Entry<String, Int>>(_wordFrequency.entrySet());

		Collections.sort(entries, SORT_BY_FREQUENCY);

		int i = 0;

		for (Map.Entry<String, Int> ent : entries)
			result[i++] = ent.getKey();
		return result;
	}

	// =========================================================== getFrequencies
	/**
	 * Return array of frequencies, in the order specified.
	 * 
	 * @return An array of the frequencies in the specified order.
	 */
	public int[] getFrequencies(SortOrder sortBy) {
		int[] result = new int[_wordFrequency.size()];
		ArrayList<Map.Entry<String, Int>> entries = new ArrayList<Map.Entry<String, Int>>(_wordFrequency.entrySet());

		Collections.sort(entries, SORT_BY_FREQUENCY);

		int i = 0;

		for (Map.Entry<String, Int> ent : entries)
			result[i++] = ent.getValue().value;
		return result;
	}

}