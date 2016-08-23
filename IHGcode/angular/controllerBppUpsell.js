(function(ng, $) {
	"use strict";
	
	ng.module("YourHotel").controller("YourHotelController", ["$scope", "UrlUtil", function($scope, UrlUtil) {
		// Namespace scope
		$scope.yourHotel = {
			data 	: {},
			loaded	: false
		};

		var yourHotelDetailsUrl = UrlUtil.getUrl('yourHotelDetails');

		// destructive modification of the response data to cover the case of Holiday Inn Asia Pacific region
		// inherited from pre-existing implementation, unknown justification
		var handleResponseHolidayInnAsiaPacific = function(data) {
			var isApplicable = (data.brandId &&
				data.brandId === 'HIRT' &&
				data.countryMktRegionCode &&
				data.countryMktRegionCode === 'ASIA_PACIFIC');
			data.modBrandId = isApplicable ? 'HIRT05457' : data.brandId;
		};

		// images from ihg.scene7.com can be resized with query parameters to optimize download
		// destructively change image URLs to match dimensions of thumbnail element; pick one to use
		var handleThumbnail = function() {
			var imgSelector = '#hotelImage img';

			var dimGuard = function(dim) {
				// force height or width to be a minimum of 1
				return Math.max(dim || 0, 1);
			};

			// urls at ihg.scene7.com can be shrunk and stretched server-side
			// CSS is fully in control of the dimensions of the thumbnail
			var scene7UrlTransform = function() {
				var isScene7Regex = /^(https?:)?\/\/ihg[^.]*\.scene7\.com/;
				var hasParametersRegex = /^(https?:)?\/\/.*\?/;
				return function(url) {
					if (url.match(isScene7Regex)) {
						var hasParameters = url.match(hasParametersRegex);
						var sel = $(imgSelector);
						var width = dimGuard(sel.width()),
							height = dimGuard(sel.height());
						// http://crc.scene7.com/is-docs/pages/HTTP-Protocol-Reference.htm
						return url + (hasParameters ? '&' : '?') + 'wid=' + encodeURI(String(width)) + '&hei=' + encodeURI(String(height)) + '&fit=stretch';
					} else {
						return url;
					}
				};
			}();

			// CSS is fully in control of the dimensions of the thumbnail
			var updateImgElement = function(data, url) {
				var sel = $(imgSelector);
				// setting width and height attributes shouldn't matter but let's be paranoid
				sel.attr('width', dimGuard(sel.width()));
				sel.attr('height', dimGuard(sel.height()));
				data.primaryImage = url;
			};

			var xform = _.curry(function(data, key) {
				data[key] = scene7UrlTransform(data[key]);
			});

			return function(data) {
				xform(data, 'primaryImageUrl');
				if (data.primaryImageUrls) {
					_.keys(data.primaryImageUrls).forEach(xform(data.primaryImageUrls));
					// prefer the HCM-designated primary image, then any 1x1 image, then whatever image
					updateImgElement(data, data.primaryImageUrl || data.primaryImageUrls['1x1'] || _.toPairs(data.primaryImageUrls)[0][0]);
				} else {
					updateImgElement(data, data.primaryImageUrl);
				}
			};
		}();

		var handleResponse = function(response) {
			if(response && response.data) {
				var data = response.data;
				$scope.yourHotel.data = data;
				handleResponseHolidayInnAsiaPacific(data);
				handleThumbnail(data);
			}
			$scope.yourHotel.loaded = true;
		};

		var initialize = function() {
			var empty = {};
			return function() {
				UrlUtil.getRemoteData(yourHotelDetailsUrl, empty).then(handleResponse);
			};
		}();

		initialize();
	}]);
})(angular, jQuery);
