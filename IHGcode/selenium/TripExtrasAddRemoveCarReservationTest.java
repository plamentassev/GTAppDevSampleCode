package com.ihg.dec.apps.tripextras;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import com.dynacrongroup.webtest.parameter.ParallelRunner;
import com.ihg.commons.applicationoptions.RatePreference;
import com.ihg.dec.apps.grouping.suites.TripExtrasSuite;
import com.ihg.dec.apps.testbases.TripExtrasTestBase;
import com.ihg.selenium.IhgParameterCombination;

/*
 *  US8860
 *
 * The purpose of this test is to create a room reservation, than repeatedly add a remove a car to the reservation. Our findings indicate that once we cancel a car reservation, we
 * can nop loger add car reservation to the same room reservation. Therefore the test is likely to fail until iSeatz fix their back end.
 */
@Category(TripExtrasSuite.class)
@RunWith(ParallelRunner.class)
public class TripExtrasAddRemoveCarReservationTest extends TripExtrasTestBase {

    private final int COUNT = 3;

    public TripExtrasAddRemoveCarReservationTest(IhgParameterCombination parameterCombination) {
        super(parameterCombination);
    }

    @Before
    public void setup() {
        this.createSRB(ATLANTA_HOTEL_CODE, ATLANTA_BEST_FLEX_ROOMCODE, RatePreference.BestFlexible.getCode());
    }

    @Test
    public void testTripExtrasAddRemoveCarReservation() {
        // If the test cases are running with sauce connect tunnel and not ihg-proxy, this test cases will no longer run. Always run this test case with ihg-proxy
        assumeFalse("Test is skipped in non-Akamai environment. To launch AFF, request should be over Akamai:", this.isTestRunningThroughSauceTunnel());

        // make a room reservation
        this.makeRoomReservation();

        for (int i = 0; i < this.COUNT; i++) {
            // add car to the room reservation
            this.reservationConfirmationPage.waitUntilPageLoads();
            assertTrue(this.reservationConfirmationPage.isCarResPromoPresent());
            this.addCarToReservation();
            this.reservationConfirmationPage.waitUntilPageLoads();
            this.reservationConfirmationPage.clickManageCarReservationButton();
            this.manageTripExtrasPage.waitUntilPageLoads();
            String carReservationNumber = this.manageTripExtrasPage.getCarResConfirmationNumber();

            // cancel car reservation
            this.manageTripExtrasPage.clickCancelCarReservationButton();
            this.cancelTripExtrasPage.waitUntilPageLoads();
            this.cancelTripExtrasPage.clickCancelReservation();
            this.cancelConfirmationTripExtrasPage.waitUntilPageLoads();
            String cancellationNumber = this.cancelConfirmationTripExtrasPage.getCarResCancellationNumber();
            // the car reservation number looks like this: "Confirmation # G....."
            // but the cancellation number is just the number without the Confirmation # part
            int hashIndex = carReservationNumber.indexOf("#");
            if (hashIndex > 0) {
                carReservationNumber = carReservationNumber.substring(hashIndex + 1);
            }
            carReservationNumber = carReservationNumber.trim();
            assertEquals(cancellationNumber, carReservationNumber);

            // return to Confirmation Reservation Page (should display tripExtras Promo)
            this.cancelConfirmationTripExtrasPage.clickReturnToHotelConfirmationPage();
        }
    }
}
