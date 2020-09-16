package FinalProject_Template;

import java.util.ArrayList;


public class Twitter {

	// ADD YOUR CODE BELOW HERE
	private MyHashTable<String, Tweet> latestTweets;
	private MyHashTable<String, ArrayList<Tweet>> tweetsByDate;
	MyHashTable<String,Integer> nahWords;
	//
	//private ArrayList<String> nahWords = new ArrayList<String>(); // last methof
	
	private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

	// ADD CODE ABOVE HERE

	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		// ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
		// ArrayList<String> stopWordsList = new ArrayList<String>();
		// ADD CODE ABOVE HERE

		latestTweets = new MyHashTable<String, Tweet>(tweets.size() * 2);
		tweetsByDate = new MyHashTable<String, ArrayList<Tweet>>(tweets.size() * 2);
		nahWords = new MyHashTable<String,Integer>(stopWords.size() * 2);
		for(String s :stopWords) {
			nahWords.put(s.toLowerCase(),1);
			
		}

		for (int i = 0; i < tweets.size(); i++) {
			addTweet(tweets.get(i));

		}

		/*
		 * for(int i = 0 ; i<tweets.size();i++) { String message
		 * =tweets.get(i).getMessage(); wordsInTweet = getWords(message); //I know have
		 * an arrayList of all words in the tweets }
		 */
	/*	for (int i = 0; i < stopWords.size(); i++) {
			String message = tweets.get(i).getMessage();
			nahWords = getWords(message); // I know have an arrayList of all the stop words in the tweet

		}
*/
	}

	/**
	 * Add Tweet t to this Twitter O(1)
	 */
	public void addTweet(Tweet t) {
if(t!=null) {
	
		Tweet oldTweet = latestTweets.get(t.getAuthor()); // getting the old tweet from
		if (oldTweet == null) {
			latestTweets.put(t.getAuthor(), t);
		} else if (t.compareTo(oldTweet) > 0) {
			latestTweets.put(t.getAuthor(), t);
		}
		// ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
        String date = t.getDateAndTime().substring(0,10);
		ArrayList<Tweet> List = tweetsByDate.get(date);

		if (List == null) {
			ArrayList<Tweet> newList = new ArrayList<Tweet>();
			newList.add(t);
			tweetsByDate.put(date, newList);
		} else {
			List.add(t);

		}

		tweets.add(t); // arraylist filled with all tweets
}
	}

	/**
	 * Search this Twitter for the latest Tweet of a given author. If there are no
	 * tweets from the given author, then the method returns null. O(1)
	 */
	public Tweet latestTweetByAuthor(String author) {
		return latestTweets.get(author);

	}

	/**
	 * Search this Twitter for Tweets by `date' and return an ArrayList of all such
	 * Tweets. If there are no tweets on the given date, then the method returns
	 * null. O(1)
	 */
	public ArrayList<Tweet> tweetsByDate(String date) {
		return tweetsByDate.get(date);

		// ADD CODE ABOVE HERE
	}

	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
       
    	 MyHashTable<String,Integer> lifeSaver = new MyHashTable<String,Integer>(tweets.size()*2);
    	 for(int i = 0 ; i<tweets.size();i++) {      
    	        String message =tweets.get(i).getMessage();
    	    	ArrayList<String>  wordsInTweet = getWords(message);     //I know have an arrayList of all words in the tweets
    	
    	    	MyHashTable<String,Integer> countedWords =  new MyHashTable<String,Integer>(wordsInTweet.size()*2);
    	    	
    	    	for(int k = 0 ; k<wordsInTweet.size() ;k++) {
    	    		String wordInTweet = wordsInTweet.get(k).toLowerCase();
    	    		if(nahWords.get(wordInTweet)==null && wordInTweet.length()!=0){
    	    			
    	    			if(countedWords.get(wordInTweet)==null){
    	    			Integer count = lifeSaver.get(wordInTweet);
    	    			if (count == null) {
    	    				count=0;
    	    			}
    	    			lifeSaver.put(wordInTweet,count+1);
    	    			countedWords.put(wordInTweet, 1);
    	    			}
    	
    	    	}	
    	    		
    	    	}
    	    	
    	    	
    	    	
    	 }
   /* 	for(HashPair<String,Integer> h : lifeSaver) {
    		System.out.println(h.getKey() + ";" + h.getValue());
    	}
   */ 	
    	 return MyHashTable.fastSort(lifeSaver);
    }
	/**
	 * An helper method you can use to obtain an ArrayList of words from a String,
	 * separating them based on apostrophes and space characters. All character that
	 * are not letters from the English alphabet are ignored.
	 */
	private static ArrayList<String> getWords(String msg) {
		msg = msg.replace('\'', ' ');
		String[] words = msg.split(" ");
		ArrayList<String> wordsList = new ArrayList<String>(words.length);
		for (int i = 0; i < words.length; i++) {
			String w = "";
			for (int j = 0; j < words[i].length(); j++) {
				char c = words[i].charAt(j);
				if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
					w += c;

			}
			wordsList.add(w);
		}
		return wordsList;
	}

}
