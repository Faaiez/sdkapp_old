package com.sap.cloud.sdk.tutorial.sdkapp;

import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;

import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

public class GetBusinessPartnersCommand extends ErpCommand<List<BusinessPartner>> {
    private static final Logger logger = CloudLoggerFactory.getLogger(GetBusinessPartnersCommand.class);

    protected GetBusinessPartnersCommand() {
        super(GetBusinessPartnersCommand.class);
    }

    @Override
    protected List<BusinessPartner> run()
            throws Exception {
        final List<BusinessPartner> businessPartners =
                new DefaultBusinessPartnerService()
                        .getAllBusinessPartner()
                        .select(BusinessPartner.BUSINESS_PARTNER,
                                BusinessPartner.LAST_NAME,
                                BusinessPartner.FIRST_NAME,
                                BusinessPartner.IS_MALE,
                                BusinessPartner.IS_FEMALE,
                                BusinessPartner.CREATION_DATE)
                        .orderBy(BusinessPartner.LAST_NAME, Order.ASC)
                        .execute();

        return businessPartners;
    }

    @Override
    protected List<BusinessPartner> getFallback() {
        logger.warn("Fallback called because of exception:", getExecutionException());
        return Collections.emptyList();
    }
}