<?php
  defined( 'ABSPATH' ) || die( 'No script kiddies please!' );
  
  require_once ('vendor/autoload.php');
  require_once ('kunta-api-client.php');

  class ServiceRenderer {
  	
  	private $twig;
  	
  	public function __construct() {
  	  $this->twig = new Twig_Environment(new Twig_Loader_Filesystem( __DIR__ . '/templates'));
  	}
  	
  	function renderDefault($service) {
  	  $serviceId = $service->getId();
  	  $name = $this->getLocalizedValue($service->getName(), "fi");
  	  $description = null;
  	  $shortDescription = null;
  	  $serviceUserInstruction = null;
  	  
  	  foreach ($service->getDescriptions() as $serviceDescription) {
  	  	switch ($serviceDescription->getType()) {
  	  	  case 'Description':
  	  	    $description = $serviceDescription->getValue();
  	  	  break;
  	  	  case 'ShortDescription':
  	  	    $shortDescription = $serviceDescription->getValue();
  	  	  break;
  	  	  case 'ServiceUserInstruction':
  	  	    $serviceUserInstruction = $serviceDescription->getValue();
  	  	  break;
  	  	  default:
  	  	  	error_log("Unknown description type: $serviceDescription->getType() found.");
  	  	  break;
  	  	}
  	  }
  	  
  	  return $this->twig->render("service-default-layout.twig", [
  	    'serviceId' => $serviceId,
        'name' => $name,
  	    'description' => $description,
  	  	'shortDescription' => $shortDescription,
  	    'serviceUserInstruction' => $serviceUserInstruction
  	  ]);
  	}
  	
  	private function getLocalizedValue($localizedValue, $language) {
  	  foreach ($localizedValue as $item) {
  	  	if ($language == $item['language']) {
  	  	  return $item['value'];
  	  	}
  	  }
  	  
  	  return null;
  	}
  	
  }

?>