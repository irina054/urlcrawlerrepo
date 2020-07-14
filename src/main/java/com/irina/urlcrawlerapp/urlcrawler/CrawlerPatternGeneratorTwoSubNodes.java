package com.irina.urlcrawlerapp.urlcrawler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.irina.urlcrawlerapp.exception.InputValidationError;
import com.irina.urlcrawlerapp.exception.ValidationError;

public class CrawlerPatternGeneratorTwoSubNodes implements CrawlerPatternGenerator{

		private static final int MIN_FIRST_LEVEL_ENTRIES = 1;
		private static final int MAX_FIRST_LEVEL_ENTRIES = 1000;
		private static final int MIN_TREE_DEPTH = 1;
		private static final int MAX_TREE_DEPTH = 1000;
		private static final String DEFAULT_PREFIX_DELIMITER = "-";
		private static final String DEFAULT_INSIDE_DELIMITER = "-";

		private static final String DEFAULT_SUFFIX_URL = ".com";

		private int firstLevel; 
		private int treeDepth;
		private String prefixDelimiter;
		private String insideDelimiter;
		private String urlPrefix;
		private String urlSuffix;

		@Override
		public List<String> getURLsToCrawler(String url, int depth) throws ValidationError {
		
			//TODO check exact format of URL input
			//     meanwhile work with assumption that URL is transfered as a "http://from-root", at 1st phase I will concat  all possible generated patterns and URL suffix as a constant
			//TODO parse input URL : prefix and suffix; integrate patterns between
			//TODO validate input URL format: must be correct URL format
		
			if (url == null || url.isEmpty()) {
				throw new InputValidationError(String.format("Wrong input URL : can't be null or empty."));
			}
			return generatePatterns(depth, depth,  url, DEFAULT_SUFFIX_URL, null,  null);
			
		}
		
		public List<String> generatePatterns(int firstLevel, int treeDepth, String urlPrefix, String urlSuffix, 
												String prefixDelimiter, String insideDelimiter) throws ValidationError{
			
			//TODO: allow configuration of prefixDelimiter and insideDelimiter as a system parameters if will required
			if (prefixDelimiter == null) {
				this.prefixDelimiter = DEFAULT_PREFIX_DELIMITER;
			}
			if (insideDelimiter == null) {
				this.insideDelimiter = DEFAULT_INSIDE_DELIMITER;
			}
			validateInput(firstLevel, treeDepth, prefixDelimiter, insideDelimiter);
			this.firstLevel = firstLevel;
			this.treeDepth = treeDepth;
			this.urlPrefix = urlPrefix;
			this.urlSuffix = urlSuffix;
			List<String> result = generatePatternBasicAlg();			
			return result;
		}

		/*
		 * generate patterns according to given width of first level, and given depthTree, 
		 * each node will have next level 2 kids: "-1" and "-2".
		 */
		private List<String> generatePatternBasicAlg() {
			List<String> result = new ArrayList<String>();
			
			Queue<String> queue = new LinkedList<String>();
			
			//insert first level of elements
			for (int i= 1; i <= firstLevel; i++) {
				queue.add(String.format("%s%d", prefixDelimiter, i));
			}
			//separate each level with delimiter element = null
			queue.add(null);
			
			//handle current level and prepare next level, 1st level already inside
			for (int i= 1; i <= treeDepth; i++) {
				String element = null;
				while (!queue.isEmpty()) {
						element = queue.poll();					
	
						if (element != null) {
							queue.add(String.format("%s%s%d", element, insideDelimiter, 1));
							queue.add(String.format("%s%s%d", element, insideDelimiter, 2));
							result.add(String.format("%s%s%s", urlPrefix, element, urlSuffix));
						}else {
							break;
						}
				}
				queue.add(null);
			}
			return result;
		}

		private void validateInput(int firstLevel, int treeDepth, String prefixDelimiter, String insideDelimiter) throws InputValidationError{
			if ((firstLevel < MIN_FIRST_LEVEL_ENTRIES || firstLevel > MAX_FIRST_LEVEL_ENTRIES ) || 
			   (treeDepth < MIN_TREE_DEPTH) || (treeDepth > MAX_TREE_DEPTH))
				throw new InputValidationError (String.format("Wrong input for crawler pattern : firstLevel: %d ,  treeDepth: %d. It limited by : firstLevel: between : %d and %d, treeDepth: %d and %d", 
						firstLevel, treeDepth,  MIN_FIRST_LEVEL_ENTRIES, MAX_FIRST_LEVEL_ENTRIES,MIN_TREE_DEPTH , MAX_TREE_DEPTH));
			   
		}
		
		
}
