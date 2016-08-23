package com.ihg.dec.apps.hi.actions.reservation.api.resource.bpp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.ihg.client.domain.enhanceyourstay.bppupsell.BPPUpsellRequest;

public class BPPUpsellRequestTest {

    private static final String HOTEL_CODE = "mock_hotel_code";
    private static final String CHECKIN_DATE = "20160324";
    private static final String CHECKOUT_DATE = "20160323";
    private static final String RATE_CODE = "mock_rate_code";
    private static final String RATE_CODE2 = "mock_rate_code2";
    private static final String ROOM_CODE = "mock_room_code";
    private static final int ADULTS = 2;
    private static final int ROOMS = 1;
    private static final int CHILDREN = 0;

    BPPUpsellRequest bppUpsellRequest = new BPPUpsellRequest();
    BPPUpsellRequest bppUpsellRequest2 = new BPPUpsellRequest();

    @Test
    public void hashCodeTest() {

        this.populateBPPUpsellRequest(this.bppUpsellRequest);
        this.populateBPPUpsellRequest(this.bppUpsellRequest2);

        assertEquals(this.bppUpsellRequest.hashCode(), this.bppUpsellRequest2.hashCode());

        this.bppUpsellRequest2.setAdultCount(this.bppUpsellRequest2.getAdultCount() + 1);
        assertNotEquals(this.bppUpsellRequest.hashCode(), this.bppUpsellRequest2.hashCode());

        this.bppUpsellRequest2.setAdultCount(this.bppUpsellRequest2.getAdultCount() - 1);
        this.bppUpsellRequest2.setRateCode(RATE_CODE2);
        assertNotEquals(this.bppUpsellRequest.hashCode(), this.bppUpsellRequest2.hashCode());

    }

    private void populateBPPUpsellRequest(BPPUpsellRequest bppUpsellRequest) {
        bppUpsellRequest.setHotelCode(HOTEL_CODE);
        bppUpsellRequest.setCheckInDate(CHECKIN_DATE);
        bppUpsellRequest.setCheckOutDate(CHECKOUT_DATE);
        bppUpsellRequest.setRoomCode(ROOM_CODE);
        bppUpsellRequest.setRateCode(RATE_CODE);
        bppUpsellRequest.setRoomCount(ROOMS);
        bppUpsellRequest.setAdultCount(ADULTS);
        bppUpsellRequest.setChildCount(CHILDREN);
    }

    @Test
    public void setterGetterTest() {
        this.populateBPPUpsellRequest(this.bppUpsellRequest);

        assertEquals(this.bppUpsellRequest.getHotelCode(), HOTEL_CODE);
        assertEquals(this.bppUpsellRequest.getCheckInDate(), CHECKIN_DATE);
        assertEquals(this.bppUpsellRequest.getCheckOutDate(), CHECKOUT_DATE);
        assertEquals(this.bppUpsellRequest.getRateCode(), RATE_CODE);
        assertEquals(this.bppUpsellRequest.getRoomCode(), ROOM_CODE);
        assertEquals(this.bppUpsellRequest.getRoomCount(), ROOMS);
        assertEquals(this.bppUpsellRequest.getAdultCount(), ADULTS);
        assertEquals(this.bppUpsellRequest.getChildCount(), CHILDREN);
        assertNotNull(this.bppUpsellRequest.toString());
    }

}
