package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.formation.cbd.model.Page;

public class PageTest {
	
    private Page page = Mockito.mock(Page.class);
    
    private static final int totalLines=500;

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
		
		Page p = Mockito.spy(new Page());
		Mockito.doReturn(1).when(page).getNumberPage();
		boolean result = p.hasPrevious();
		assertFalse("Success hasPrevious",result);
	}

	@Test
	public void testGetPreviousPage() {
		
		//tester qu'on ne peut pas aller avant 1
		Page p = Mockito.spy(new Page());
		Mockito.doReturn(false).when(page).hasPrevious();
		int num = p.getNumberPage();
		assertEquals(1,num);
		
		//On peut voir la page precedente 
		Mockito.doReturn(2).when(page).getNumberPage();
		int number = p.getPreviousPage();
		assertEquals(1,number);
		
	}

	@Test
	public void testGetNextPage() {
		
		Page p = Mockito.spy(new Page());
	
		//tester qu'on passe bien à la page suivante
		Mockito.doReturn(50).when(p).getTotalPages(totalLines);
		Mockito.doReturn(true).when(p).hasNext();
		int nbNext = p.getNextPage();
		assertEquals(2,nbNext);
		
		//Aprés maxPages on ne peut plus avancer
		int total = p.getTotalPages(totalLines);
		p.setNumberPage(total);
		Mockito.doReturn(false).when(p).hasNext();
		int number = p.getNextPage();
		assertEquals(total,number);
	}
	
	@Test
	public void testGetTotalPages() {
		Page p = Mockito.spy(new Page());
		int pages = p.getTotalPages(totalLines);
		assertEquals(50,pages);
	}

	@Test
	public void testHasNext() {
		Page p = Mockito.spy(new Page());
		Mockito.doReturn(50).when(p).getNbTotalPages();
		Mockito.doReturn(1).when(p).getNumberPage();
		boolean result = p.hasNext();
		assertTrue("Success hasNext",result);
	}

}
