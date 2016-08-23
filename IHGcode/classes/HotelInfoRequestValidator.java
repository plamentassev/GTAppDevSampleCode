package com.ihg.dec.apps.hi.actions.reservation.api.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.ihg.client.domain.enhanceyourstay.yourhotel.HotelInfoRequest;

/**
 * Validator class used to validate a HotelInfoRequest
 *
 * @author jpaca
 */
@Component
public class HotelInfoRequestValidator extends AbstractAjaxRequestValidator {

    private static final String HOTEL_MNEMONIC = "hotelMnemonic";
    private static final String LANGUAGE = "language";
    private static final String REGION = "region";

    private static final String HOTEL_MNEMONIC_DESC = "Hotel Mnemonic";
    private static final String LANGUAGE_DESC = "Language";
    private static final String REGION_DESC = "Region";

    /*
     * Verify the Object is supported for validation
     *
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public final boolean supports(Class<?> hotelInfoRequest) {
        return HotelInfoRequest.class.equals(hotelInfoRequest);
    }

    /**
     * Validates:
     *
     * - A hotel code is supplied, the language is supplied, the region is supplied
     *
     * Returns 400 error to client if no preference is passed
     *
     * @param HotelInfoRequest
     *            The resource object to apply basic validation to
     * @param Errors
     *            The Spring Errors object
     *
     */
    @Override
    public void validate(Object upsellRequest, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, HOTEL_MNEMONIC, FIELD_MISSING_ERROR_CODE, HOTEL_MNEMONIC_DESC + MUST_BE_SUPPLIED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, LANGUAGE, FIELD_MISSING_ERROR_CODE, LANGUAGE_DESC + MUST_BE_SUPPLIED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, REGION, FIELD_MISSING_ERROR_CODE, REGION_DESC + MUST_BE_SUPPLIED);
    }

}
