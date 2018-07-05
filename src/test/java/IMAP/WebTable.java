package IMAP;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


public class WebTable {
	
	private WebElement _webTable;
	
	public void set_webTable(WebElement _webTable)
	{
		this._webTable = _webTable;
	}
	
	public WebTable(WebElement webTable)
	{
		set_webTable(webTable);
	}
	
	public WebElement get_webTable()
	{
		return _webTable;
	}
	
	public int getRowCount()
	{
		
		List<WebElement> tableRows = _webTable.findElements(By.tagName("tr"));
		return tableRows.size();
		
	}
	
	public int getColumnCount(int startRow)
	{
		
		List<WebElement> tableRows = _webTable.findElements(By.tagName("tr"));
		WebElement headerRow = tableRows.get(startRow);
		List<WebElement> tableCols = headerRow.findElements(By.tagName("td"));
		return tableCols.size();
		
	}
	
	public WebElement getCellEditor(int rowIdx, int colIdx, int editorIdx) throws NoSuchElementException
	{
		try {
			
			List<WebElement> tableRows = _webTable.findElements(By.tagName("tr"));
			WebElement currentRow = tableRows.get(rowIdx-1);
			List<WebElement> tableCols = currentRow.findElements(By.tagName("td"));
			WebElement cell = tableCols.get(colIdx-1);
			WebElement cellEditor = cell.findElements(By.tagName("input")).get(editorIdx);
			return cellEditor;
		}
		catch(NoSuchElementException e) {
			throw new NoSuchElementException("Failed to get cell editor");
		}
	}
	
	public String getCellData(int rowIDx, int colIdx) throws NoSuchElementException
	{
		try
		{
			List<WebElement> tableRows = _webTable.findElements(By.tagName("tr"));
			WebElement currentRow = tableRows.get(rowIDx-1);
			List<WebElement> tableCols = currentRow.findElements(By.tagName("td"));
			WebElement cell = tableCols.get(colIdx-1);
			return cell.getText();
		}
		catch(NoSuchElementException e)
		{
			throw new NoSuchElementException("Failed to get cell");
		}
	}
	
	public WebElement getCell(int rowIDx, int colIdx) throws NoSuchElementException
	{
		try
		{
			List<WebElement> tableRows = _webTable.findElements(By.tagName("tr"));
			WebElement currentRow = tableRows.get(rowIDx-1);
			List<WebElement> tableCols = currentRow.findElements(By.tagName("td"));
			WebElement cell = tableCols.get(colIdx-1);
			return cell;
		}
		catch(NoSuchElementException e)
		{
			throw new NoSuchElementException("Failed to get cell");
		}
	}
	
}