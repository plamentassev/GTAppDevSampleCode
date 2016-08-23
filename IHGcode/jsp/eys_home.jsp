<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.ichotelsgroup.com/taglib/day" prefix="day"%>
<%@ taglib uri="http://www.ichotelsgroup.com/taglib/mvc" prefix="pcrmvc" %>
<html lang="${AppConfig.language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title data-slnm-ihg="pageTitleMessageSID">Enhance Your Stay - IHG</title>
    <link rel="stylesheet" type="text/css" href="/enhanceyourstay/static/styles/dist/style-rollup.min.css${BUILD_VERSION}">
    <script type="text/javascript" src="/enhanceyourstay/static/scripts/dist/eys-head.min.js?v=2.1.3" charset="UTF-8"></script>
    <script type="text/javascript">
        var jq; jq = jq || jQuery;<%-- UHF loaded code breaks without this here, bug in UHF --%>
        var BUILD_VERSION = '${BUILD_VERSION}';
        var virtualAssistantEndPoint= '${virtualAssistantEndPoint}';
    </script>
    <pcrmvc:ensightenBootstrap/>
    <pcrmvc:uhfHead/>
</head>


<body class="${AppConfig.brand.id eq '6c'? "sixc" : AppConfig.brand.id}">

    <pcrmvc:uhfHeader/>
    <div class="container">

    	<c:if test="${genesysChatSwitch eq true}">
	        <div class="row">
	        	<%-- Need help? Ask our virtual assistant. --%>
	            <div class="virtual-chat-container ng-cloak ng-hide" data-ng-controller="VirtualAssistantController" data-ng-show="showVirtualAssistant">
	            	<fmt:message key="title.virtualassistant.text.1"/>
	            	<a data-slnm-ihg="chatVirtualAssistantLinkSID" data-ng-click="openVirtualAssistantWindow()"> <fmt:message key="title.virtualassistant.text.2"/></a>
	            	<fmt:message key="title.virtualassistant.text.3"/>
	            </div>
	        </div>
        </c:if>

        <div class="row">
            <p class="col-lg-12 bpp_message ${AppConfig.brand.id}" data-slnm-ihg="pageTitleMessageSID">Add Bonus Points to Your Stay</p>
        </div>
        <div class="row">
            <div class="col-sm-3 col-md-4 col-lg-4 invoice-container">
                <pcrmvc:invoiceModule/>
            </div>
            <div class="col-sm-9 col-md-8 col-lg-8 bonus-container">
            	<div data-ng-controller="SignInController" data-ng-include="'templates/sign_in_helper${BUILD_VERSION}'"></div> 
                <%@ include file="/components/bonus_points_upsell.jsp" %>

                <c:if test="${tripExtrasInPath}">
	                <%@ include file="/components/trip_extras/car/banner.jsp" %>
				</c:if>

                <%-- Continue with Reservation Button --%>
                <div class="continueResbutton_wrapper">
                    <p class="BonusPointsPackageUpsell__NoElections__title" data-slnm-ihg="continueButtonHeaderTitleSID">Prefer to skip this step?</p>
                    <pcrmvc:cssButton href="" elemId="continueLink" brandCode="${AppConfig.brand.id}" buttonText="Continue with your reservation" titleText="Continue with your reservation" seleniumId="continueWithResButtonSID" isFormInput="false"/>
                </div>
            </div>
        </div>
    </div>
    <pcrmvc:uhfFooter/>
    <script type="text/javascript" src="/enhanceyourstay/static/scripts/dist/eys-body.min.js${BUILD_VERSION}" charset="UTF-8"></script>
    <!-- Locale file -->
    <c:set var="localeFilePart" value="${AppConfig.language}-${AppConfig.country}" />
    <script type="text/javascript" src="/enhanceyourstay/static/scripts/vendor/angular/i18n/angular-locale_${localeFilePart}.js?v=1.5.5" charset="UTF-8"></script>
    <script type="text/javascript">IHG.LinkManagement.autoUpdateHref('#continueLink', 'guestandpaymentinfo');</script>
</body>
</html>
