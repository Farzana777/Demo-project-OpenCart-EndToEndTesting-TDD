package testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.AccountRegistrationPage;
import pageObjects.CheckoutPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import pageObjects.SearchPage;
import pageObjects.ShoppingCartPage;
import testBase.BaseClass;

public class TC_002_EndToEndTest extends BaseClass {
	
	@Test
	public void endToendTest() throws InterruptedException
	{
		SoftAssert myassert=new SoftAssert();
		
	//Account Registration
	HomePage hp = new HomePage(driver);
	hp.clickMyAccount();
	hp.clickRegister();
	
	AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
	regpage.setFirstName(randomeString().toUpperCase());
	regpage.setLastName(randomeString().toUpperCase());
	
	String email=randomeString() + "@gmail.com";
	regpage.setEmail(email);// randomly generated the email
			
	regpage.setTelephone("123456789");
	
	regpage.setPassword("test123");
	regpage.setConfirmPassword("test123");
	regpage.setPrivacyPolicy();
	regpage.clickContinue();
	Thread.sleep(3000);

	String confmsg = regpage.getConfirmationMsg();
	System.out.println(confmsg);
	
	myassert.assertEquals(confmsg, "Your Account Has Been Created!"); //validation
	
	MyAccountPage mc=new MyAccountPage(driver);
	mc.clickLogout();
	Thread.sleep(3000);
	
	
	//Login
	hp.clickMyAccount();
	hp.clickLogin();
	
	LoginPage lp=new LoginPage(driver);
	lp.setEmail(email);
	lp.setPassword("test123");
	lp.clickLogin();
		
	
	System.out.println("Going to My Account Page? "+ mc.isMyAccountPageExists());
	myassert.assertEquals(mc.isMyAccountPageExists(), true); 

	
	//search & add product to cart
	hp.enterProductName("iPhone");
	hp.clickSearch();
	
	SearchPage sp=new SearchPage(driver);
	
	if(sp.isProductExist("iPhone"))
	{
		sp.selectProduct("iPhone");
		sp.setQuantity("2");
		sp.addToCart();
		
	}
	Thread.sleep(3000);
	System.out.println("Added product to cart ? "+ sp.checkConfMsg());
	myassert.assertEquals(sp.checkConfMsg(),true);
		
		
	//Shopping cart
	ShoppingCartPage sc=new ShoppingCartPage(driver);
	sc.clickItemsToNavigateToCart();
	sc.clickViewCart();
	Thread.sleep(3000);
	String totprice=sc.getTotalPrice();
	System.out.println("total price is shopping cart: "+totprice);
	myassert.assertEquals(totprice, "$246.40");   //total price validation
	sc.clickOnCheckout(); //navigate to checkout page
	Thread.sleep(3000);
	
	
	//Checkout Product
	CheckoutPage ch=new CheckoutPage(driver);
	
	ch.setfirstName(randomeString().toUpperCase());
	Thread.sleep(1000);
	ch.setlastName(randomeString().toUpperCase());
	Thread.sleep(1000);
	ch.setaddress1("address ABC");
	Thread.sleep(1000);
	ch.setaddress2("address XYZ");
	Thread.sleep(1000);
	ch.setcity("Edmonton");
	Thread.sleep(1000);
	ch.setpin("777777");
	Thread.sleep(1000);
	ch.setCountry("Canada");
	Thread.sleep(1000);
	ch.setState("Alberta");
	Thread.sleep(1000);
	ch.clickOnContinueAfterBillingAddress();
	Thread.sleep(1000);
	ch.clickOnContinueAfterDeliveryAddress();
	Thread.sleep(1000);
	ch.setDeliveryMethodComment("comments.........testing");
	Thread.sleep(1000);
	ch.clickOnContinueAfterDeliveryMethod();
	Thread.sleep(1000);
	ch.selectTermsAndConditions();
	Thread.sleep(1000);
	ch.clickOnContinueAfterPaymentMethod();
	Thread.sleep(2000);
	
	String total_price_inOrderPage=ch.getTotalPriceBeforeConfOrder();
	System.out.println("total price in confirmed order page:"+total_price_inOrderPage);
	myassert.assertEquals(total_price_inOrderPage, "$207.00");
		
	myassert.assertAll();
}

}