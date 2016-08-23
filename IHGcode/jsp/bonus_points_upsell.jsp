<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ichotelsgroup.com/taglib/mvc" prefix="pcrmvc" %>

<div id="eys_wrapper" data-ng-show="pointsUpsell.loaded" class="__bonusPointsMVTBox ng-cloak" data-ng-controller="PointsUpsellController">

    <p class="bonusPointsUpsellSignInTeaser"
    data-ng-if="!pointsUpsell.showWantToEarn"
    data-slnm-ihg="bonusPointsHeaderTextSID">
    	<%-- Sign in to earn {N} points for this stay with a bonus points upgrade --%>
    	<fmt:message key="label.bonuspoints.upsell.title.1"/>
    	<span class="bonusPointsUpsellIncentiveAmount notranslate" data-slnm-ihg="anonymousBPPIncentiveSID"> {{ bppPointsPerStayCalc }} </span>
    	<fmt:message key="label.bonuspoints.upsell.title.2"/>
    </p>

    <p data-ng-if="pointsUpsell.showWantToEarn"
	data-slnm-ihg="bonusPointsHeaderTextSID">
		<%-- Want to earn {N} more points for this stay with a bonus points upgrade? --%>
    	<fmt:message key="label.bonuspoints.upsell.subtitle.1"/>
    	<span class="bonusPointsUpsellIncentiveAmount notranslate" data-slnm-ihg="explicitBPPIncentiveSID"> {{ bppPointsPerStayCalc }} </span>
    	<fmt:message key="label.bonuspoints.upsell.subtitle.2"/>
	</p>

    <div class="__bonusPointsMVTBox_actionBox">
        <div class="__bonusPointInnerBlock">
            <p><fmt:message key="title.points.upsell.bonus.points" /></p>

            <%-- SINGLE ITEM --%>
            <div class="bppSingleItemWrapper" data-ng-if="!pointsUpsell.multipleChoices" data-slnm-ihg="bppSingleItemSID">
            	<div data-ng-init="pointsUpsell.setInitialSelectedRate()">
	                <span class="bppSingleItem"> 
	                	<span data-ng-bind-template="{{ pointsUpsell.selectedDetail.points }}"></span>                
						<span data-ng-switch="pointsUpsell.selectedDetail.upsellUnits">
								<span data-ng-switch-when="POINTS"><fmt:message key="label.BonusPointsPackageUpsell.points.unit" /></span>
								<span data-ng-switch-when="POINTS_PER_NIGHT"><fmt:message key="label.BonusPointsPackageUpsell.pointspernight.unit" /></span>
								<span data-ng-switch-when="POINTS_PER_STAY"><fmt:message key="label.BonusPointsPackageUpsell.pointsperstay.unit" /></span>
								<span data-ng-switch-when="POINTS_PER_STAY_BREAKFAST"><fmt:message key="label.BonusPointsPackageUpsell.pointsperstaybreakfast.unit" /></span>
								<span data-ng-switch-when="POINTS_PER_DAY_BREAKFAST"><fmt:message key="label.BonusPointsPackageUpsell.pointsperdaybreakfast.unit" /></span>
						</span>
	                </span>                
	                <input type="hidden" id="bonusPointPackageRate" name="bonusPointPackageRate" value="{{ pointsUpsell.selectedDetail.rateDetailsCode }}" />
                </div>
            </div>

            <%-- MULTIPLE OFFERS --%>
            <section class="bonusPointPackageOptions" name="bonusPointPackageOptions" data-ng-if="pointsUpsell.multipleChoices">
                <div class="bppContainer" data-ng-init="pointsUpsell.setInitialSelectedRate()">
                    <div class="bppDisplayWrapper" data-ng-click="pointsUpsell.showOptions = !pointsUpsell.showOptions">
                    	<div class="bppDisplay" data-slnm-ihg="pointsSelectDropDownButtonSID"> 
                    		<span data-ng-bind-template="{{ pointsUpsell.selectedDetail.points }}"></span> 
							<span data-ng-switch="pointsUpsell.selectedDetail.upsellUnits">
								<span data-ng-switch-when="POINTS"><fmt:message key="label.BonusPointsPackageUpsell.points.selected" /></span>
								<span data-ng-switch-when="POINTS_PER_NIGHT"><fmt:message key="label.BonusPointsPackageUpsell.pointspernight.selected" /></span>
								<span data-ng-switch-when="POINTS_PER_STAY"><fmt:message key="label.BonusPointsPackageUpsell.pointsperstay.selected" /></span>
								<span data-ng-switch-when="POINTS_PER_STAY_BREAKFAST"><fmt:message key="label.BonusPointsPackageUpsell.pointsperstaybreakfast.selected" /></span>
								<span data-ng-switch-when="POINTS_PER_DAY_BREAKFAST"><fmt:message key="label.BonusPointsPackageUpsell.pointsperdaybreakfast.selected" /></span>
							</span>

							<a></a>                   		
                    	</div>
                    	<span class="bppArrow">&#x25BC;</span>
                   	</div>
                    <div class="bppDropdown" data-slnm-ihg="pointsSelectDropDownSID" data-toggle-slidedown="pointsUpsell.showOptions" data-toggle-close-on-document-click=".bonusPointPackageOptions">

                    	<div data-ng-repeat="upsellGroup in pointsUpsell.data.upsellGroups">
	                    	<span class="bppLabel" data-slnm-ihg="pointsSelectDropDownOptionsSID"> 
								<span data-ng-if="upsellGroup.type == 'PER_DAY'"><fmt:message key="label.BonusPointsPackageUpsell.perday.type" /></span>
								<span data-ng-if="upsellGroup.type == 'PER_STAY'"><fmt:message key="label.BonusPointsPackageUpsell.perstay.type" /></span>
								<span data-ng-if="upsellGroup.type == 'PER_NIGHT'"><fmt:message key="label.BonusPointsPackageUpsell.pernight.type" /></span>
	                    	</span>

	                    	<p class="bppOption" 
	                    		data-ng-repeat="upsellDetail in upsellGroup.upsellDetails" 
	                    		data-ng-click="onClickBppOption(upsellGroup, upsellDetail)"
	                    		data-slnm-ihg="pointsSelectDropDownValuesSID" 
	                    		data-currencycode="{{ pointsUpsell.data.currencyCode }}" 
	                    		data-currencysymbol="{{ pointsUpsell.data.currencySymbol }}" 
	                    		data-packagepoints="{{ upsellDetail.points }}" 
	                    		data-packagepertext="{{ upsellDetail.upsellUnits }}"  
	                    		data-packagetype="{{ upsellGroup.encoding }}" 
	                    		data-packageprice="{{ upsellDetail.upchargePriceDifference }}" 
	                    		data-bpprate="{{ upsellDetail.rateDetailsCode }}">

	                    		<span data-ng-bind-template="{{ upsellDetail.points }}"></span>
								<span data-ng-switch="upsellDetail.upsellUnits">
									<span data-ng-switch-when="POINTS"><fmt:message key="label.BonusPointsPackageUpsell.points.unit" />&nbsp;<fmt:message key="label.BonusPointsPackageUpsell.for" /></span>
									<span data-ng-switch-when="POINTS_PER_NIGHT"><fmt:message key="label.BonusPointsPackageUpsell.pointspernight.unit" />&nbsp;<fmt:message key="label.BonusPointsPackageUpsell.for" /></span>
									<span data-ng-switch-when="POINTS_PER_STAY"><fmt:message key="label.BonusPointsPackageUpsell.pointsperstay.unit" />&nbsp;<fmt:message key="label.BonusPointsPackageUpsell.for" /></span>
									<span data-ng-switch-when="POINTS_PER_STAY_BREAKFAST"><fmt:message key="label.BonusPointsPackageUpsell.pointsperstaybreakfast.unit" />&nbsp;<fmt:message key="label.BonusPointsPackageUpsell.for" /></span>
									<span data-ng-switch-when="POINTS_PER_DAY_BREAKFAST"><fmt:message key="label.BonusPointsPackageUpsell.pointsperdaybreakfast.unit" />&nbsp;<fmt:message key="label.BonusPointsPackageUpsell.for" /></span>
								</span>
								<span data-ng-bind-template="{{ upsellDetail.upchargePriceDifference | eyscurrency:pointsUpsell.data.currencySymbol:pointsUpsell.data.currencyCode }}"></span>
                    		</p>
	                    	<hr class="bppSeparator">
                    	</div>
                    </div>
                </div>
                <input type="hidden" id="bonusPointPackageRate" name="bonusPointPackageRate" value="" />
            </section>
        </div>
        <div class="__bonusPointInnerBlock--center1"><fmt:message key="text.points.upsell.for.only" /></div>
        <div class="__bonusPointInnerBlock--center2" data-ng-if="pointsUpsell.loaded">
            <p><fmt:message key="title.points.upsell.additional.costs" /></p>
            <section data-ng-init="pointsUpsell.setInitialSelectedRate()">
                <div class="bppAdditionalCost" data-slnm-ihg="additionalCostSID" 
                	data-ng-bind-template="+ {{ pointsUpsell.selectedDetail.upchargePriceDifference | eyscurrency:pointsUpsell.data.currencySymbol:pointsUpsell.data.currencyCode }}"></div>
            </section>
        </div>
        <div class="addBonusPointsButton __bonusPointInnerBlock--right" title="Add Bonus Points" data-slnm-ihg="addBonusPointsButtonSID">
            <a class="buy-points" data-ng-click="pointsUpsell.setSessionRateCode()" class="bppAddPackage">
            	<span id="btnAddPoints--BonusPointsPackageUpsell" class="bppBtnAddPoints"><fmt:message key="button.points.upsell.add.points" /></span>
            </a>
        </div>
    </div>
    <div class="__bonusPointsMVTBox_errorBox" data-slnm-ihg="bonusPointsErrorMessageSID">
        <p><fmt:message key="text.points.upsell.cs.description" /></p>
    </div>
</div>
