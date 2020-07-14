package com.irina.urlcrawlerapp.urlcrawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.irina.urlcrawlerapp.exception.InputValidationError;
import com.irina.urlcrawlerapp.exception.ValidationError;
import com.irina.urlcrawlerapp.urlcrawler.CrawlerPatternGeneratorTwoSubNodes;

class CrawlerPatternGeneratorTest {

	@Test
	void testGeneratePatternsSimple1() throws ValidationError {
		CrawlerPatternGeneratorTwoSubNodes generator  =  new CrawlerPatternGeneratorTwoSubNodes();
		List<String> result = generator.generatePatterns(3,  3, "", "",  null,  null);
		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add("-1");
		expectedResult.add("-2");
		expectedResult.add("-3");

		//2nd level
		expectedResult.add("-1-1");
		expectedResult.add("-1-2");

		expectedResult.add("-2-1");
		expectedResult.add("-2-2");

		expectedResult.add("-3-1");
		expectedResult.add("-3-2");

		//3rd level
		expectedResult.add("-1-1-1");
		expectedResult.add("-1-1-2");

		expectedResult.add("-1-2-1");
		expectedResult.add("-1-2-2");

		expectedResult.add("-2-1-1");
		expectedResult.add("-2-1-2");

		expectedResult.add("-2-2-1");
		expectedResult.add("-2-2-2");

		expectedResult.add("-3-1-1");
		expectedResult.add("-3-1-2");
		expectedResult.add("-3-2-1");
		expectedResult.add("-3-2-2");

		System.out.println("Expected crawler patterns are: " + expectedResult);
		System.out.println("Actual   crawler patterns are: " + result);
		assertEquals(expectedResult.size(), result.size());
		//fail("Not yet implemented");
	}

	@Test
	void testGeneratePatternsSimpleURL() throws ValidationError {
		CrawlerPatternGeneratorTwoSubNodes generator  =  new CrawlerPatternGeneratorTwoSubNodes();
		List<String> result = generator.generatePatterns(3,  2, "http://from-root", ".com",  null,  null);
		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add("http://from-root-1.com");
		expectedResult.add("http://from-root-2.com");
		expectedResult.add("http://from-root-3.com");

		//2nd level
		expectedResult.add("http://from-root-1-1.com");
		expectedResult.add("http://from-root-1-2.com");

		expectedResult.add("http://from-root-2-1.com");
		expectedResult.add("http://from-root-2-2.com");

		expectedResult.add("http://from-root-3-1.com");
		expectedResult.add("http://from-root-3-2.com");

		System.out.println("Expected crawler patterns are: " + expectedResult);
		System.out.println("Actual   crawler patterns are: " + result);
		assertEquals(expectedResult, result);
		//fail("Not yet implemented");
	}


	@Test
	void testGeneratePatternsSimpleURL_Negative() throws ValidationError {
		CrawlerPatternGeneratorTwoSubNodes generator  =  new CrawlerPatternGeneratorTwoSubNodes();
		List<String> result = generator.generatePatterns(3,  2, "http://from-root", ".com",  null,  null);
		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add("http://from-root-1.com");
		expectedResult.add("http://from-root-2.com");
		expectedResult.add("http://from-root-3.com");

		//2nd level
		expectedResult.add("http://from-root-1-1.com");
		expectedResult.add("http://from-root-1-2WRONGPATTERN.com");

		expectedResult.add("http://from-root-2-1.com");
		expectedResult.add("http://from-root-2-2.com");

		expectedResult.add("http://from-root-3-1.com");
		expectedResult.add("http://from-root-3-2.com");

		System.out.println("Expected crawler patterns are: " + expectedResult);
		System.out.println("Actual   crawler patterns are: " + result);
		
		assertNotEquals(expectedResult, result);
		//fail("Not yet implemented");
	}

	
	@Test
	void testGeneratePatternsSimple_Minimum() throws ValidationError {
		CrawlerPatternGeneratorTwoSubNodes generator  =  new CrawlerPatternGeneratorTwoSubNodes();
		List<String> result = generator.generatePatterns(1,  1,  "", "",  null,  null);
		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add("-1");

		System.out.println("Expected crawler patterns are: " + expectedResult);
		System.out.println("Actual   crawler patterns are: " + result);
		assertEquals(expectedResult.size(), result.size());
		//fail("Not yet implemented");
	}

	@Test
	void testGeneratePatternsSimple_NegativeWrongInput() throws ValidationError {
		CrawlerPatternGeneratorTwoSubNodes generator  =  new CrawlerPatternGeneratorTwoSubNodes();
		Exception e = assertThrows(InputValidationError.class,  () -> { generator.generatePatterns(0,  0,  "", "", null,  null);});
		
		//TODO check error message with all its parameters
		System.out.println("Retrieved error message is : " + e.getMessage());//List<String> expectedResult = new ArrayList<String>();
				
		//fail("Not yet implemented");
	}

}
