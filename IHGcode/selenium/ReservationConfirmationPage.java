package com.ihg.selenium.framework.pagecontracts;

import java.util.List;

import org.joda.time.DateTime;
import org.openqa.selenium.WebElement;

import com.ihg.selenium.framework.pagecontracts.modules.PVAModule;
import com.ihg.selenium.framework.util.xlat.XlatPageElements;

public interface ReservationConfirmationPage extends BasePageObject {

    void clickAddCarToMyStay();

    void clickAddToTripItButton();

    void clickAssociateNumber();

    void clickChangeReservationButton();

    void clickChangeReservationLink();

    void clickCloseStayDetailsLayerByBottomButton();

    void clickCloseStayDetailsLayerByTopButton();

    void clickContinueButton();

    void clickDuplicateReservationLink();

    void clickEditStayPreferencesLink();

    void clickEditTempPinLink();

    void clickEmailConfirmationLink();

    void clickEvenHotelsWellnessPrefsShareButton();

    void clickHotelDetails();

    void clickHotelStayDetailsLink();

    void clickJoinNowButton();

    void clickManageCarReservationButton();

    void clickManageCarReservationSummaryButton();

    void clickPrintConfirmationLink();

    void clickQuickEnrollLink();

    void clickSendEmailConfirmationButton();

    void clickSignInRCHeader();

    void clickSMSConfCanadaSMSTandCLink();

    void clickSMSConfirmationButton();

    void clickSMSConfirmationCheckBox();

    void clickSMSConfirmationLink();

    void clickStayDetailsLayerCurrencyLink();

    void clickViewMoreDetailsLink();

    void clickViewMoreDetailsOfYourCarReservation();

    void closeEmailConfirmationLayer();

    void closeLightBox();

    void closeSMSConfirmationLayer();

    String getAdultsCountText();

    String getAverageNightlyRate();

    String getAverageNightlyRateAmountOnly();

    WebElement getBestPriceGuaranteeDiv();

    WebElement getBookWithAdvantageDiv();

    String getCancellationPolicyText();

    String getCancelReservationLinkText();

    /**
     * @return each Car Addon, separated by each element per line item.
     */
    String[] getCarResAddOns();

    /**
     * @return the Car Reservation Assistance telephone number
     */
    String getCarResAssisstanceNumber();

    String getCarResCarType();

    /**
     * @return the Car Reservation-specific confirmation number. This is NOT the hotel confirmation number.
     */
    String getCarResConfirmationNumber();

    String getCarResDropOffDate();

    String getCarResEstimatedTotal();

    String getCarResPickUpDate();

    /**
     * @return each line item of the location's address as a separate element.
     */
    String[] getCarResPickUpLocation();

    String getCarResPickUpLocationCityState();

    String getCarResPickUpLocationCountryZip();

    String getCarResPickUpLocationName();

    String getCarResPickUpLocationStreetAddress();

    String getCarResPromoDescription();

    String getCarResPromoRate();

    String getChangeReservationLinkText();

    DateTime getCheckInDate();

    String getCheckInDateString();

    DateTime getCheckOutDate();

    String getCheckOutDateString();

    String getChildrenCountText();

    String getConfirmationNumber();

    String getDoubleClickSrc();

    String getDuplicateReservationLinkText();

    String getEmailConfirmationAddressField1();

    String getEmailConfirmationAddressField2();

    String getEnrolledPcrNumber();

    String getEnrolledTempPin();

    String getHotelAddress();

    String getHotelName();

    boolean getLightBox();

    WebElement getLowestPromisePriceLogo();

    String getPCRDetailsAccountBalance();

    int getPCRInfoAccountBalance();

    int getPnCReceiptAuthNumber();

    String getPnCReceiptCCNumber();

    String getPnCReceiptRate();

    /**
     * @return the Date/Time on the Points And Cash Credit Card Receipt as a DateTime object
     */
    DateTime getPnCReceiptTimestamp();

    String getPointsAfterBooking();

    String getPointsAndCashReceiptText();

    String getPointsToBeEarnedByQuickEnrollMessage();

    String getPointsToBeEarnedByQuickEnrollNumberOnly();

    PVAModule getPVAModule();

    String getQuickEnrollMemberNumber();

    String getQuickEnrollMemberPin();

    String getRateDescriptionFromDetailsLayer();

    String getRateFromDetailsLayer();

    String getRateTypeText();

    String getResPriceSummaryDetails();

    List<WebElement> getResPriceSummaryLines();

    String getRoomsCountText();

    String getRoomTypeText();

    String getSMSConfTandCHeaderText();

    String getSpecialRequest();

    WebElement getStayDetailsLayer();

    List<WebElement> getStayDetailsLayerAdditionalChargesLabels();

    List<WebElement> getStayDetailsLayerAdditionalChargesPrices();

    String getStayDetailsLayerRateType();

    String getStayDetailsLayerRoomType();

    XlatPageElements getTranslationAffectedElements();

    List<String> getUpcomingPointsDescription();

    String getUpcomingPotentialPointsHelpLink();

    String getUpcomingPotentialPointsTotal();

    String getUpcomingPointsTotalNumber();

    List<String> getUpcomingPointsValue();

    String getUpcomingValueCalculateTotal();

    boolean hasConfirmationNumber();

    boolean hasDuplicateReservationMessage();

    boolean hasEditStayPreferenceLink();

    boolean hasSMSConfCanadaSMSTandCLink();

    boolean hasSpecialRequest();

    boolean hasSPSuccessMessage();

    boolean isAddToTripItButtonDisplayed();

    boolean isBookingDirectMessageDisplayed();

    boolean isBPGLogoDisplayed();

    boolean isBPPTextPresent();

    boolean isCancelLinkVisible();

    /**
     * Verifies that the Car Reservation Promotion is displayed in it's entirety.
     *
     * @return true only if the container, description, image, and button for the Car Reservation Promo is displayed.
     */
    boolean isCarResPromoPresent();

    boolean isCarResSummaryDisplayed();

    boolean isChangeReservationsPageLinkDisplayed();

    boolean isCreditCardGuaranteeLinkDisplayed();

    boolean isDICOMExchangeRateDisplayed();

    boolean isDisplayedUpcomingPotentialPointsDescription();

    boolean isDisplayedUpcomingPotentialPointsValue();

    boolean isDuplicateReservationPageLinkDisplayed();

    boolean isEmailConfirmationErrorDisplayed();

    boolean isEmailConfirmationPopupDisplayed();

    boolean isErrorMessagePresent();

    boolean isEvenHotelsWellnessPrefsDisplayed();

    boolean isLoginLinkPresent();

    boolean isMakeAnotherReservationPageLinkDisplayed();

    boolean isManageCarReservationButtonDisplayed();

    boolean ismobileCheckinImagePresent();

    boolean isNewAmbassadorAccountInformationDisplayed();

    boolean isOnReservationConfirmationPage();

    boolean isPointsAndCashReceiptDisplayed();

    boolean isQuickEnrollDisplayed();

    boolean isQuickEnrollPCRDisplayed();

    boolean isQuickEnrollPINDisplayed();

    boolean isQuickEnrollWelcomeMessageDisplayed();

    boolean isReservationPageLinkDisplayed();

    boolean isSendSuccessMessageAppear();

    boolean isSMSConfirmationErrorDisplayed();

    boolean isSMSConfirmationPopupDisplayed();

    boolean isSMSConfirmationSuccessDisplayed();

    boolean isStayDetailsLayerDisplayed();

    boolean isSuccessMessageAfterAddingToAccount();

    boolean isTripItSuccessMessageDisplayed();

    boolean isViewAllReservationsPageLinkDisplayed();

    void rightClickEditStayPreferencesLink();

    void setEmailConfirmationAddressField1(String input);

    void setEmailConfirmationAddressField2(String input);

    void setSMSConfirmationCountryField(String input);

    void setSMSConfirmationMobileNumberField(String input);

    void signInViaAccountModule(int memberId, int pin);

    void signOutAndCloseLightBox();

    String smsConfirmationErrorMessage();

    void submitCancellationRequest();

    void waitForDataLayer();

    void waitForEmailConfirmationErrorBlock();

    void waitForReservationConfirmationSuccess();

}
