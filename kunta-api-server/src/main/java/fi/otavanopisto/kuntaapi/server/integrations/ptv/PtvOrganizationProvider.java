package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import fi.otavanopisto.kuntaapi.server.integrations.IdController;
import fi.otavanopisto.kuntaapi.server.integrations.KuntaApiConsts;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationId;
import fi.otavanopisto.kuntaapi.server.integrations.OrganizationProvider;
import fi.otavanopisto.kuntaapi.server.rest.model.Organization;
import fi.otavanopisto.ptv.client.ApiResponse;
import fi.otavanopisto.ptv.client.model.VmOpenApiGuidPage;
import fi.otavanopisto.ptv.client.model.VmOpenApiOrganization;

/**
 * Organization provider for PTV
 * 
 * @author Antti Leppä
 */
@Dependent
public class PtvOrganizationProvider implements OrganizationProvider {
  
  @Inject
  private Logger logger;
  
  @Inject
  private PtvApi ptvApi;
  
  @Inject
  private IdController idController;

  private PtvOrganizationProvider() {
  }

  @Override
  public Organization findOrganization(OrganizationId organizationId) {
    OrganizationId ptvOrganization = idController.translateOrganizationId(organizationId, KuntaApiConsts.IDENTIFIER_NAME);
    if (ptvOrganization == null) {
      logger.severe(String.format("Failed to translate organizationId %s into PTV organizationId", organizationId.toString()));
      return null;
    }
    
    ApiResponse<VmOpenApiOrganization> ptvOrganizationResponse = ptvApi.getOrganizationApi().apiOrganizationByIdGet(ptvOrganization.getId());
    if (!ptvOrganizationResponse.isOk()) {
      logger.severe(String.format("Organization %s reported [%d] %s", ptvOrganization.getId(), ptvOrganizationResponse.getStatus(), ptvOrganizationResponse.getMessage()));
    } else {
      return transform(ptvOrganizationResponse.getResponse());
    }
    
    return null;
  }

  @Override
  @SuppressWarnings("squid:S135")
  public List<Organization> listOrganizations(String businessName, String businessCode) {
    List<Organization> result = new ArrayList<>();
    
    ApiResponse<VmOpenApiGuidPage> apiOrganizationGet = ptvApi.getOrganizationApi().apiOrganizationGet(null, 0);
    if (!apiOrganizationGet.isOk()) {
      logger.severe(String.format("Organizations listing reported [%d] %s", apiOrganizationGet.getStatus(), apiOrganizationGet.getMessage()));
      return Collections.emptyList();
    }
    
    VmOpenApiGuidPage page = apiOrganizationGet.getResponse();
   
    for (String guid : page.getGuidList()) {
      ApiResponse<VmOpenApiOrganization> organizationResponse = ptvApi.getOrganizationApi().apiOrganizationByIdGet(guid);
      if (!organizationResponse.isOk()) {
        logger.severe(String.format("Organization %s reported [%d] %s", guid, organizationResponse.getStatus(), organizationResponse.getMessage()));
      } else {
        VmOpenApiOrganization ptvOrganization = organizationResponse.getResponse();
        
        if (StringUtils.isNotBlank(businessCode) && !StringUtils.equals(businessCode, ptvOrganization.getBusinessCode())) {
          continue;
        }
      
        if (StringUtils.isNotBlank(businessName) && !StringUtils.equals(businessName, ptvOrganization.getBusinessName())) {
          continue;
        }
      
        result.add(transform(ptvOrganization));
      }
    }

    return result;
  }
  
  private Organization transform(VmOpenApiOrganization ptvOrganiztion) {
    OrganizationId ptvId = new OrganizationId(PtvConsts.IDENTIFIFER_NAME, ptvOrganiztion.getId());
    OrganizationId kuntaApiId = idController.translateOrganizationId(ptvId, KuntaApiConsts.IDENTIFIER_NAME);
    if (kuntaApiId == null) {
      logger.severe(String.format("Could not translate %s into Kunta API id", ptvId.getId()));
      return null;
    }
    
    Organization organization = new Organization();
    organization.setId(kuntaApiId.getId());
    organization.setBusinessCode(ptvOrganiztion.getBusinessCode());
    organization.setBusinessName(ptvOrganiztion.getBusinessName());
    
    return organization;
  }

}
