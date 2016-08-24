package com.ihg.selenium.framework.page.impl.www;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ihg.model.Brand;
import com.ihg.selenium.ByIhgSelenium;
import com.ihg.selenium.DateUtil;
import com.ihg.selenium.framework.annotation.SupportedBrands;
import com.ihg.selenium.framework.page.impl.GlobalPageObject;
import com.ihg.selenium.framework.pagecontracts.ReservationConfirmationPage;
import com.ihg.selenium.framework.pagecontracts.modules.BrandBarModule;
import com.ihg.selenium.framework.pagecontracts.modules.IhgAccountModule;
import com.ihg.selenium.framework.pagecontracts.modules.PVAModule;
import com.ihg.selenium.framework.pagecontracts.modules.UhfTimeOutModule;
import com.ihg.selenium.framework.util.SeleniumPageComponentName;
import com.ihg.selenium.framework.util.xlat.XlatPageElements;

@Component(SeleniumPageComponentName.DEFAULT_RESERVATION_CONFIRMATION_PAGE)
@SupportedBrands({ Brand.IHG, Brand.HI, Brand.HIEX, Brand.EVENHOTELS, Brand.HOLIDAYINN_CLUB_VACATIONS, Brand.HOLIDAYINN_RESORTS, Brand.PAL, Brand.CROWNEPLAZA, Brand.CANDLEWOOD,
    Brand.STAYBRIDGE, Brand.INDIGO })
public class DefaultReservationConfirmationPage extends GlobalPageObject implements ReservationConfirmationPage {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultReservationConfirmationPage.class);

    private static final String RESERVATION_CONFIRMATION_URL_TOKEN = "singlereservationsummary";
    private static final String RESERVATION_CONFIRMAITON_INITIAL_LOAD_URL_TOKEN = "reservationconfirmation";

    private static final By BY_CONFIRMATION_NUMBER = By.cssSelector(".confirmationNumber");

    private static final By BY_RES_PRICE_SUMMARY_DETAILS = By.className("resPriceSummaryDetails");
    private static final By BY_RES_PRICE_SUMMARY_LINES = By.cssSelector(".resPriceSummaryDetails .rightCol");
    private static final By BY_DUPLICATE_RESERVATION_LINK = ByIhgSelenium.attribute("duplicateReservationSID");
    private static final By BY_CANCEL_RESERVATION_LINK = ByIhgSelenium.attribute("cancelReservationSID");
    private static final By BY_MAKE_ANOTHER_RESERVATION_LINK = ByIhgSelenium.attribute("makeReservationSID");
    private static final By BY_VIEW_ALL_RESERVATIONS_LINK = ByIhgSelenium.attribute("viewReservationsSID");

    private static final By BY_PRINT_CONFIRMATION_LINK = By.cssSelector("a[id='printConfDetails'][class='email']");
    private static final By BY_SMS_CONFIRMATION_LINK = By.cssSelector("a[id='smsConfirmation'][class='sms']");
    private static final By BY_CHANGE_RESERVATION_LINK = ByIhgSelenium.attribute("changeReservationSID");
    private static final By BY_CHANGE_RESERVATION_BUTTON = By.cssSelector("#resButton a[class*=\"actionBtn\"]");
    private static final By BY_JOIN_NOW_BUTTON = ByIhgSelenium.attribute("joinBtnSID");
    private static final By BY_DUPLICATE_RESERVATION_MESSAGE = By.cssSelector(".duplicateMessage");
    private static final By BY_HOTEL_NAME = By.cssSelector("#hotelInfo dl dd a");
    private static final By BY_HOTEL_ADDRESS = By.cssSelector("#hotelInfo dl dd p");
    private static final By BY_HOTEL_INFO = By.cssSelector("#hotelInfo dl dd a");
    private static final By BY_STAY_PAGE_CATEGORY = By.cssSelector("#sp_preferences .sp_category");
    private static final By BY_QUICK_ENROLL_LINK = ByIhgSelenium.attribute("associationOnJoinSID");

    private static final By BY_QUICK_ENROLL_PIN = ByIhgSelenium.attribute("quickEnrollTempPinSID");
    private static final By BY_QUICK_ENROLL_PCRN = ByIhgSelenium.attribute("enrollPCRNumberSID");
    private static final By BY_RATE_TYPE = By.cssSelector("#confRateSummary dl dd");
    private static final By BY_ROOM_TYPE = By.cssSelector(".staySummaryParent div[class=\"dataTxt\"]");

    // New addition to confirmation page - content within the lightbox is pulled
    // from Day
    private static final String LIGHT_BOX_SELECTOR = "#AppDownloadLightbox";

    private static final String LIGHT_BOX_CONTENT_SELECTOR = "#AppDownloadLightboxContent";
    private static final By BY_LIGHT_BOX = By.cssSelector(LIGHT_BOX_SELECTOR);
    private static final By BY_LIGHT_BOX_CONTENT = By.cssSelector(LIGHT_BOX_CONTENT_SELECTOR);
    private static final By BY_AMBASSADOR_ENROLLMENT_INFO = By.className("ambassadorProgramme");
    private static final By BY_IMPLICIT_SIGN_IN = By.cssSelector(".associationOnSignIn");
    private static final By BY_STAY_SUMMARY = By.cssSelector("#confDetailStaySummary div[class=\"dataTxt\"]");
    private static final By BY_BOX_CONTAINER = ByIhgSelenium.attribute("link-reservation-containerSID");
    private static final By BY_ASSOCIATE_NUMBER = ByIhgSelenium.attribute("associationOnSignIn");
    private static final By BY_ERROR_MESSAGE = ByIhgSelenium.attribute("link-reservation-error");
    private static final By BY_CONTINUE_BUTTON = ByIhgSelenium.attribute("continue-link-reservation");
    private static final By BY_SUCCESS_MESSAGE_ASSOCIATED = ByIhgSelenium.attribute("thanksForAddingSID");
    private static final By BY_BOOKING_DIRECT = ByIhgSelenium.attribute("thanksForBookingSID");
    private static final By BY_QUICK_ENROLL_WELCOME = ByIhgSelenium.attribute("welcomeQuickEnrollSID");

    private static final By BY_ADD_TO_TRIP_IT = By.id("AddtoTripit");
    private static final By BY_ERROR_BLOCK1 = By.id("messageEmail");
    private static final By BY_ERROR_BLOCK2 = By.id("errorBlock");

    private static final By BY_TRIPIT_SPINNER = By.className("tripItLoadingSpinner");
    private static final By BY_TRIPIT_SUCCESS_MESSAGE = By.className("tripItSuccessMessage");
    private static final By BY_HOTEL_STAY_DETAILS_LINK = ByIhgSelenium.attribute("hotelStayDetailsLinkSID");
    private static final By BY_CAR_RES_SUMMARY_CONTAINER = ByIhgSelenium.attribute("carResSummaryContainerSID");
    private static final By BY_CAR_RES_DETAILS_LINK = ByIhgSelenium.attribute("carResDetailsLinkSID");
    private static final By BY_CAR_RES_CONF_NUM = ByIhgSelenium.attribute("carResConfirmationNumberSID");
    private static final By BY_CAR_RES_ASSISTANCE_NUM = ByIhgSelenium.attribute("carResAssistanceNumberSID");
    private static final By BY_CAR_RES_PICKUP_DATE = ByIhgSelenium.attribute("carResPickupDateSID");
    private static final By BY_CAR_RES_DROPOFF_DATE = ByIhgSelenium.attribute("carResDropoffDateSID");
    private static final By BY_CAR_RES_PICKUP_LOCATION = ByIhgSelenium.attribute("carResPickupLocationSID");
    private static final By BY_CAR_RES_CAR_TYPE = ByIhgSelenium.attribute("carResCarTypeSID");
    private static final By BY_CAR_RES_ADD_ONS = ByIhgSelenium.attribute("carResAddOnSID");
    private static final By BY_CAR_RES_ESTIMATED_TOTAL = ByIhgSelenium.attribute("carResEstimatedTotalSID");
    private static final By BY_MANAGE_CAR_RES_BUTTON = ByIhgSelenium.attribute("manageCarReservationButtonSID");
    private static final By BY_CAR_RES_PROMO_CONTAINER = ByIhgSelenium.attribute("carPromoContainerSID");
    private static final By BY_CAR_RES_SPINNER = ByIhgSelenium.attribute("loadingIconSID");
    private static final By BY_CAR_RES_PROMO_IMAGE = ByIhgSelenium.attribute("carPromoImageSID");
    private static final By BY_CAR_RES_PROMO_DESCRIPTION = ByIhgSelenium.attribute("carPromoDescriptionSID");
    private static final By BY_CAR_RES_PROMO_RATE = ByIhgSelenium.attribute("carPromoRateSID");
    private static final By BY_ADD_CAR_TO_STAY_BUTTON = ByIhgSelenium.attribute("addCarToStayButtonSID");
    private static final By BY_IHG_MANAGE_CAR_RESERVATION_SUMMARY_BTN = ByIhgSelenium.attribute("manageCarReservationButtonSID");
    private static final By BY_CANCELLATION_POLICY_TEXT = ByIhgSelenium.attribute("cancellationPolicySID");
    private static final By BY_PCR_DETAILS_ACCT_BALANCE_SID = ByIhgSelenium.attribute("pcrDetailsAccountBalanceSID");
    private static final By BY_PNC_RECEIPT_BLOCK_SID = ByIhgSelenium.attribute("pncReceiptBlockSID");
    private static final By BY_PNC_RECEIPT_CC_SID = ByIhgSelenium.attribute("pncReceiptCCSID");
    private static final By BY_PNC_RECEIPT_RATE_SID = ByIhgSelenium.attribute("pncReceiptRateSID");
    private static final By BY_PNC_RECEIPT_AUTH_NUMBER_SID = ByIhgSelenium.attribute("pncReceiptAuthNumSID");
    private static final By BY_PNC_RECEIPT_TIMESTAMP_SID = ByIhgSelenium.attribute("pncReceiptTimestampSID");

    // TODO: Remove duplicate selectors and condense related methods if possible
    private static final By BY_PCR_INFO_ACCT_BALANCE_SID = ByIhgSelenium.attribute("pcrDetailsAccountBalanceSID");
    // private static final By BY_PCR_DETAILS_ACCT_BALANCE_SID =
    // ByIhgSelenium.attribute("pcrDetailsAccountBalanceSID");

    private static final String CSS_CHECK_IN_DATE_FIELD = "#confDetailsChkinChkoutDate div[class=\"headingText\"] span";
    private static final String CSS_CHECK_OUT_DATE_FIELD = "#confDetailsChkinChkoutDate div[class=\"headingText checkOut\"] span";
    private static final By BY_CHECK_IN_DATE_FIELD = By.cssSelector(CSS_CHECK_IN_DATE_FIELD);
    private static final By BY_CHECK_OUT_DATE_FIELD = By.cssSelector(CSS_CHECK_OUT_DATE_FIELD);
    private static final By BY_AVERAGE_NIGHTLY_RATE = By.cssSelector(".resAverageNightRate .rightCol, .resAverageNightRate .rigtCol");
    private static final By BY_EDIT_STAY_PREFERENCES_LINK = By.id("stay_preferences_uri");
    private static final By BY_DOUBLECLICK_ELEMENT = By.cssSelector("iframe[src*='doubleclick.net']");
    private static final String CURRENCY_REGEX = "\\d+[\\,\\.]?\\d+[\\,\\.]\\d+";

    private static final By BY_MOBILE_CHECKIN_IMAGE = ByIhgSelenium.attribute("mobileCheckInAvailableSID");
    private static final By BY_LOWEST_PRICE_PROMISE_LOGO = ByIhgSelenium.attribute("lpp_logo_resconfSID");

    private static final By BY_BOOK_WITH_US_ADVANTAGE_TEXT_DIV = By.cssSelector(".bpgConfirmation");
    private static final By BY_BEST_PRICE_GUARANTEE_TEXT_DIV = By.cssSelector(".LIRGConfirmationPriceSummary>div>p");

    private static final By BY_VIEW_MORE_DETAILS_LINK = By.id("viewConfDetails");
    private static final By BY_STAY_DETAILS_LAYER = By.id("confDetailsOfStay");
    private static final By BY_STAY_DETAILS_CLOSE_BUTTON_TOP = By.id("confDetailCloseBtn");
    private static final By BY_STAY_DETAILS_CLOSE_BUTTON_BOTTOM = By.id("confDetailsClose");
    private static final By BY_STAY_DETAILS_CURRENCY_LINK = By.cssSelector(".currency a.amenUsdLink");
    private static final By BY_STAY_DETAILS_ADDITIONAL_CHARGES_LABEL = By.cssSelector("#additionalCharges .priceInfo dt");
    private static final By BY_STAY_DETAILS_ADDITIONAL_CHARGES_PRICE = By.cssSelector("#additionalCharges .priceInfo dd");
    private static final By BY_STAY_DETAILS_RATE = By.cssSelector("dl:nth-of-type(2) dd");

    private static final By BY_SPECIAL_REQUEST = ByIhgSelenium.id("spcRequest1");

    private static final By BY_EMAIL_CONF_FIELD_1 = By.id("sendConfEmail");
    private static final By BY_EMAIL_CONF_FIELD_2 = By.id("emailfld");
    private static final By BY_EMAIL_CONF_FORM_CLOSE = By.className("closeEmail");
    private static final By BY_SMS_CONF_FORM_CLOSE = By.className("closeSMS");

    private static final By BY_EMAIL_SEND_BUTTON = By.className("sendEmailButn");

    private static final By BY_EMAIL_CONFIRMATION = By.id("emailConfirmation");
    private static final By BY_SEND_SUCCESS_MESSAGE = By.id("emailConfDesc");
    private static final By BY_EMAIL_CONF_LINK = By.id("emailConf");
    private static final By BY_SMS_CONF_LINK = By.id("smsConf");

    private static final By BY_STAY_PREFERENCE_SUCCESS_MESSAGE = ByIhgSelenium.attribute("successStayPrefMessageSID");

    private static final String CLASS_EVEN_HOTELS_WELLNESS_PREFS_DIV = "confirmation-panel";
    private static final By BY_EVEN_HOTELS_WELLNESS_PREFS_DIV = By.className("confirmation-panel");
    private static final By BY_EVEN_HOTELS_WELLNESS_PREFS_SHARE_BTN = By.cssSelector(String.format(".%s a.cssButton", CLASS_EVEN_HOTELS_WELLNESS_PREFS_DIV));

    private static final By BY_STAY_DETAILS_RATE_TYPE = By.xpath("//div[@id='confDetailsOfStay']//dt[contains(text(),'Rate Type:')]/../dd");
    private static final By BY_STAY_DETAILS_ROOM_TYPE = By.xpath("//div[@id='confDetailsOfStay']//dt[contains(text(),'Room Type:')]/../dd");
    private static final By BY_SMS_CONF_COUNTRY = By.id("smsConfIdd");
    private static final By BY_SMS_CONF_MOBILE_NUMBER_FIELD = By.id("sendConfSMS");
    private static final By BY_SMS_CONF_CHECKBOX = By.id("smsCheckBox");
    private static final By BY_SMS_SEND_BUTTON = By.className("closeSMSButn");
    private static final By BY_ERROR_SMS = By.cssSelector("div#messageSMS > div.errorMsg");
    private static final By BY_SUCCESS_SMS = By.cssSelector("div#messageSMS > div.successMsg");
    private static final By BY_SMS_CONF_MESSAGE = By.id("messageSMS");
    private static final By BY_SMS_CONF_TC_LINK = By.cssSelector("a[id='sms_tc_link']");
    private static final By BY_SMS_CONF_TC_HEADER_TEXT = By.cssSelector(".bar5 > h1");
    private static final By BY_POINTS_TO_BE_EARNED = ByIhgSelenium.attribute("pointsToEarnSID");
    private static final By BY_UPCOMING_POINTS_TOTAL = ByIhgSelenium.attribute("upcomingPointsTotalSID");
    private static final By BY_UPCOMING_VALUE = ByIhgSelenium.attribute("upcomingPointsValueSID");
    private static final By BY_UPCOMING_POINTS_DESCRIPTION = ByIhgSelenium.attribute("upcomingPointsDescriptionSID");
    private static final By BY_UPCOMING_POINTS_HELP_LINK = ByIhgSelenium.attribute("upcomingPointsHelpLinkSID");
    private static final By BY_DICOM_EXCHANGE_RATE = ByIhgSelenium.attribute("DICOMExchangeRateID");

    private static final By BY_BPG_LOGO = ByIhgSelenium.attribute("bpgOfferLogo");

    private static final By BY_CREDIT_CARD_GUARANTEE_LINK = ByIhgSelenium.attribute("creditCardGuaranteeOfPaymentSID");

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Autowired
    @Qualifier(SeleniumPageComponentName.DEFAULT_PVA_MODULE)
    PVAModule pvaModule;

    /**
     * Method to remove the day of the week from a date string
     *
     * @param dateWithDays
     *            date string with leading day of the week
     * @return date string with leading day of the week removed
     */
    private String cleanUpDateString(String dateWithDays) {
        return dateWithDays.split(" ", 2)[1];
    }

    @Override
    public void clickAddCarToMyStay() {
        this.findElement(BY_ADD_CAR_TO_STAY_BUTTON).click();
    }

    @Override
    public void clickAddToTripItButton() {
        this.findElement(ExpectedConditions.elementToBeClickable(BY_ADD_TO_TRIP_IT)).click();
        this.waitForInvisibility(BY_TRIPIT_SPINNER);
    }

    @Override
    public void clickAssociateNumber() {
        this.findElement(ExpectedConditions.elementToBeClickable(BY_ASSOCIATE_NUMBER)).click();
    }

    @Override
    public void clickChangeReservationButton() {
        this.clickElement(BY_CHANGE_RESERVATION_BUTTON);
    }

    @Override
    public void clickChangeReservationLink() {
        this.findElement(ExpectedConditions.elementToBeClickable(BY_CHANGE_RESERVATION_LINK)).click();
    }

    @Override
    public void clickCloseStayDetailsLayerByBottomButton() {
        this.clickElement(BY_STAY_DETAILS_CLOSE_BUTTON_BOTTOM);
    }

    @Override
    public void clickCloseStayDetailsLayerByTopButton() {
        this.clickElement(BY_STAY_DETAILS_CLOSE_BUTTON_TOP);
    }

    @Override
    public void clickContinueButton() {
        this.findElement(ExpectedConditions.elementToBeClickable(BY_CONTINUE_BUTTON)).click();
    }

    @Override
    public void clickDuplicateReservationLink() {
        this.findElement(BY_DUPLICATE_RESERVATION_LINK).click();
    }

    @Override
    public void clickEditStayPreferencesLink() {
        this.findElement(ExpectedConditions.elementToBeClickable(BY_EDIT_STAY_PREFERENCES_LINK)).click();
        this.waitUntilStayPageLoads();
    }

    @Override
    public void clickEditTempPinLink() {
        // TODO Auto-generated method stub
    }

    @Override
    public void clickEmailConfirmationLink() {
        this.clickElement(BY_EMAIL_CONFIRMATION);
    }

    @Override
    public void clickEvenHotelsWellnessPrefsShareButton() {
        this.clickElement(BY_EVEN_HOTELS_WELLNESS_PREFS_SHARE_BTN);
    }

    @Override
    public void clickHotelDetails() {
        this.clickElement(BY_HOTEL_INFO);
    }

    @Override
    public void clickHotelStayDetailsLink() {
        this.findElement(BY_HOTEL_STAY_DETAILS_LINK).click();
    }

    @Override
    public void clickJoinNowButton() {
        this.findElement(ExpectedConditions.elementToBeClickable(BY_JOIN_NOW_BUTTON)).click();
        super.waitUntilPageLoads();
    }

    @Override
    public void clickManageCarReservationButton() {
        this.findElement(BY_MANAGE_CAR_RES_BUTTON).click();
    }

    @Override
    public void clickManageCarReservationSummaryButton() {
        this.findElement(BY_IHG_MANAGE_CAR_RESERVATION_SUMMARY_BTN).click();
    }

    @Override
    public void clickPrintConfirmationLink() {
        this.clickElement(BY_PRINT_CONFIRMATION_LINK);
    }

    @Override
    public void clickQuickEnrollLink() {
        this.findElement(ExpectedConditions.elementToBeClickable(BY_QUICK_ENROLL_LINK)).click();
        // this.findElement(ExpectedConditions.elementToBeClickable(BY_JOIN_NOW_BUTTON)).click();
        super.waitUntilPageLoads();
    }

    @Override
    public void clickSendEmailConfirmationButton() {
        this.waitForVisibility(BY_EMAIL_SEND_BUTTON);
        if (this.isElementDisplayed(BY_EMAIL_SEND_BUTTON)) {
            this.clickElement(BY_EMAIL_SEND_BUTTON);
        }
    }

    @Override
    public void clickSignInRCHeader() {
        this.findElement(ExpectedConditions.elementToBeClickable(BY_BOX_CONTAINER)).findElement((BY_IMPLICIT_SIGN_IN)).click();
    }

    @Override
    public void clickSMSConfCanadaSMSTandCLink() {
        this.clickElement(BY_SMS_CONF_TC_LINK);

    }

    @Override
    public void clickSMSConfirmationButton() {

        this.clickElement(BY_SMS_SEND_BUTTON);
        this.waitForVisibility(BY_SMS_CONF_MESSAGE);
    }

    @Override
    public void clickSMSConfirmationCheckBox() {
        this.clickElement(BY_SMS_CONF_CHECKBOX);
    }

    @Override
    public void clickSMSConfirmationLink() {
        this.clickElement(BY_SMS_CONFIRMATION_LINK);
        this.waitForPopupToAppear();
    }

    @Override
    public void clickStayDetailsLayerCurrencyLink() {
        this.findElement(ExpectedConditions.elementToBeClickable(this.getStayDetailsLayer().findElement(BY_STAY_DETAILS_CURRENCY_LINK))).click();
    }

    @Override
    public void clickViewMoreDetailsLink() {
        this.clickElement(BY_VIEW_MORE_DETAILS_LINK);
    }

    @Override
    public void clickViewMoreDetailsOfYourCarReservation() {
        this.findElement(BY_CAR_RES_DETAILS_LINK).click();
    }

    @Override
    public void closeEmailConfirmationLayer() {
        WebElement element = this.findElement(BY_EMAIL_CONF_FORM_CLOSE);
        if (element.isDisplayed()) {
            element.click();
        }
    }

    @Override
    public void closeLightBox() {
        this.closeLightBoxIfVisible();
    }

    private void closeLightBoxIfVisible() {
        boolean isLightBoxPresent = false;
        try {
            LOG.debug("Checking for lightbox modal dialog .....");
            // Give 3 seconds for the lightbox to show.
            long start = System.currentTimeMillis();
            long end = start + 3 * 1000;

            while ((System.currentTimeMillis() < end) && (isLightBoxPresent == false)) {
                isLightBoxPresent = this.isElementDisplayed(BY_LIGHT_BOX) || this.isElementDisplayed(BY_LIGHT_BOX_CONTENT);
            }

        } catch (NoSuchElementException noe) {
            LOG.debug("Lightbox modal dialog not present on reservation confirmation page, proceeding with test.");
        } catch (TimeoutException toe) {
            LOG.debug("Lightbox modal dialog not present on reservation confirmation page, proceeding with test.");
        } catch (WebDriverException wde) {
            LOG.debug("Unexpected condition occured {}", wde);
        }
        if (isLightBoxPresent) {
            LOG.debug("Attempting to hide lightbox modal dialogs on reservation confirmation page");
            // String script = "$('" + LIGHT_BOX_SELECTOR + "').hide(); $('" +
            // LIGHT_BOX_CONTENT_SELECTOR + "').hide();";
            String script = "document.getElementById('AppDownloadLightbox').style.display = 'none'; document.getElementById('AppDownloadLightboxContent').style.display = 'none';";
            this.executeJavaScript(script);
            LOG.debug("Javascript execution completed.");
            this.waitForInvisibility(BY_LIGHT_BOX);
            this.waitForInvisibility(BY_LIGHT_BOX_CONTENT);
            LOG.debug("Modal elements no longer visible on the page");
        } else {
            LOG.debug("Modal elements are not present on the page, proceeding with test");
        }
    }

    @Override
    public void closeSMSConfirmationLayer() {
        WebElement element = this.findElement(BY_SMS_CONF_FORM_CLOSE);
        if (element.isDisplayed()) {
            element.click();
        }
    }

    @Override
    public String getAdultsCountText() {
        WebElement adultsCount = this.findElements(BY_STAY_SUMMARY).get(2);
        return this.getCountValue(adultsCount.getText(), 0);
    }

    private WebElement getAmbassadorInformationSection() {
        return this.findElement(ExpectedConditions.presenceOfElementLocated(BY_AMBASSADOR_ENROLLMENT_INFO));

    }

    @Override
    public String getAverageNightlyRate() {
        return this.findElement(BY_AVERAGE_NIGHTLY_RATE).getText();
    }

    @Override
    public String getAverageNightlyRateAmountOnly() {

        Pattern p = Pattern.compile(CURRENCY_REGEX);
        Matcher m = p.matcher(this.getAverageNightlyRate());
        String matched;
        if (m.find()) {
            matched = m.group();
        } else {
            matched = null;
        }

        matched = matched != null ? String.valueOf(Double.valueOf(matched)) : matched;

        try {
            return (matched != null ? String.valueOf(NumberFormat.getInstance(this.getBrowserLocale().getLocale()).parse(matched)) : matched);
        } catch (ParseException e) {
            LOG.info("Exception encountered when attempting to parse nightly rate. Exception: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public WebElement getBestPriceGuaranteeDiv() {
        WebElement tmp = null;
        if (this.findElements(BY_BEST_PRICE_GUARANTEE_TEXT_DIV).size() > 0) {
            tmp = this.findElement(BY_BEST_PRICE_GUARANTEE_TEXT_DIV);
        } else {
            tmp = null;
        }
        return tmp;
    }

    @Override
    public WebElement getBookWithAdvantageDiv() {
        WebElement tmp = null;
        if (this.findElements(BY_BOOK_WITH_US_ADVANTAGE_TEXT_DIV).size() > 0) {
            tmp = this.findElement(BY_BOOK_WITH_US_ADVANTAGE_TEXT_DIV);
        }
        return tmp;
    }

    @Override
    public String getCancellationPolicyText() {
        return this.findElement(BY_CANCELLATION_POLICY_TEXT).getText();
    }

    @Override
    public String getCancelReservationLinkText() {
        return this.findElement(BY_CANCEL_RESERVATION_LINK).getText();
    }

    @Override
    public String[] getCarResAddOns() {
        String[] addOnsList = this.findElement(BY_CAR_RES_ADD_ONS).getText().split("\\r?\\n");
        for (int i = 0; i < addOnsList.length - 1; i++) {
            addOnsList[i] = addOnsList[i].trim();
        }
        return addOnsList;
    }

    @Override
    public String getCarResAssisstanceNumber() {
        return this.findElement(BY_CAR_RES_ASSISTANCE_NUM).getText();
    }

    @Override
    public String getCarResCarType() {
        return this.findElement(BY_CAR_RES_CAR_TYPE).getText();
    }

    @Override
    public String getCarResConfirmationNumber() {
        return this.findElement(BY_CAR_RES_CONF_NUM).getText();
    }

    @Override
    public String getCarResDropOffDate() {
        return this.findElement(BY_CAR_RES_DROPOFF_DATE).getText();
    }

    @Override
    public String getCarResEstimatedTotal() {
        return this.findElement(BY_CAR_RES_ESTIMATED_TOTAL).getText();
    }

    @Override
    public String getCarResPickUpDate() {
        return this.findElement(BY_CAR_RES_PICKUP_DATE).getText();
    }

    @Override
    public String[] getCarResPickUpLocation() {
        String[] pickUpAddress = this.findElement(BY_CAR_RES_PICKUP_LOCATION).getText().split("\\r?\\n");
        for (int i = 0; i < pickUpAddress.length - 1; i++) {
            pickUpAddress[i] = pickUpAddress[i].trim();
        }
        return pickUpAddress;
    }

    @Override
    public String getCarResPickUpLocationCityState() {
        String[] locationText = this.getCarResPickUpLocation();
        if (locationText.length > 0) {
            return locationText[2];
        } else {
            return null;
        }
    }

    @Override
    public String getCarResPickUpLocationCountryZip() {
        String[] locationText = this.getCarResPickUpLocation();
        if (locationText.length > 0) {
            return locationText[3];
        } else {
            return null;
        }
    }

    @Override
    public String getCarResPickUpLocationName() {
        String[] locationText = this.getCarResPickUpLocation();
        if (locationText.length > 0) {
            return locationText[0];
        } else {
            return null;
        }
    }

    @Override
    public String getCarResPickUpLocationStreetAddress() {
        String[] locationText = this.getCarResPickUpLocation();
        if (locationText.length > 0) {
            return locationText[1];
        } else {
            return null;
        }
    }

    @Override
    public String getCarResPromoDescription() {
        return this.findElement(BY_CAR_RES_PROMO_DESCRIPTION).getText();
    }

    @Override
    public String getCarResPromoRate() {
        return this.findElement(BY_CAR_RES_PROMO_RATE).getText();
    }

    @Override
    public String getChangeReservationLinkText() {
        return this.findElement(BY_CHANGE_RESERVATION_LINK).getText();
    }

    @Override
    public DateTime getCheckInDate() {
        return DateUtil.parseLocalizedDate(this.getBrowserLocale(), this.cleanUpDateString(this.findElement(BY_CHECK_IN_DATE_FIELD).getText()));
    }

    @Override
    public String getCheckInDateString() {
        return this.findElement(BY_CHECK_IN_DATE_FIELD).getText();
    }

    @Override
    public DateTime getCheckOutDate() {
        return DateUtil.parseLocalizedDate(this.getBrowserLocale(), this.cleanUpDateString(this.findElement(BY_CHECK_OUT_DATE_FIELD).getText()));
    }

    @Override
    public String getCheckOutDateString() {
        WebElement checkOutElement = this.findElement(BY_CHECK_OUT_DATE_FIELD);
        return checkOutElement.getAttribute("class").contains("notranslate") ? this.findElement(By.cssSelector(CSS_CHECK_OUT_DATE_FIELD.concat(" > span"))).getText()
                : checkOutElement.getText();
    }

    @Override
    public String getChildrenCountText() {
        WebElement counts = this.findElements(BY_STAY_SUMMARY).get(2);
        return this.getCountValue(counts.getText(), 1);
    }

    @Override
    public String getConfirmationNumber() {
        return this.findElement(BY_CONFIRMATION_NUMBER).getText();
    }

    private String getCountValue(String text, int index) {
        String regex = "\\d+";
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches.get(index);
    }

    @Override
    public String getDoubleClickSrc() {
        this.waitForVisibility(BY_CONFIRMATION_NUMBER);
        return this.findElement(BY_DOUBLECLICK_ELEMENT).getAttribute("src");
    }

    @Override
    public String getDuplicateReservationLinkText() {
        return this.findElement(BY_DUPLICATE_RESERVATION_LINK).getText();
    }

    @Override
    public String getEmailConfirmationAddressField1() {
        return this.findElement(BY_EMAIL_CONF_FIELD_1).getText();
    }

    @Override
    public String getEmailConfirmationAddressField2() {
        return this.findElement(BY_EMAIL_CONF_FIELD_2).getText();
    }

    @Override
    public String getEnrolledPcrNumber() {
        return this.findElement(ExpectedConditions.visibilityOfElementLocated(BY_QUICK_ENROLL_PCRN)).getText();

    }

    @Override
    public String getEnrolledTempPin() {
        return this.findElement(ExpectedConditions.visibilityOfElementLocated(BY_QUICK_ENROLL_PIN)).getText();

    }

    @Override
    public String getHotelAddress() {
        return this.findElement(BY_HOTEL_ADDRESS).getText();
    }

    @Override
    public String getHotelName() {
        return this.findElement(BY_HOTEL_NAME).getAttribute("title");
    }

    @Override
    public boolean getLightBox() {
        return this.isElementPresent(BY_LIGHT_BOX) || this.findElement(BY_LIGHT_BOX_CONTENT).isDisplayed();
    }

    @Override
    public WebElement getLowestPromisePriceLogo() {
        WebElement temp = null;
        if (this.findElements(BY_LOWEST_PRICE_PROMISE_LOGO).size() > 0) {
            temp = this.findElement(BY_LOWEST_PRICE_PROMISE_LOGO);
        }
        return temp;
    }

    // TODO: Do we need this method in addition to getPCRInfoAccountBalance?
    @Override
    public String getPCRDetailsAccountBalance() {
        return this.findElement(BY_PCR_DETAILS_ACCT_BALANCE_SID).getText();
    }

    // TODO: Do we need this method in addition to getPCRDetailsAccountBalance?
    @Override
    public int getPCRInfoAccountBalance() {
        return Integer.parseInt(this.findElement(BY_PCR_INFO_ACCT_BALANCE_SID).getText().replaceAll("[^0-9]", ""));

    }

    @Override
    public int getPnCReceiptAuthNumber() {
        int authNumber = Integer.parseInt(this.findElement(BY_PNC_RECEIPT_AUTH_NUMBER_SID).getText());
        return authNumber;
    }

    @Override
    public String getPnCReceiptCCNumber() {
        String ccNumber = this.findElement(BY_PNC_RECEIPT_CC_SID).getText().replaceAll("[^0-9]", "");
        return ccNumber;
    }

    @Override
    public String getPnCReceiptRate() {
        String pncRate = this.findElement(BY_PNC_RECEIPT_RATE_SID).getText();
        return pncRate;
    }

    @Override
    public DateTime getPnCReceiptTimestamp() {
        String timeStamp = this.findElement(BY_PNC_RECEIPT_TIMESTAMP_SID).getText().substring(10).replaceAll("[^0-9]", "");
        DateTimeFormatter dtf = DateTimeFormat.forPattern("ddMMMyyyy"); // hh:mm
        // aa");
        DateTime receiptDateTime = DateTime.parse(timeStamp, dtf);
        return receiptDateTime;
    }

    @Override
    public String getPointsAfterBooking() {
        return this.findElement(By.cssSelector("div[id='pcrInformation']")).getText().split("Account Balance:")[1].trim();
    }

    @Override
    public String getPointsAndCashReceiptText() {
        return this.findElement(BY_PNC_RECEIPT_BLOCK_SID).getText();
    }

    @Override
    public String getPointsToBeEarnedByQuickEnrollMessage() {
        return this.findElement(BY_POINTS_TO_BE_EARNED).getText();
    }

    @Override
    public String getPointsToBeEarnedByQuickEnrollNumberOnly() {
        return this.findElement(BY_POINTS_TO_BE_EARNED).getText().replaceAll("\\D+", "");
    }

    @Override
    public PVAModule getPVAModule() {
        return this.pvaModule;
    }

    @Override
    public String getQuickEnrollMemberNumber() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getQuickEnrollMemberPin() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRateDescriptionFromDetailsLayer() {
        if (!this.getStayDetailsLayer().isDisplayed()) {
            this.clickViewMoreDetailsLink();
        }
        WebElement rateDescription = this.findElement(ExpectedConditions.visibilityOf(this.getStayDetailsLayer().findElements(By.tagName("dd")).get(2)));
        String returnValue = rateDescription.getText().trim();
        this.clickCloseStayDetailsLayerByTopButton();

        return returnValue;
    }

    @Override
    public String getRateFromDetailsLayer() {
        if (!this.getStayDetailsLayer().isDisplayed()) {
            this.clickViewMoreDetailsLink();
        }
        WebElement rate = this.findElement(ExpectedConditions.visibilityOf(this.getStayDetailsLayer().findElement(BY_STAY_DETAILS_RATE)));
        String returnValue = rate.getText().trim();
        this.clickCloseStayDetailsLayerByTopButton();

        return returnValue;
    }

    @Override
    public String getRateTypeText() {
        return this.findElement(BY_RATE_TYPE).getText();
    }

    @Override
    public String getResPriceSummaryDetails() {
        return this.findElement(BY_RES_PRICE_SUMMARY_DETAILS).getText();
    }

    @Override
    public List<WebElement> getResPriceSummaryLines() {
        return this.findElements(BY_RES_PRICE_SUMMARY_LINES);
    }

    @Override
    public String getRoomsCountText() {
        WebElement rooms = this.findElements(BY_STAY_SUMMARY).get(1);
        return rooms.getText();
    }

    @Override
    public String getRoomTypeText() {
        return this.findElement(BY_ROOM_TYPE).getText();
    }

    @Override
    public String getSMSConfTandCHeaderText() {
        return this.findElement(BY_SMS_CONF_TC_HEADER_TEXT).getText();

    }

    @Override
    public String getSpecialRequest() {
        return this.findElement(BY_SPECIAL_REQUEST).getText();
    }

    @Override
    public WebElement getStayDetailsLayer() {
        return this.findElement(BY_STAY_DETAILS_LAYER);
    }

    @Override
    public List<WebElement> getStayDetailsLayerAdditionalChargesLabels() {
        return this.findElements(BY_STAY_DETAILS_ADDITIONAL_CHARGES_LABEL);
    }

    @Override
    public List<WebElement> getStayDetailsLayerAdditionalChargesPrices() {
        return this.findElements(BY_STAY_DETAILS_ADDITIONAL_CHARGES_PRICE);
    }

    @Override
    public String getStayDetailsLayerRateType() {
        return this.findElement(ExpectedConditions.visibilityOfElementLocated(BY_STAY_DETAILS_RATE_TYPE)).getText();
    }

    @Override
    public String getStayDetailsLayerRoomType() {
        return this.findElement(ExpectedConditions.visibilityOfElementLocated(BY_STAY_DETAILS_ROOM_TYPE)).getText();
    }

    // @Override
    public String getTextFromAddingPointsToAccountMessage() {
        return this.findElement(ExpectedConditions.presenceOfElementLocated(BY_BOX_CONTAINER)).findElement(By.cssSelector(".rc-body-left")).getText();
    }

    @Override
    public XlatPageElements getTranslationAffectedElements() {
        XlatPageElements xlatPageElements = new XlatPageElements();
        Map<WebElement, String> texts = new HashMap<WebElement, String>();
        texts.put(this.findElement(By.cssSelector(".resFlowMainTitle")), "Your reservation is confirmed");
        Set<WebElement> urls = new HashSet<WebElement>();
        urls.add(this.findElement(By.cssSelector(".changeReservation")));
        urls.add(this.findElement(By.cssSelector(".cancelReservation")));
        urls.add(this.findElement(By.cssSelector(".duplicateReservation")));
        urls.add(this.findElement(By.cssSelector(".makeReservation")));
        urls.add(this.findElement(By.cssSelector(".viewReservations")));
        xlatPageElements.setTexts(texts);
        xlatPageElements.setUrls(urls);
        return xlatPageElements;
    }

    @Override
    public List<String> getUpcomingPointsDescription() {
        List<WebElement> tmpList = new ArrayList<WebElement>();
        List<String> tmp = new ArrayList<String>();
        tmpList = this.findElements(BY_UPCOMING_POINTS_DESCRIPTION);
        for (WebElement e : tmpList) {
            tmp.add(e.getText());
        }
        return tmp;
    }

    @Override
    public String getUpcomingPotentialPointsHelpLink() {
        return this.findElement(BY_UPCOMING_POINTS_HELP_LINK).getText();
    }

    @Override
    public String getUpcomingPotentialPointsTotal() {
        return this.findElement(BY_UPCOMING_POINTS_TOTAL).getText();
    }

    @Override
    public String getUpcomingPointsTotalNumber() {
        return this.findElement(BY_UPCOMING_POINTS_TOTAL).getText().replaceAll("\\D+", "");
    }

    @Override
    public List<String> getUpcomingPointsValue() {
        List<WebElement> tmpList = new ArrayList<WebElement>();
        List<String> tmp = new ArrayList<String>();
        tmpList = this.findElements(BY_UPCOMING_VALUE);
        for (WebElement e : tmpList) {
            tmp.add(e.getText());
        }
        return tmp;
    }

    @Override
    public String getUpcomingValueCalculateTotal() {
        List<WebElement> tmpList = new ArrayList<WebElement>();
        tmpList = this.findElements(BY_UPCOMING_VALUE);
        int totalPoints = 0;
        for (WebElement e : tmpList) {
            totalPoints += Integer.parseInt(e.getText().replaceAll("\\D+", ""));
        }
        return Integer.toString(totalPoints);
    }

    @Override
    public boolean hasConfirmationNumber() {
        this.closeLightBoxIfVisible();
        return this.isElementDisplayed(BY_CONFIRMATION_NUMBER);
    }

    @Override
    public boolean hasDuplicateReservationMessage() {
        this.closeLightBoxIfVisible();
        return this.isElementDisplayed(BY_DUPLICATE_RESERVATION_MESSAGE);
    }

    @Override
    public boolean hasEditStayPreferenceLink() {
        // Give 3-second buffer for a possible delay on the link displaying
        long start = System.currentTimeMillis();
        long end = start + 3 * 1000;

        while ((System.currentTimeMillis() < end)) {
        }
        return this.isElementDisplayed(BY_EDIT_STAY_PREFERENCES_LINK);
    }

    @Override
    public boolean hasSMSConfCanadaSMSTandCLink() {
        return this.isElementDisplayed(BY_SMS_CONF_TC_LINK);
    }

    @Override
    public boolean hasSpecialRequest() {
        return this.isElementDisplayed(BY_SPECIAL_REQUEST);
    }

    @Override
    public boolean hasSPSuccessMessage() {
        return this.isElementDisplayed(BY_STAY_PREFERENCE_SUCCESS_MESSAGE);
    }

    @Override
    public boolean isAddToTripItButtonDisplayed() {
        return this.isElementDisplayed(BY_ADD_TO_TRIP_IT);
    }

    // TODO: need to also add the Motive and Rate verification here when the
    // time comes for it in later sprints
    @Override
    public boolean isBookingDirectMessageDisplayed() {
        return this.findElement(ExpectedConditions.presenceOfElementLocated(BY_BOOKING_DIRECT)).isDisplayed();
    }

    @Override
    public boolean isBPGLogoDisplayed() {
        return this.isElementPresent(BY_BPG_LOGO) && this.isElementDisplayed(BY_BPG_LOGO);
    }

    @Override
    public boolean isBPPTextPresent() {
        return this.isElementPresent(BY_BEST_PRICE_GUARANTEE_TEXT_DIV);
    }

    @Override
    public boolean isCancelLinkVisible() {
        return this.isElementDisplayed(BY_CANCEL_RESERVATION_LINK);
    }

    @Override
    public boolean isCarResPromoPresent() {
        try {
            return (this.findElement(BY_CAR_RES_PROMO_CONTAINER).isDisplayed() && this.findElement(BY_CAR_RES_PROMO_IMAGE).isDisplayed()
                    && this.findElement(BY_CAR_RES_PROMO_DESCRIPTION).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            LOG.info("Element not found.  If browser locale isn't US/EN, then this is expected. Browser Locale" + this.getBrowserLocale().toString());
            e.printStackTrace();
            this.collector.addError(e);
        }
        return false;
    }

    @Override
    public boolean isCarResSummaryDisplayed() {
        return this.findElement(BY_CAR_RES_SUMMARY_CONTAINER).isDisplayed();
    }

    @Override
    public boolean isChangeReservationsPageLinkDisplayed() {
        List<WebElement> changeReservationLinks = this.findElements(BY_CHANGE_RESERVATION_LINK);
        return !changeReservationLinks.isEmpty();
    }

    @Override
    public boolean isCreditCardGuaranteeLinkDisplayed() {
        try {
            return this.findElement(BY_CREDIT_CARD_GUARANTEE_LINK).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isDICOMExchangeRateDisplayed() {
        this.waitForVisibility(BY_DICOM_EXCHANGE_RATE);
        WebElement elem = this.findElement(BY_DICOM_EXCHANGE_RATE);
        return (elem.isDisplayed() && StringUtils.isNotBlank(elem.getText()));
    }

    @Override
    public boolean isDisplayedUpcomingPotentialPointsDescription() {
        return this.isElementDisplayed(BY_UPCOMING_POINTS_DESCRIPTION);
    }

    @Override
    public boolean isDisplayedUpcomingPotentialPointsValue() {
        return this.isElementDisplayed(BY_UPCOMING_VALUE);
    }

    @Override
    public boolean isDuplicateReservationPageLinkDisplayed() {
        return this.findElement(BY_DUPLICATE_RESERVATION_LINK).isDisplayed();
    }

    @Override
    public boolean isEmailConfirmationErrorDisplayed() {
        return (this.isElementDisplayed(BY_ERROR_BLOCK1) || this.isElementDisplayed(BY_ERROR_BLOCK2));

    }

    @Override
    public boolean isEmailConfirmationPopupDisplayed() {
        return this.isElementDisplayed(BY_EMAIL_CONF_LINK);
    }

    @Override
    public boolean isErrorMessagePresent() {
        return this.isElementPresent(BY_ERROR_MESSAGE);
    }

    @Override
    public boolean isEvenHotelsWellnessPrefsDisplayed() {
        return this.isElementDisplayed(BY_EVEN_HOTELS_WELLNESS_PREFS_DIV);
    }

    @Override
    public boolean isLoginLinkPresent() {
        return this.isElementPresent(BY_IMPLICIT_SIGN_IN);
    }

    @Override
    public boolean isMakeAnotherReservationPageLinkDisplayed() {
        return this.findElement(BY_MAKE_ANOTHER_RESERVATION_LINK).isDisplayed();
    }

    @Override
    public boolean isManageCarReservationButtonDisplayed() {
        return this.findElement(BY_IHG_MANAGE_CAR_RESERVATION_SUMMARY_BTN).isDisplayed();
    }

    @Override
    public boolean ismobileCheckinImagePresent() {
        return this.isElementPresent(BY_MOBILE_CHECKIN_IMAGE);
    }

    @Override
    public boolean isNewAmbassadorAccountInformationDisplayed() {
        return this.getAmbassadorInformationSection().isDisplayed();
    }

    @Override
    public boolean isOnReservationConfirmationPage() {
        return this.getCurrentUrl().contains(RESERVATION_CONFIRMATION_URL_TOKEN) || this.getCurrentUrl().contains(RESERVATION_CONFIRMAITON_INITIAL_LOAD_URL_TOKEN);
    }

    @Override
    public boolean isPointsAndCashReceiptDisplayed() {
        return this.findElement(BY_PNC_RECEIPT_BLOCK_SID).isDisplayed();
    }

    @Override
    public boolean isQuickEnrollDisplayed() {
        return this.isElementDisplayed(BY_QUICK_ENROLL_LINK);
    }

    @Override
    public boolean isQuickEnrollPCRDisplayed() {
        return this.findElement(ExpectedConditions.presenceOfElementLocated(BY_QUICK_ENROLL_PCRN)).isDisplayed();
    }

    @Override
    public boolean isQuickEnrollPINDisplayed() {
        return this.findElement(ExpectedConditions.presenceOfElementLocated(BY_QUICK_ENROLL_PIN)).isDisplayed();
    }

    @Override
    public boolean isQuickEnrollWelcomeMessageDisplayed() {
        return this.findElement(ExpectedConditions.presenceOfElementLocated(BY_QUICK_ENROLL_WELCOME)).isDisplayed();
    }

    @Override
    public boolean isReservationPageLinkDisplayed() {
        return this.findElement(BY_CANCEL_RESERVATION_LINK).isDisplayed();
    }

    @Override
    public boolean isSendSuccessMessageAppear() {
        return this.findElement(BY_SEND_SUCCESS_MESSAGE).isDisplayed();
    }

    @Override
    public boolean isSMSConfirmationErrorDisplayed() {
        this.waitForPresenceOf(BY_ERROR_SMS);
        return (this.isElementDisplayed(BY_ERROR_SMS));

    }

    @Override
    public boolean isSMSConfirmationPopupDisplayed() {
        return this.isElementDisplayed(BY_SMS_CONF_LINK);
    }

    @Override
    public boolean isSMSConfirmationSuccessDisplayed() {
        this.waitForPresenceOf(BY_SUCCESS_SMS);
        return (this.isElementDisplayed(BY_SUCCESS_SMS));

    }

    @Override
    public boolean isStayDetailsLayerDisplayed() {
        return this.isElementDisplayed(BY_STAY_DETAILS_LAYER);
    }

    @Override
    public boolean isSuccessMessageAfterAddingToAccount() {
        return this.findElement(ExpectedConditions.presenceOfElementLocated(BY_SUCCESS_MESSAGE_ASSOCIATED)).isDisplayed();
    }

    @Override
    public boolean isTripItSuccessMessageDisplayed() {
        return this.isElementDisplayed(BY_TRIPIT_SUCCESS_MESSAGE);
    }

    @Override
    public boolean isViewAllReservationsPageLinkDisplayed() {
        return this.findElement(BY_VIEW_ALL_RESERVATIONS_LINK).isDisplayed();
    }

    @Override
    public void rightClickEditStayPreferencesLink() {
        this.findElement(BY_EDIT_STAY_PREFERENCES_LINK).sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
        this.waitForPopupToAppear();
    }

    @Autowired
    @Qualifier(SeleniumPageComponentName.DEFAULT_BRAND_BAR)
    public void setBrandBarModule(BrandBarModule brandBarModule) {
        this.brandBarModule = brandBarModule;
    }

    @Override
    public void setEmailConfirmationAddressField1(String input) {
        WebElement email1 = this.findElement(BY_EMAIL_CONF_FIELD_1);
        email1.clear();
        email1.click();
        email1.sendKeys(input);
    }

    @Override
    public void setEmailConfirmationAddressField2(String input) {
        WebElement email1 = this.findElement(BY_EMAIL_CONF_FIELD_2);
        email1.clear();
        email1.click();
        email1.sendKeys(input);
    }

    @Override
    public void setSMSConfirmationCountryField(String input) {
        Select selectCountry = new Select(this.findElement(ExpectedConditions.visibilityOfElementLocated(BY_SMS_CONF_COUNTRY)));
        selectCountry.selectByVisibleText(input);
        this.tabSMSCountryField();
    }

    @Override
    public void setSMSConfirmationMobileNumberField(String input) {
        WebElement mobileNumber = this.findElement(BY_SMS_CONF_MOBILE_NUMBER_FIELD);
        mobileNumber.clear();
        mobileNumber.click();
        mobileNumber.sendKeys(input);
    }

    @Autowired
    @Qualifier(SeleniumPageComponentName.DEFAULT_UHF_TIMEOUT_MODULE)
    public void setTimeOutModule(UhfTimeOutModule timeOutModule) {
        this.timeOutModule = timeOutModule;
    }

    @Autowired
    @Qualifier(SeleniumPageComponentName.DEFAULT_WALLET_MODULE)
    public void setWalletModule(IhgAccountModule wallet) {
        this.wallet = wallet;
    }

    @Override
    public void signInViaAccountModule(int memberId, int pin) {
        try {
            this.getIhgAccountModule().signIn(memberId, pin);
        } catch (WebDriverException wde) {
            // Possible that lightbox is interfering with closing the wallet
            // after sign in.
            this.closeLightBoxIfVisible();
            this.getIhgAccountModule().closeFlyoutWalletViaHeaderToggle();
        }
        this.closeLightBoxIfVisible();
    }

    @Override
    public void signOutAndCloseLightBox() {
        this.closeLightBoxIfVisible();
        this.getIhgAccountModule().signOut();
        this.closeLightBoxIfVisible();
    }

    @Override
    public String smsConfirmationErrorMessage() {
        return this.findElement(BY_ERROR_SMS).getText();

    }

    @Override
    public void submitCancellationRequest() {
        this.closeLightBoxIfVisible();
        this.clickElement(BY_CANCEL_RESERVATION_LINK);
        super.waitUntilPageLoads();
    }

    private void tabField(By element) {
        this.findElement(element).sendKeys(Keys.TAB);
    }

    private void tabSMSCountryField() {
        this.tabField(BY_SMS_CONF_COUNTRY);
    }

    @Override
    public void waitForDataLayer() {
        this.waitUntil(new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return Bootstrapper.trackerFramework.dataLayer") != null;
            }
        });
    }

    @Override
    public void waitForEmailConfirmationErrorBlock() {
        this.waitForVisibility(BY_ERROR_BLOCK1);
    }

    @Override
    public void waitForReservationConfirmationSuccess() {
        this.waitForVisibility(BY_SEND_SUCCESS_MESSAGE);
    }

    @Override
    public void waitUntilPageLoads() {
        this.waitForVisibility(BY_CONFIRMATION_NUMBER);
        this.waitForInvisibility(BY_CAR_RES_SPINNER);
        this.closeLightBoxIfVisible();
    }

    private void waitUntilStayPageLoads() {
        this.waitForPresenceOf(BY_STAY_PAGE_CATEGORY);
    }
}