package fi.otavanopisto.kuntaapi.server.integrations;

import java.util.List;

import fi.otavanopisto.kuntaapi.server.rest.model.Organization;

/**
 * Provider interface for organizations
 * 
 * @author Otavan Opisto
 */
public interface OrganizationProvider {
  
  /**
   * Find organization
   * 
   * @param id organization's id
   * @return Business
   */
  public Organization findOrganizations(OrganizationId id);
  
  /**
   * Lists organizations.
   * 
   * @param businessName filter results by business name
   * @param businessCode filter results by business code
   * @return List of businesses
   */
  public List<Organization> listOrganizations(String businessName, String businessCode);
  
}
