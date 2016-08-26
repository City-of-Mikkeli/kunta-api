package fi.otavanopisto.kuntaapi.server.integrations.ptv;

/**
 * Constants for PTV
 * 
 * @author Otavan Opisto
 */
@SuppressWarnings ("squid:S1125")
public class PtvConsts {

  public static final String DEFAULT_LANGUAGE = "fi";
  public static final String IDENTIFIFER_NAME = "PTV";
  public static final boolean SYNCHRONIZE = true;
  public static final boolean SYNCHRONIZE_ORGANIZATIONS = SYNCHRONIZE && false;
  public static final boolean SYNCHRONIZE_ORGANIZATION_SERVICES = SYNCHRONIZE && true;
  public static final boolean SYNCHRONIZE_GENERAL_DESCRIPTIONS = SYNCHRONIZE && false;
  public static final boolean SYNCHRONIZE_SERVICES = SYNCHRONIZE && false;
  
  private PtvConsts() {
  }
  
}
