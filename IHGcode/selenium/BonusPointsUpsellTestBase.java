package com.ihg.dec.apps.testbases;

import java.util.List;

import org.joda.time.DateTime;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import com.ihg.commons.base.user.User;
import com.ihg.commons.util.url.HIUrlBuilder;
import com.ihg.commons.util.url.SearchRequestBeanCreatorHelper;
import com.ihg.dec.apps.page.helpers.roomrates.RoomAndRateItemHelper;
import com.ihg.model.reservation.enums.RoomRateSortType;
import com.ihg.model.reservation.search.request.SearchRequestBean;
import com.ihg.selenium.IhgParameterCombination;
import com.ihg.selenium.framework.IhgSeleniumTestBase;
import com.ihg.selenium.framework.pagecontracts.CancelConfirmationPage;
import com.ihg.selenium.framework.pagecontracts.CancelReservationPage;
import com.ihg.selenium.framework.pagecontracts.EnhanceYourStayPage;
import com.ihg.selenium.framework.pagecontracts.GuestAndPaymentInfoPage;
import com.ihg.selenium.framework.pagecontracts.ReservationConfirmationPage;
import com.ihg.selenium.framework.pagecontracts.RoomRatesPage;
import com.ihg.selenium.framework.pagecontracts.modules.StayInformationModule;

public class BonusPointsUpsellTestBase extends IhgSeleniumTestBase {

    public enum TestDataEnum {
        TD0("PGFTN", "IGCOR", "IKPCM"),
        TD1("FRAIS", "IGCOR", "IKPCM"),
        TD3("DUBDT", "IGCOR", "IKPCM"),
        TD4("HANHB", "IGCOR", "IKAM2, IKAM4"),
        ABEAL_IDME1("ABEAL", "IGCOR", "IDME1"),
        ABEAL("ABEAL", "IGCOR", "IKME3"),
        BERPA_IDME1("BERPA", "IGCOR", "IDME1"),
        BERPA("BERPA", "IGCOR", "IKME4"),
        HOLIDAY_INN_RESORT("AUAAN", "IGCOR", "IKPCM"),
        HOLIDAY_INN_CLUB_VACATIONS("WBGCV", "IGCOR", "IKPCM");

        private String hotelCode;
        private String parentRate;
        private String childRate;

        TestDataEnum(String hotelCode, String parentRate, String childRate) {
            this.hotelCode = hotelCode;
            this.parentRate = parentRate;
            this.childRate = childRate;
        }

        public String getChildRate() {
            return this.childRate;
        }

        public String getHotelCode() {
            return this.hotelCode;
        }

        public String getParentRate() {
            return this.parentRate;
        }
    }

    protected static final int RES_LENGTH = 4;

    protected User user;

    @Autowired
    protected RoomRatesPage roomRatesPage;
    @Autowired
    protected EnhanceYourStayPage enhanceYourStayPage;

    @Autowired
    protected GuestAndPaymentInfoPage guestAndPaymentInfoPage;
    @Autowired
    protected StayInformationModule stayInformationModule;
    @Autowired
    protected ReservationConfirmationPage reservationConfirmationPage;
    @Autowired
    protected CancelConfirmationPage cancelConfirmationPage;
    @Autowired
    protected CancelReservationPage cancelReservationPage;

    protected SearchRequestBean srb;
    protected SearchRequestBeanCreatorHelper helper;
    protected DateTime checkInDateTime = DateTime.now().plusDays(55);
    protected DateTime checkOutDateTime = DateTime.now().plusDays(59);

    public BonusPointsUpsellTestBase(IhgParameterCombination parameterCombination) {
        super(parameterCombination);
    }

    public void cancelReservation() {
        if (this.reservationConfirmationPage.hasConfirmationNumber()) {
            this.reservationConfirmationPage.submitCancellationRequest();
            this.cancelReservationPage.cancel();
            this.getLogger().info(this.cancelConfirmationPage.getCancellationText());
        } else if (!this.reservationConfirmationPage.getConfirmationNumber().isEmpty()) {
            this.cancelReservationPage.cancel();
        }
    }

    protected void goToEYSPage(String hotelCode, RoomRateSortType sortType, String rateCode, boolean withBreakFast) {
        this.goToRoomsAndRates(hotelCode, sortType);
        if (sortType.equals(RoomRateSortType.RATE_CATEGORY)) {
            WebElement group = RoomAndRateItemHelper.getRateItem(this.roomRatesPage.getPageWrapper(), rateCode);
            List<WebElement> roomList = this.roomRatesPage.getRoomsListForGroup(group);
            this.roomRatesPage.expandAGroup(group);
            this.roomRatesPage.bookRoom(roomList.get(0));
        } else if (sortType.equals(RoomRateSortType.ROOM_TYPE)) {
            WebElement rate1 = this.roomRatesPage.getRoomByRateCodeRoomTypeSort(rateCode);
            if (withBreakFast) {
                this.roomRatesPage.clickBreakfastCheck(rate1);
            }
            this.roomRatesPage.bookRoom(rate1);
        } else {
            this.getLogger().error("Invalid sort type !!!!!");
        }

    }

    protected void goToRoomsAndRates(String hotelCode, RoomRateSortType sortType) {
        this.helper = new SearchRequestBeanCreatorHelper(this.getParameters(), this.getClass());
        this.srb = this.helper.createSearchRequestBeanFromConfig(true);
        this.srb.getWhen().setCheckInDate(this.checkInDateTime.toDate());
        this.srb.getWhen().setCheckOutDate(this.checkOutDateTime.toDate());
        this.srb.getWhere().setHotelCode(hotelCode);
        this.srb.setSelectedHotelCode(hotelCode);
        this.srb.getControls().setRoomRateSortOrder(sortType);
        this.srb.getOptions().setRateCodes(null);
        String url = HIUrlBuilder.buildRoomRate(this.getBrand(), this.getBrowserLocale(), this.getEnvironment(), this.srb, null);
        this.getLogger().info("my URL : " + url);
        this.driver.get(url);
        this.roomRatesPage.waitUntilPageLoads();
    }

}
