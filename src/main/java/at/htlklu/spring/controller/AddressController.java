package at.htlklu.spring.controller;

import at.htlklu.spring.model.*;
import at.htlklu.spring.repository.*;

import at.htlklu.spring.api.LogUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "mvc/addresses")
public class AddressController {
    //region Properties
    private static Logger logger = LogManager.getLogger(AddressController.class);
    private static final String CLASS_NAME = "AddressController";
    public static final String FORM_NAME_SINGLE = "AddressSingle";
    public static final String FORM_NAME_LIST = "AddressList";

    @Autowired
    AddressRepository addressRepository;
    //endregion


    // localhost:8082/mvc/addresses
    @GetMapping("")
    public ModelAndView show() {
        logger.info(LogUtils.info(CLASS_NAME, "show"));

        ModelAndView mv = new ModelAndView();

        mv.setViewName(AddressController.FORM_NAME_LIST);                // Übergabe der View

        List<Address> addresses = addressRepository.findAll();            // Sortierung fehlt
        mv.addObject("addresses", addresses);                  // Übergabe des Models

        return mv;
    }
}