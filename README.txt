Word Cloud
This program allows the user to enter a .txt file or a URL which adds the words and the frequency of that word to a HashMap. Then it prompts the user to enter an ignore words file. Then the it asks what is the minimum frequency of the word that can be printed out to a word cloud .png file with the most frequent word appearing larger than the rest get smaller. Finally, the name of the .png file is asked to the user.

ISSUES
There is one issue with it, in the while loop if you enter a .txt file and get to the end, then you enter a URL or vice versa, the second time around the new words and frequency will be added to the original HashMap. The only way to get around this is to enter one, then end the program then restart.

Also, if the largest frequency of the words is small enough then the font size will be microscopic.