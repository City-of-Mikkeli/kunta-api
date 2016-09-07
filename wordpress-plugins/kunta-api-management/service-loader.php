<?php
  defined( 'ABSPATH' ) || die( 'No script kiddies please!' );
  
  require_once ('vendor/autoload.php');
  require_once ('kunta-api-client.php');

  class ServiceLoader {
  	
  	private $organizationid;
  	private $services;
  	private $servicesApi;
  	
  	public function __construct() {
  	  $options = get_option('kunta_api_management');
  	  if (!$options['organizationid']) {
        error_log(__( 'Failed to update service classes: Missing organization id setting from Kunta API Settings', 'kunta_api_management' ));
  	  }
  		
  	  $this->organizationid = $options['organizationid'];
  	  $this->services = [
  	  	
  	  ];
  	  $this->servicesApi = new KuntaAPI\Api\ServicesApi(getKuntaApiClient());
  	}
  	
  	public function loadService($serviceId) {
  	  if (!in_array($serviceId, $this->services)) {
        $this->services[$serviceId] = $this->servicesApi->findService($this->organizationid, $serviceId);
      }
  		
  	  return $this->services[$serviceId];
  	}
  	
  	public function loadServiceComponent($serviceId, $serviceComponent) {
  	  $service = $this->loadService($serviceId);
  	  
  	  foreach ($service->getDescriptions() as $serviceDescription) {
  	  	if (($serviceDescription->getType() == $serviceComponent)) {
  	  	  return $serviceDescription->getValue();
  	  	}
  	  }
  	  
  	  return null;
  	}
  	
  }

?>