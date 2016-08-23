package com.ihg.dec.apps.hi.actions.reservation.api.delegate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihg.client.domain.Address;
import com.ihg.client.domain.enhanceyourstay.yourhotel.HotelInfoRequest;
import com.ihg.client.domain.enhanceyourstay.yourhotel.HotelInfoResource;
import com.ihg.commons.helpers.HotelDetailBeanHelper;
import com.ihg.model.exceptions.ServiceException;
import com.ihg.model.hotelcontent.ImageUrl;
import com.ihg.model.reservation.hotel.HotelDetailBean;
import com.ihg.server.dao.HotelDao;

@Component
public class HotelInfoDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(HotelInfoDelegate.class);
    
    private static final String ASPECT_RATIO_FORMAT = "{0}x{1}";

    @Autowired
    HotelDao hotelDao;

    @Autowired
    HotelDetailBeanHelper hotelDetailBeanHelper;

    /*
     * Get General Hotel information.
     *
     * @param A HotelInfoRequest object containing the request parameters (hotel mnemonic and locale)
     *
     * @return A HotelInfoResource object containing general hotel information
     */
    public HotelInfoResource getHotelInfo(HotelInfoRequest hotelInfoRequest) throws ServiceException {

        Locale locale = new Locale(hotelInfoRequest.getLanguage(), hotelInfoRequest.getRegion());

        LOG.debug("getHotelInfo:  locale=" + locale.toString());

        return this.getHotelInfo(hotelInfoRequest.getHotelMnemonic(), locale);
    }

    private HotelInfoResource getHotelInfo(String mnemonic, Locale locale) {

        HotelDetailBean hotelDetailBean = null;
        hotelDetailBean = this.hotelDao.getHotelDetailByMnemonic(mnemonic, locale);

        if (hotelDetailBean != null) {
            HotelInfoResource hotelInfoResource = new HotelInfoResource();
            this.hotelDetailBeanToHotelInfoResource(hotelDetailBean, hotelInfoResource);
            return hotelInfoResource;
        } else {
            throw new ServiceException();
        }

    }

    private void mapImageUrls(HotelDetailBean hotelDetailBean, HotelInfoResource hotelInfoResource) {
        hotelInfoResource.setPrimaryImageUrl(hotelDetailBean.getPrimaryImageURL());

        Map<String, String> primaryImageUrls = new HashMap<String, String>();
        List<ImageUrl> primaryImageUrlsFromService = hotelDetailBean.getPrimaryImageUrls();
        if(primaryImageUrlsFromService != null) {
	        for(ImageUrl imageUrl : primaryImageUrlsFromService) {
	        	String aspectRatio = MessageFormat.format(ASPECT_RATIO_FORMAT, new Object[]{imageUrl.getAspectWidth(), imageUrl.getAspectHeight()});
	        	primaryImageUrls.put(aspectRatio, imageUrl.getUrl());
	        }
	        hotelInfoResource.setPrimaryImageUrls(primaryImageUrls);
        }
    }

    private void hotelDetailBeanToHotelInfoResource(HotelDetailBean hotelDetailBean, HotelInfoResource hotelInfoResource) {

        hotelInfoResource.setBrandId(hotelDetailBean.getBrandCode());

        // fields in the bean not guaranteed to be null-safe; do that here !
        Address hotelAddress = this.hotelDetailBeanHelper.makeAddressNullSafe(hotelDetailBean.getAddress());

        hotelInfoResource.setAddress1(hotelAddress.getAddress1());
        hotelInfoResource.setAddress2(hotelAddress.getAddress2());
        hotelInfoResource.setAddress3(hotelAddress.getAddress3());
        hotelInfoResource.setCity(hotelAddress.getCity());

        hotelInfoResource.setCountryDisplayName(hotelAddress.getCountry().getDisplayName());
        hotelInfoResource.setCountryMktRegionCode(StringUtils.defaultString(hotelAddress.getCountry().getMktRegionCode(), ""));
        hotelInfoResource.setCountryMktRegionName(StringUtils.defaultString(hotelAddress.getCountry().getMktRegionName(), ""));

        hotelInfoResource.setPostalCode(hotelAddress.getPostalCode());
        hotelInfoResource.setStateCode(hotelAddress.getState().getCode());

        hotelInfoResource.setName(hotelDetailBean.getName());

        mapImageUrls(hotelDetailBean, hotelInfoResource);
    }

    /*
     * Strip of the domain portion of the URL
     */
    protected String stripDomainName(String fullURL) {
        if (fullURL != null && fullURL.startsWith("http")) {
            int index = fullURL.lastIndexOf("//");
            if (index >= 0) {
                index = fullURL.indexOf('/', index + 2);
                return fullURL.substring(index);
            }
        }

        return fullURL;
    }

}
