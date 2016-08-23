package com.ihg.dec.apps.bonuspointsuspsell;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assume.assumeFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.dynacrongroup.webtest.parameter.ParallelRunner;
import com.ihg.commons.applicationoptions.RatePreference;
import com.ihg.dec.apps.grouping.categories.DataDependentTest;
import com.ihg.dec.apps.testbases.BonusPointsUpsellTestBase;
import com.ihg.model.reservation.enums.RoomRateSortType;
import com.ihg.selenium.IhgParameterCombination;
import com.ihg.selenium.framework.pagecontracts.modules.StayInformationModule;

/**
 * These tests verify the Rate Description And Rate Rules Popup on the Enhance Your Stay Page.
 *
 * @author bradfoni
 *
 */
@Category(DataDependentTest.class)
@RunWith(ParallelRunner.class)
public class BonusPointsPopupTests extends BonusPointsUpsellTestBase {

    @Autowired
    private StayInformationModule stayInfoModule;

    public BonusPointsPopupTests(IhgParameterCombination parameterCombination) {
        super(parameterCombination);
    }

    @Before
    public void setup() {
        // If the test cases are running with sauce connect tunnel and not ihg-proxy, this test cases will no longer run. Always run this test case with ihg-proxy
        assumeFalse("Test is skipped in non-Akamai environment. To launch AFF, request should be over Akamai:", this.isTestRunningOnTeamVM());
        this.goToEYSPage(TestDataEnum.TD0.getHotelCode(), RoomRateSortType.RATE_CATEGORY, RatePreference.BestFlexible.getCode(), false);
        this.enhanceYourStayPage.waitUntilPageLoads();
    }

    @Test
    public void verifyPopupFields() {
        String rateType = this.stayInfoModule.getRateTypeDescription();
        String expectedAverageNightlyRate = this.stayInfoModule.getAverageNightlyrate().trim();
        String modifyOrCancelPolicyText = this.stayInfoModule.getModifyCancelPolicyText().trim();

        this.stayInfoModule.clickToOpenRateAndRulesPopUp();

        assertThat(this.stayInfoModule.getPopupRateType()).containsIgnoringCase(rateType);

        assertThat(this.stayInfoModule.getPopupTaxText()).isNotEmpty();
        assertThat(this.stayInfoModule.getPopupTaxText()).isNotNull();

        assertThat(this.stayInfoModule.getPopupCancelPolicy().trim()).containsIgnoringCase(modifyOrCancelPolicyText);

        assertThat(this.stayInfoModule.getPopupRateDescription()).isNotEmpty();
        assertThat(this.stayInfoModule.getPopupRateDescription()).isNotNull();

        assertThat(this.stayInfoModule.getPopupAverageNightlyRateText().replaceAll("[^0-9]", "")).containsIgnoringCase(expectedAverageNightlyRate);
    }

}
