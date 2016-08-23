(function() {
	"use strict";
	
	angular.module("YourStay").controller("YourStayController", ["$scope", "UrlUtil", "UserSession", "$sce", function($scope, UrlUtil, UserSession, $sce) {
		// Namespace scope
		$scope.yourStay = {
			data 	: {},
			getHTML	: function(text) {
				return $sce.trustAsHtml(text);
			},
			loaded	: false
		};
		
		var vars = {
			url : UrlUtil.getUrl('yourStayDetails')
		};
		
		var fns = {
			getParams : function() {
				var result = {
			        hotelCode	: UserSession.getHotelCode(),
			        checkInDate	: UserSession.getCheckInDate(),
			        checkOutDate: UserSession.getCheckOutDate(),
			        rateCode	: UserSession.getRateCode(),
			        roomCode	: UserSession.getSelectedRoomCode(),
			        roomCount	: UserSession.getNumRooms(),
			        adultCount	: UserSession.getNumAdults(),
			        childCount	: UserSession.getNumChildren()
			    };
			    if (UserSession.getCorporateId() !== null) {
			    	result.corporateId = UserSession.getCorporateId();
			    }
			    return result;
			},
			
			init : function() {
				UrlUtil.getRemoteData(vars.url, fns.getParams()).then(function(response) {
					if(response && response.data) {
						$scope.yourStay.data 	= response.data;
						$scope.yourStay.loaded 	= true;
						$scope.yourStay.singularMessage = response.data.singularUrgencyMessage;
					}
				});
			}	
		};
		
		// init
		fns.init();
	}]);
})();