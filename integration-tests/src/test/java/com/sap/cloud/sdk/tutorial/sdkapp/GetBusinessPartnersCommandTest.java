package com.sap.cloud.sdk.tutorial.sdkapp;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import com.sap.cloud.sdk.cloudplatform.servlet.Executable;
import com.sap.cloud.sdk.testutil.MockUtil;

import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.cloudplatform.servlet.RequestContextExecutor;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBusinessPartnersCommandTest {

    private MockUtil mockUtil;

    @Before
    public void beforeClass() {
        mockUtil = new MockUtil();
        mockUtil.mockDefaults();
    }

    private List<BusinessPartner> getBusinessPartners() {
        return new GetBusinessPartnersCommand().execute();
    }

    @Test
    public void testWithSuccess() throws Exception {
        mockUtil.mockErpDestination();
        new RequestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                assertThat(getBusinessPartners()).isNotEmpty();
            }
        });
    }

    @Test
    public void testWithFallback() throws Exception {
        mockUtil.mockDestination("ErpQueryEndpoint", new URI("http://localhost"));
        new RequestContextExecutor().execute(new Executable() {
            @Override
            public void execute() throws Exception {
                assertThat(getBusinessPartners()).isEqualTo(Collections.emptyList());
            }
        });
    }

}