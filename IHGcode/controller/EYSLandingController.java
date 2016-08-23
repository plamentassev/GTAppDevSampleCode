package com.ihg.trip.extras.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ihg.model.reservation.search.request.SearchRequestBean;
import com.ihg.trip.extras.config.JMXSwitch;

@Controller
@RequestMapping("**/")
/**
 * Landing controller for all eys URLs.
 *
 */
public class EYSLandingController {

    private static final String ENHANCE_YOUR_STAY_HOME = "eys_home";
    private static final String TRIP_EXTRAS_INPATH = "tripExtrasInPath";
    private static final String URL_PREFIX_VIRTUAL_ASSISTANT = "virtualAssistantEndPoint";
    private static final String GENESYS_CHAT_SWITCH = "genesysChatSwitch";

    static final Logger LOG = LoggerFactory.getLogger(EYSLandingController.class);

    @Autowired
    JMXSwitch jmxSwitch;

    @Value("#{systemProperties['virtual.assistant.url']}")
    String virtualAssistantUrl;

    @RequestMapping(value = "/{country}/{language}/eys_home")
    public ModelAndView enhanceYourStayHome(HttpServletRequest request, SearchRequestBean searchRequest) {

        // by default, the view is eys_home.jsp
        ModelAndView modelAndView = new ModelAndView(ENHANCE_YOUR_STAY_HOME);

        // add necessary model objects
        modelAndView.addObject(TRIP_EXTRAS_INPATH, this.jmxSwitch.tripExtrasInpathSwitch.isEnabled());
        modelAndView.addObject(GENESYS_CHAT_SWITCH, this.jmxSwitch.eysGenesysChatSwitch.isEnabled());
        modelAndView.addObject(URL_PREFIX_VIRTUAL_ASSISTANT, this.virtualAssistantUrl);

        // return the view
        return modelAndView;

    }

}
