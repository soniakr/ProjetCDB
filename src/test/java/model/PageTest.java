package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class PageTest {
	
    private Page page = Mockito.mock(Page.class);
    
    private static final int totalPages=500;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void testCalculFirstLine() {
	
		//Mockito.when(page.calculFirstLine()).thenReturn(0);
		int result = page.calculFirstLine();
		int expected = (page.getNumberPage()-1) * page.getMaxLines();
		
		assertEquals(expected, result);
	}

	@Test
	public void testHasPrevious() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPreviousPage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNextPage() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasNext() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTotalPages() {
		fail("Not yet implemented");
	}

}
