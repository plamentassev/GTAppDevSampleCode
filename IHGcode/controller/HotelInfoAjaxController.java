package com.ihg.dec.apps.hi.actions.reservation.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ihg.client.domain.enhanceyourstay.RequestStatus;
import com.ihg.client.domain.enhanceyourstay.yourhotel.HotelInfoRequest;
import com.ihg.client.domain.enhanceyourstay.yourhotel.HotelInfoResource;
import com.ihg.dec.apps.hi.actions.reservation.api.delegate.HotelInfoDelegate;
import com.ihg.dec.apps.hi.actions.reservation.api.validator.HotelInfoRequestValidator;
import com.ihg.dec.apps.hi.config.urlmapping.URLMappingElement;
import com.ihg.model.exceptions.ServiceException;

/**
 * Controller for obtaining general hotel information such as address, name, etc.
 *
 * Inputs are the hotel mnemonic and the locale.
 *
 * @author John Paca 3/18/2016
 */
@Controller
public class HotelInfoAjaxController {

    static final Logger LOGGER = LoggerFactory.getLogger(HotelInfoAjaxController.class.getName());

    private static final String SERVICE_EXCEPTION_MSG = "Service exception obtaining hotel information. Caused by bad hotel code, language, or region.";

    private static final String URL_PATH_TEST_PATH = "/{region}/{language}/api/hotelInfo/{hotelMnemonic}";

    @Autowired
    private HotelInfoDelegate hotelInfoDelegate;

    @Autowired
    private HotelInfoRequestValidator hotelInfoRequestValidator;

    @Autowired
    URLMappingElement urlMappingElement;

    private ResponseEntity<?> createErrorResponse(HttpStatus httpStatus, String message) {
        HotelInfoResource bppUpsellResource = new HotelInfoResource();
        RequestStatus requestStatus = new RequestStatus();
        requestStatus.setCode(Integer.toString(httpStatus.value()));
        requestStatus.setMessage(message);
        bppUpsellResource.setStatus(requestStatus);
        return new ResponseEntity<>(bppUpsellResource, httpStatus);

    }

    @RequestMapping(value = URL_PATH_TEST_PATH, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getHotelInfo(@ModelAttribute @Valid HotelInfoRequest hotelInfoRequest, @PathVariable String region, @PathVariable String language,
            @PathVariable String hotelMnemonic, HttpServletRequest request) {

        LOGGER.debug("getHotelInfoViaPath: region=" + region + " language=" + language + " hotelMnemonic=" + hotelMnemonic);

        hotelInfoRequest.setHotelMnemonic(hotelMnemonic);
        hotelInfoRequest.setLanguage(this.urlMappingElement.getLanguageId());
        hotelInfoRequest.setRegion(this.urlMappingElement.getCountryId());

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(hotelInfoRequest, "hotelInfoRequest");

        return this.handleRequest(hotelInfoRequest, result);
    }

    private ResponseEntity<?> handleRequest(HotelInfoRequest hotelInfoRequest, BindingResult result) {

        HotelInfoResource hotelInfoResource = null;
        this.hotelInfoRequestValidator.validate(hotelInfoRequest, result);
        if (result.hasErrors()) {
            return this.createErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, this.hotelInfoRequestValidator.fieldErrorsToString(result));
        }

        try {
            hotelInfoResource = this.hotelInfoDelegate.getHotelInfo(hotelInfoRequest);
            RequestStatus requestStatus = new RequestStatus();
            requestStatus.setCode(Integer.toString(HttpStatus.OK.value()));
            requestStatus.setMessage(HttpStatus.OK.name());
            return new ResponseEntity<>(hotelInfoResource, HttpStatus.OK);

        } catch (ServiceException se) {
            // A ServiceException is thrown for the call if there is an issue with one of the inputs; however, the exception does not have
            // any message (it is null), so create a message
            return this.createErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, SERVICE_EXCEPTION_MSG);
        }
    }

}
