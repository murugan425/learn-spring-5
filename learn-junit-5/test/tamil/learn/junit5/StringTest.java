/**
 * 
 */
package tamil.learn.junit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author murugan425
 *
 */
class StringTest {

	@BeforeAll
	static void beforeAll() {
		System.out.println("Do the required initializations here in this BEFOREALL method.");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("Execute the required closure here in this AFTERALL method.");
	}
	
	@BeforeEach
	void beforeEachMtd(TestInfo info) {
		System.out.println("Method executed BEFORE each Test cases.," + info.getDisplayName());
	}
	
	@AfterEach
	void afterEachMtd(TestInfo info) {
		System.out.println("Method executed AFTER each Test cases.," + info);
	}
	
	@Test
	@DisplayName("Method to Test String Length")
	void testStrLength() {
		int actualLength = "Murugan".length();
		int expLength = 7;
		assertEquals(expLength, actualLength);
	}
	
	@Test
	@DisplayName("Method to Test ToUpperCase")
	void testUpperCase() {
		String actualResult = "Murugan".toUpperCase();
		assertEquals("MURUGAN", actualResult);
		assertNotNull(actualResult);
	}

	@Test
	@DisplayName("Method to Test Array")
	void testArrays() {
		String names = "Murugan Tamil Teju";
		String actualNames[] = names.split(" ");
		String expectedResult[] = new String[] {"Murugan","Tamil","Teju"};
		assertArrayEquals(expectedResult, actualNames);	
	}
	
	@Test()
	@DisplayName("Method to Test Exception scenario for a Null String Length")
	void testNullStrLength() {
		String name = null;		
		assertThrows(NullPointerException.class, () -> {name.length();});
	}
	
	@ParameterizedTest
	@ValueSource(strings= {"ABCD", "ABC", "AB", "ZXCVBN"})
	void testNonEmptyString(String inputTestStr) {
		assertTrue(inputTestStr.length() > 0);
	}
	
	@ParameterizedTest
	@CsvSource(value= {"Murugan, MURUGAN","Tamil, TAMIL","abcde, ABCDE","'',''"})
	void testMultipleUpperCase(String inputWord, String capitalizedResult) {
		assertEquals(capitalizedResult, inputWord.toUpperCase());
	}
	
	@ParameterizedTest(name = "{0} length is {1}")
	@CsvSource(value= {"Murugan, 7","Tamil, 5","abcde, 5","'',0"})
	void testStringsLength(String inputWord, int expectedLength) {
		assertEquals(expectedLength, inputWord.length());
	}
}
