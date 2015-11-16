package com.nnm.emenu.client.utils.ui;

import java.util.ArrayList;

public class SimpleAutoCompletionItems implements CompletionItems {
	  private ArrayList<String> completions;

	  public SimpleAutoCompletionItems(ArrayList<String> items)
	  {
	    completions = items;
	  }

	  public ArrayList<String> getCompletionItems(String match) {
	    ArrayList<String> matches = new ArrayList<String>();
	    for (int i = 0; i < completions.size(); i++) {
	      if (completions.get(i).toLowerCase().startsWith(match.toLowerCase())) {
	        matches.add(completions.get(i));
	      }
	    }
	    ArrayList<String> returnMatches = new ArrayList<String>();
	    for(int i = 0; i < matches.size(); i++)
	    {
	      
	      returnMatches.add(matches.get(i));
	    }
	    return matches;
	  }
	} 