import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SeleniumTest {
	/**
	 * A helper function to easily click links
	 * @param driver The driver which is being used
	 * @param linkText The text of the link
	 */
	private static void clickLink(WebDriver driver, String linkText) {
		driver.findElement(By.linkText(linkText)).click();
	}

	/**
	 * Checks if a download link is valid
	 * @param url The url to check
	 * @return true iff the link is valid
	 */
	private static boolean checkLink(String url) {
		try {
			URL urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
			if (conn.getResponseCode() == 200) {
				// We chose 100 because thats a little larger than a error message html
				if (Integer.valueOf(conn.getHeaderField("Content-Length")) > 100) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Traverses the website and creates a assignment for download
	 * @param driver The driver to use
	 */
	private void createAssignment(WebDriver driver) {
		driver.get("http://localhost:8088/smartass-dev/");

		clickLink(driver, "HOME");
		clickLink(driver, "[create new]");

		driver.findElement(By.id("items0.value")).sendKeys("A test assignment");
		driver.findElement(By.name("_eventId_ok")).click();

		driver.findElement(By.id("addQuestion")).click();

		// Click the checkbox
		driver.findElement(By.id("selectedIds4")).click();
		driver.findElement(By.name("_eventId_add")).click();

		driver.findElement(By.name("_eventId_execute")).click();

		driver.findElement(By.name("_eventId_makePdf")).click();
	}

	@Test
	public void testDownloads() {
		WebDriver driver = new FirefoxDriver();
		createAssignment(driver);

		// Get the url for the download file
		String url = driver.findElement(By.linkText("Download all as .zip archive")).getAttribute("href");
		assertEquals(true, checkLink(url));

		url = driver.findElement(By.linkText("Questions")).getAttribute("href");
		assertEquals(true, checkLink(url));

		url = driver.findElement(By.linkText("Solutions")).getAttribute("href");
		assertEquals(true, checkLink(url));

		url = driver.findElement(By.linkText("Answers")).getAttribute("href");
		assertEquals(true, checkLink(url));

		driver.quit();
	}
}
