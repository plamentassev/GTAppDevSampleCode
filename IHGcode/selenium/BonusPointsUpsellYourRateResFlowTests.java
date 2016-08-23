package com.ihg.dec.apps.bonuspointsuspsell;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import com.dynacrongroup.webtest.parameter.ParallelRunner;
import com.ihg.commons.base.user.LoyaltySeededAccount;
import com.ihg.dec.apps.grouping.categories.DataDependentTest;
import com.ihg.dec.apps.testbases.BonusPointsUpsellTestBase;
import com.ihg.model.reservation.enums.RoomRateSortType;
import com.ihg.selenium.IhgParameterCombination;
import com.ihg.selenium.framework.annotation.TestArea;
import com.ihg.selenium.framework.annotation.TestDescription;
import com.ihg.selenium.framework.annotation.TestFeature;
import com.ihg.selenium.framework.annotation.enums.Areas;
import com.ihg.selenium.framework.annotation.enums.Features;

/**
 * Verifies the Addition of BonusPointsPackage for Your Rate Best Flex is functioning correctly through the reservation flow.
 *
 * @author bradfoni
 *
 */

@Category(DataDependentTest.class)
@RunWith(ParallelRunner.class)
public class BonusPointsUpsellYourRateResFlowTests extends BonusPointsUpsellTestBase {

    private static final String IKME3 = "IKME3", IKME4 = "IKME4";

    public BonusPointsUpsellYourRateResFlowTests(IhgParameterCombination parameterCombination) {
        super(parameterCombination);
    }

    @TestArea(Areas.ROOM_AND_RATES)
    @TestFeature(Features.RATE_DETAIL)
    @TestDescription("Verify that the BonusPointsPackage for YourRate was added to the GIPI URL, is functional, the GIPI Quick Enroll Block is displayed, and the reservation can be completed.")
    @Test
    public void BPPYourRateAddedToGuestInfoURL() {
        this.user = LoyaltySeededAccount.CLUB.createLoyaltySeededUser(this.getEnvironment());
        this.goToEYSPage(TestDataEnum.ABEAL_IDME1.getHotelCode(), RoomRateSortType.RATE_CATEGORY, TestDataEnum.ABEAL_IDME1.getChildRate(), false);
        this.enhanceYourStayPage.waitUntilPageLoads();
        this.enhanceYourStayPage.clickAddBonusPointsButton();
        this.guestAndPaymentInfoPage.waitUntilPageLoads();
        assertThat(this.driver.getCurrentUrl()).containsIgnoringCase(TestDataEnum.ABEAL.getChildRate());
        assertTrue(this.guestAndPaymentInfoPage.isQuickEnrollBlockDisplayed());
        this.guestAndPaymentInfoPage.getIhgAccountModule().signIn(this.user);
        this.guestAndPaymentInfoPage.waitUntilPageLoads();
        this.guestAndPaymentInfoPage.submitGuestAndPaymentInfo(this.user, false, true, false);
        this.reservationConfirmationPage.waitUntilPageLoads();
        assertThat(this.reservationConfirmationPage.getConfirmationNumber()).isNotEmpty();
        assertThat(this.reservationConfirmationPage.getConfirmationNumber()).isNotNull();
        this.cancelReservation();
    }

    @TestArea(Areas.ROOM_AND_RATES)
    @TestFeature(Features.RATE_DETAIL)
    @TestDescription("Verify that the BonusPointsPackage for YourRate with Breakfast was added to the Enhance Your Stay Dropdown and is functional.")
    @Test
    public void BPPYourRateAndYourRateWithBreakfastAddedToEYS() {
        this.goToEYSPage(TestDataEnum.BERPA_IDME1.getHotelCode(), RoomRateSortType.RATE_CATEGORY, TestDataEnum.BERPA_IDME1.getChildRate(), false);
        assertTrue(this.enhanceYourStayPage.isRateInBPPDropdownList(IKME3));
        assertTrue(this.enhanceYourStayPage.isRateInBPPDropdownList(IKME4));
    }

    /**
     * Created to address DE3647 in Rally
     */
    @TestArea(Areas.ROOM_AND_RATES)
    @TestFeature(Features.RATE_DETAIL)
    @TestDescription("Verify that the BonusPointsPackage for YourRate was added to the Reservation Flow successfully, skips EYS when booking IKME4, and can be cancelled and booked again.")
    @Test
    public void BPPYourRateResFlow() {
        this.user = LoyaltySeededAccount.CLUB.createLoyaltySeededUser(this.getEnvironment());
        for (int i = 0; i < 2; i++) {
            this.goToEYSPage(TestDataEnum.BERPA.getHotelCode(), RoomRateSortType.ROOM_TYPE, TestDataEnum.BERPA.getChildRate(), false);
            this.roomRatesPage.getIhgAccountModule().signIn(this.user);
            this.guestAndPaymentInfoPage.waitUntilPageLoads();
            assertTrue(this.guestAndPaymentInfoPage.isOnGuestIntoPage());
            this.guestAndPaymentInfoPage.submitGuestAndPaymentInfo(this.user, false, true, false);
            this.reservationConfirmationPage.waitUntilPageLoads();
            assertThat(this.reservationConfirmationPage.getConfirmationNumber()).isNotEmpty();
            assertThat(this.reservationConfirmationPage.getConfirmationNumber()).isNotNull();
            this.cancelReservation();
        }
    }

    @Before
    public void setUp() {
        // If the test cases are running with sauce connect tunnel and not ihg-proxy, this test cases will no longer run. Always run this test case with ihg-proxy
        assumeFalse("Test is skipped in non-Akamai environment. To launch AFF, request should be over Akamai:", this.isTestRunningOnTeamVM());
    }


}
