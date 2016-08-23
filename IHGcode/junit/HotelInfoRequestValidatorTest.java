package com.ihg.dec.apps.hi.actions.reservation.api.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.validation.BeanPropertyBindingResult;

import com.ihg.client.domain.enhanceyourstay.yourhotel.HotelInfoRequest;

public class HotelInfoRequestValidatorTest {

    private static final String HOTEL_MNEMONIC = "hotelMnemonic";
    private static final String LANGUAGE = "language";
    private static final String REGION = "region";

    private static final String ERRORS = "errors";
    private BeanPropertyBindingResult errors = null;
    HotelInfoRequest hotelInfoRequest = new HotelInfoRequest();
    Class<?> mockClass;

    @InjectMocks
    HotelInfoRequestValidator hotelInfoRequestValidator;

    @Before
    public void init() {
        initMocks(this);
    }

    private void populateHotelInfoRequestData() {
        this.hotelInfoRequest.setHotelMnemonic(HOTEL_MNEMONIC);
        this.hotelInfoRequest.setLanguage(LANGUAGE);
        this.hotelInfoRequest.setRegion(REGION);
    }

    @Test
    public void testFieldErrorsToString() {
        this.populateHotelInfoRequestData();
        this.hotelInfoRequest.setLanguage("");
        this.errors = new BeanPropertyBindingResult(this.hotelInfoRequest, ERRORS);
        this.hotelInfoRequestValidator.validate(this.hotelInfoRequest, this.errors);
        String errorString = this.hotelInfoRequestValidator.fieldErrorsToString(this.errors);
        assertTrue(errorString.length() > 0);
    }

    @Test
    public void testSupportsMethodForCodeCovearge() {
        boolean value = this.hotelInfoRequestValidator.supports(this.mockClass);
        assertFalse(value);
    }

    @Test
    public void testValidateWithCorrectData() {
        this.populateHotelInfoRequestData();
        this.errors = new BeanPropertyBindingResult(this.hotelInfoRequest, ERRORS);
        this.hotelInfoRequestValidator.validate(this.hotelInfoRequest, this.errors);
        assertFalse(this.errors.hasErrors());
    }

    @Test
    public void testValidateWithEmptyHotelMenmonic() {
        this.populateHotelInfoRequestData();
        this.hotelInfoRequest.setHotelMnemonic("");
        this.errors = new BeanPropertyBindingResult(this.hotelInfoRequest, ERRORS);
        this.hotelInfoRequestValidator.validate(this.hotelInfoRequest, this.errors);
        assertTrue(this.errors.hasErrors());
    }

    @Test
    public void testValidateWithEmptyLanguage() {
        this.populateHotelInfoRequestData();
        this.hotelInfoRequest.setLanguage("");
        this.errors = new BeanPropertyBindingResult(this.hotelInfoRequest, ERRORS);
        this.hotelInfoRequestValidator.validate(this.hotelInfoRequest, this.errors);
        assertTrue(this.errors.hasErrors());
    }

    @Test
    public void testValidateWithEmptyRegion() {
        this.populateHotelInfoRequestData();
        this.hotelInfoRequest.setRegion("");
        this.errors = new BeanPropertyBindingResult(this.hotelInfoRequest, ERRORS);
        this.hotelInfoRequestValidator.validate(this.hotelInfoRequest, this.errors);
        assertTrue(this.errors.hasErrors());
    }

}
