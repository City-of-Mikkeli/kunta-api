package fi.otavanopisto.kuntaapi.server.integrations.ptv;

import java.util.ArrayList;
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

@Dependent
public class PtvOrganizationProvider implements OrganizationProvider {

  @Inject
  private Logger logger;
  
  @Inject
  private PtvApi ptvApi;
  
  @Inject
  private IdController idController;

  @Override
  public Organization findOrganizations(OrganizationId id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  @SuppressWarnings("squid:S135")
  public List<Organization> listOrganizations(String businessName, String businessCode) {
    List<Organization> result = new ArrayList<>();
    
    ApiResponse<VmOpenApiGuidPage> apiOrganizationGet = ptvApi.getOrganizationApi().apiOrganizationGet(null, 0);
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
